package io.sytac.resumator.employee;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.model.exceptions.InvalidOrganizationException;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.security.Roles;
import org.eclipse.jetty.http.HttpStatus;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * Removes an {@link Employee}
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
@Path("employees/{id}")
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
    @Produces({RepresentationFactory.HAL_JSON, MediaType.APPLICATION_JSON})
    public Response removeEmployee(@PathParam("id") final String employeeId,
                                   @Context final SecurityContext securityContext) {

        final Organization organization = organizations.get(getUser().getOrganizationId())
                .orElseThrow(InvalidOrganizationException::new);

        if (organization.getEmployeeById(employeeId) == null) {
            return buildRepresentation(HttpStatus.NOT_FOUND_404);
        }

        final RemoveEmployeeCommand command = descriptors.removeEmployeeCommand(employeeId, organization.getDomain());
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
