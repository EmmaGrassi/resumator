package io.sytac.resumator.employee;

import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.model.exceptions.InvalidOrganizationException;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.security.Roles;
import io.sytac.resumator.security.User;
import io.sytac.resumator.security.UserPrincipal;
import org.eclipse.jetty.http.HttpStatus;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.naming.NoPermissionException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Optional;

/**
 * Removes an {@link Employee}
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
@Path("employees/{email}")
@RolesAllowed(Roles.USER)
public class RemoveEmployee extends BaseResource {

    private final OrganizationRepository organizations;
    private final CommandFactory descriptors;
    private final EventPublisher events;

    @Inject
    public RemoveEmployee(final OrganizationRepository organizations, final CommandFactory descriptors, final EventPublisher events) {
        this.organizations = organizations;
        this.descriptors = descriptors;
        this.events = events;
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeEmployee(@PathParam("email") final String email,
                                   @UserPrincipal final User user) throws NoPermissionException {

        final String checkedEmail = Optional.ofNullable(email).orElseThrow(IllegalArgumentException::new);
        final Organization organization = organizations.get(user.getOrganizationId()).orElseThrow(InvalidOrganizationException::new);

        if (!user.hasRole(Roles.ADMIN) && !checkedEmail.equals(user.getName())) {
            throw new NoPermissionException("You don't have permissions to delete this profile");
        }

        final Employee employee = organization.getEmployeeByEmail(email);
        if (employee == null) {
            return buildRepresentation(HttpStatus.NOT_FOUND_404);
        }

        final RemoveEmployeeCommand command = descriptors.removeEmployeeCommand(employee.getId(), organization.getDomain());
        removeEmployee(organization.getDomain(), command);

        events.publish(command);

        return buildRepresentation(HttpStatus.NO_CONTENT_204);
    }

    private Response buildRepresentation(int httpStatus) {
        return Response.status(httpStatus).build();
    }

    private void removeEmployee(String domain, RemoveEmployeeCommand command) {
        organizations.fromDomain(domain).get().removeEmployee(command);
    }
}
