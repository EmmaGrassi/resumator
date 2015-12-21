package io.sytac.resumator.organization;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.command.Command;
import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.employee.Employee;
import io.sytac.resumator.employee.EmployeeQuery;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.security.Roles;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Map;

/**
 * Exposes a command API to create a new Organization
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("organizations")
@RolesAllowed({Roles.SYSADMIN})
public class NewOrganization extends BaseResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewOrganization.class);

    private final OrganizationRepository organizations;
    private final CommandFactory descriptors;
    private final EventPublisher events;

    @Inject
    public NewOrganization(OrganizationRepository organizations, CommandFactory descriptors, EventPublisher events) {
        this.organizations = organizations;
        this.descriptors = descriptors;
        this.events = events;
    }

    @POST
    @Consumes("application/json")
    @Produces(RepresentationFactory.HAL_JSON)
    public Representation createOrganization(final Map<String, String> input,
                                             @Context final UriInfo uriInfo,
                                             @Context final HttpServletResponse response,
                                             @Context final SecurityContext securityContext) {
        final NewOrganizationCommand command = descriptors.newOrganizationCommand(input);
        final Organization org = organizations.register(command);
        events.publish(command);
        return buildRepresentation(uriInfo, response, org);
    }

    private Representation buildRepresentation(final UriInfo uriInfo, final HttpServletResponse response, final Organization org) {
        final URI orgLink = resourceLink(uriInfo, OrganizationQuery.class, org.getId());

        response.setStatus(HttpStatus.CREATED_201);
        response.setHeader(HttpHeader.LOCATION.asString(), orgLink.toString());
        return rest.newRepresentation()
                .withProperty("status", "created")
                .withProperty("id", org.getId())
                .withLink("employee", orgLink);
    }
}
