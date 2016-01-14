package io.sytac.resumator.employee;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.model.exceptions.InvalidOrganizationException;
import io.sytac.resumator.security.Roles;
import io.sytac.resumator.security.User;
import io.sytac.resumator.organization.OrganizationRepository;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.net.URI;
import java.security.Principal;
import java.util.Map;

/**
 * Creates a new {@link Employee}
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("employee")
@RolesAllowed(Roles.USER)
public class NewEmployee extends BaseResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewEmployee.class);

    private final OrganizationRepository organizations;
    private final CommandFactory descriptors;
    private final EventPublisher events;

    @Inject
    public NewEmployee(final OrganizationRepository organizations, final CommandFactory descriptors, final EventPublisher events) {
        this.organizations = organizations;
        this.descriptors = descriptors;
        this.events = events;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({RepresentationFactory.HAL_JSON, MediaType.APPLICATION_JSON})
    public Response newEmployee(final Map<String, String> input,
                                      @Context final UriInfo uriInfo,
                                      @Context final SecurityContext securityContext) {

        final String organizationDomain = getOrgDomain(securityContext);
        final NewEmployeeCommand command = descriptors.newEmployeeCommand(input, organizationDomain);
        final Employee employee = addEmployee(organizationDomain, command);
        events.publish(command);
        return buildRepresentation(uriInfo, employee);
    }

    private Response buildRepresentation(final UriInfo uriInfo, final Employee employee) {
        final URI employeeLink = resourceLink(uriInfo, EmployeeQuery.class, employee.getId().toString());
        final Representation halResource = rest.newRepresentation()
                .withProperty("status", "created")
                .withProperty("id", employee.getId().toString())
                .withLink("employee", employeeLink);

        return Response.ok(halResource.toString(RepresentationFactory.HAL_JSON))
                .status(HttpStatus.CREATED_201)
                .header(HttpHeader.LOCATION.asString(), employeeLink.toString())
                .build();
    }

    private Employee addEmployee(String orgDomain, NewEmployeeCommand descriptor) {
        Organization organization = organizations.fromDomain(orgDomain).get();
        return organization.addEmployee(descriptor);
    }

    private String getOrgId(SecurityContext securityContext) {
        final Principal user = securityContext.getUserPrincipal();
        if (user instanceof User) {
            return ((User) user).getOrganizationId();
        }

        LOGGER.warn("Tried to execute a command on a non-existing organization");
        throw new InvalidOrganizationException();
    }

    private String getOrgDomain(SecurityContext securityContext) {
        String organizationId = getOrgId(securityContext);
        Organization organization = organizations.get(organizationId)
                .orElseThrow(InvalidOrganizationException::new);

        return organization.getDomain();
    }
}
