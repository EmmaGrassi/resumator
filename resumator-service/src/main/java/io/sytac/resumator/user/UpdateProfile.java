package io.sytac.resumator.user;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.security.Identity;
import io.sytac.resumator.security.Roles;
import io.sytac.resumator.security.UserPrincipal;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.naming.NoPermissionException;
import javax.naming.OperationNotSupportedException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

/**
 * Creates a new {@link Profile}
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
@Path("profiles/{email}")
@RolesAllowed(Roles.USER)
public class UpdateProfile extends BaseResource {

    private final ProfileRepository profileRepository;
    private final CommandFactory descriptors;
    private final EventPublisher events;

    @Inject
    public UpdateProfile(ProfileRepository profileRepository, final CommandFactory descriptors, final EventPublisher events) {
        this.profileRepository = profileRepository;
        this.descriptors = descriptors;
        this.events = events;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({RepresentationFactory.HAL_JSON, MediaType.APPLICATION_JSON})
    public Response doUpdate(@PathParam("email") final String email,
                             final ProfileCommandPayload payload,
                             @UserPrincipal final Identity identity,
                             @Context final UriInfo uriInfo) throws NoPermissionException, OperationNotSupportedException {

        validateEmails(email, payload.getEmail());

        if (!identity.hasRole(Roles.ADMIN)) {
            checkPermissionsForUpdate(identity, email);
            validateRestrictedFields(payload);
        }

        final Profile profile = profileRepository.getProfileByEmail(email);
        if (profile == null) {
            return Response.status(HttpStatus.NOT_FOUND_404).build();
        }

        final Map<String, String> profileFailures = ProfileValidator.validateProfile(payload);
        if (profileFailures.size() > 0) {
            return buildValidationFailedRepresentation(profileFailures);
        }

        final UpdateProfileCommand command = descriptors.updateProfileCommand(profile.getId(), payload);
        final Profile updatedProfile = profileRepository.update(command);

        events.publish(command);

        return buildRepresentation(uriInfo, updatedProfile);
    }

    private Response buildValidationFailedRepresentation(Map<String, String> notValidatedFields) {
        final String halResource = rest.newRepresentation()
                .withProperty("status", "failed")
                .withProperty("fields", notValidatedFields)
                .toString(RepresentationFactory.HAL_JSON);

        return Response.ok(halResource).status(HttpStatus.BAD_REQUEST_400).build();
    }

    private void checkPermissionsForUpdate(final Identity identity, final String email) throws NoPermissionException {
        if (!email.equals(identity.getName())) {
            throw new NoPermissionException("You don't have permissions to update this profile");
        }
    }

    private void validateEmails(final String emailInQuery, final String payloadEmail) throws OperationNotSupportedException {
        final String checkedEmail = Optional.ofNullable(emailInQuery).orElseThrow(IllegalArgumentException::new);
        if (!checkedEmail.equals(payloadEmail)) {
            throw new OperationNotSupportedException("Unable to change email address - operation is not supported");
        }
    }

    private void validateRestrictedFields(final ProfileCommandPayload payload) throws NoPermissionException {
        final Profile currentProfile = profileRepository.getProfileByEmail(payload.getEmail());
        if (currentProfile.isAdmin() != payload.isAdmin()) {
            throw new NoPermissionException("You do not have permissions to update all the fields you provided");
        }
    }

    private Response buildRepresentation(final UriInfo uriInfo, final Profile profile) {
        final URI profileLink = resourceLink(uriInfo, ProfileQuery.class, profile.getEmail());
        final Representation halResource = rest.newRepresentation()
                .withProperty("status", "updated")
                .withProperty("email", profile.getEmail())
                .withLink("profile", profileLink);

        return Response.ok(halResource.toString(RepresentationFactory.HAL_JSON))
                .status(HttpStatus.OK_200)
                .header(HttpHeader.LOCATION.asString(), profileLink.toString())
                .build();
    }
}
