package io.sytac.resumator.user;

import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.security.Identity;
import io.sytac.resumator.security.Roles;
import io.sytac.resumator.security.UserPrincipal;
import org.eclipse.jetty.http.HttpStatus;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.naming.NoPermissionException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * Removes an {@link Profile}
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
@Path("profiles/{email}")
@RolesAllowed(Roles.USER)
public class RemoveProfile extends BaseResource {

    private final ProfileRepository profileRepository;
    private final CommandFactory descriptors;
    private final EventPublisher events;

    @Inject
    public RemoveProfile(final ProfileRepository profileRepository, final CommandFactory descriptors, final EventPublisher events) {
        this.profileRepository = profileRepository;
        this.descriptors = descriptors;
        this.events = events;
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doRemove(@PathParam("email") final String email,
                             @UserPrincipal final Identity identity) throws NoPermissionException {

        final String checkedEmail = Optional.ofNullable(email).orElseThrow(IllegalArgumentException::new);
        if (!identity.hasRole(Roles.ADMIN) && !checkedEmail.equals(identity.getName())) {
            throw new NoPermissionException("You don't have permissions to delete this profile");
        }

        final Profile profile = profileRepository.getProfileByEmail(email);
        if (profile == null) {
            return buildRepresentation(HttpStatus.NOT_FOUND_404);
        }

        final RemoveProfileCommand command = descriptors.removeProfileCommand(profile.getId());
        profileRepository.remove(command);

        events.publish(command);

        return buildRepresentation(HttpStatus.NO_CONTENT_204);
    }

    private Response buildRepresentation(int httpStatus) {
        return Response.status(httpStatus).build();
    }
}
