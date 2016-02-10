package io.sytac.resumator.user;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.employee.EmployeeQuery;
import io.sytac.resumator.employee.EmployeeValidator;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.security.Identity;
import io.sytac.resumator.security.Roles;
import io.sytac.resumator.security.UserPrincipal;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.naming.NoPermissionException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
@Path("profiles")
@RolesAllowed(Roles.USER)
public class NewProfile extends BaseResource {

    private final ProfileRepository profileRepository;
    private final CommandFactory descriptors;

    @Inject
    public NewProfile(final ProfileRepository profileRepository, final CommandFactory descriptors) {
        this.profileRepository = profileRepository;
        this.descriptors = descriptors;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({RepresentationFactory.HAL_JSON, MediaType.APPLICATION_JSON})
    public Response newProfile(final ProfileCommandPayload payload,
                                @UserPrincipal final Identity identity,
                                @Context final UriInfo uriInfo) throws NoPermissionException {

        final String checkedEmail = Optional.ofNullable(payload.getEmail()).orElseThrow(IllegalArgumentException::new);
        if (!identity.hasRole(Roles.ADMIN)) {
            if (!checkedEmail.equals(identity.getName())) {
                throw new IllegalArgumentException("Email address you've submitted is different from the email you have got in your Google Account");
            } else if (payload.isAdmin()) {
                throw new NoPermissionException("Only administrators can create user with admin privileges");
            }
        }

        final Map<String, String> profileFailures = ProfileValidator.validateProfile(payload);
        if (profileFailures.size() > 0) {
            return buildValidationFailedRepresentation(profileFailures);
        }

        final NewProfileCommand command = descriptors.newProfileCommand(payload);
        final Profile profile = profileRepository.register(command);

        return buildRepresentation(uriInfo, profile);
    }

    private Response buildValidationFailedRepresentation(Map<String, String> notValidatedFields) {
        final Representation halResource = rest.newRepresentation()
                .withProperty("status", "failed")
                .withProperty("fields", notValidatedFields);

        return Response.ok(halResource.toString(RepresentationFactory.HAL_JSON))
                .status(HttpStatus.BAD_REQUEST_400)
                .build();
    }

    private Response buildRepresentation(final UriInfo uriInfo, final Profile profile) {
        final URI accountLink = resourceLink(uriInfo, ProfileQuery.class, profile.getEmail());
        final Representation halResource = rest.newRepresentation()
                .withProperty("status", "created")
                .withProperty("email", profile.getEmail())
                .withLink("profile", accountLink);

        return Response.ok(halResource.toString(RepresentationFactory.HAL_JSON))
                .status(HttpStatus.CREATED_201)
                .header(HttpHeader.LOCATION.asString(), accountLink.toString())
                .build();
    }
}
