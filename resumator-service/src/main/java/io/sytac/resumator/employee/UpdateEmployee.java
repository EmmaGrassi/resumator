package io.sytac.resumator.employee;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.model.exceptions.InvalidOrganizationException;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.security.Roles;
import io.sytac.resumator.security.User;
import io.sytac.resumator.security.UserPrincipal;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

/**
 * Creates a new {@link Employee}
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("employees/{id}")
@RolesAllowed(Roles.USER)
public class UpdateEmployee extends BaseResource {

    private final OrganizationRepository organizations;
    private final CommandFactory descriptors;
    private final EventPublisher events;

    @Inject
    public UpdateEmployee(final OrganizationRepository organizations, final CommandFactory descriptors, final EventPublisher events) {
        this.organizations = organizations;
        this.descriptors = descriptors;
        this.events = events;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({RepresentationFactory.HAL_JSON, MediaType.APPLICATION_JSON})
    public Response updateEmployee(@PathParam("id") final String employeeId,
                                   final EmployeeCommandPayload payload,
                                   @UserPrincipal final User user,
                                   @Context final UriInfo uriInfo) {

        Organization organization = organizations.get(user.getOrganizationId())
                .orElseThrow(InvalidOrganizationException::new);
        String domain = organization.getDomain();

        final UpdateEmployeeCommand command = descriptors.updateEmployeeCommand(employeeId, payload, domain);
        Employee updatedEmployee = organization.updateEmployee(command);
        events.publish(command);

        return buildRepresentation(uriInfo, updatedEmployee);
    }

    private Response buildRepresentation(final UriInfo uriInfo, final Employee employee) {
        final URI employeeLink = resourceLink(uriInfo, EmployeeQuery.class, employee.getId());
        final Representation halResource = rest.newRepresentation()
                .withProperty("status", "updated")
                .withProperty("id", employee.getId())
                .withLink("employee", employeeLink);

        return Response.ok(halResource.toString(RepresentationFactory.HAL_JSON))
                .status(HttpStatus.OK_200)
                .header(HttpHeader.LOCATION.asString(), employeeLink.toString())
                .build();
    }
}
