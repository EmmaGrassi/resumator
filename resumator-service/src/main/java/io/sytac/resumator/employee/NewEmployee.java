package io.sytac.resumator.employee;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.model.Organization;
import io.sytac.resumator.model.exceptions.InvalidOrganizationException;
import io.sytac.resumator.security.User;
import io.sytac.resumator.store.OrganizationRepository;
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
import java.security.Principal;

/**
 * Creates a new {@link Employee}
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("employee")
@RolesAllowed({"admin"})
public class NewEmployee extends BaseResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewEmployee.class);

    private final OrganizationRepository organizations;
    private final CommandFactory descriptors;

    @Inject
    public NewEmployee(OrganizationRepository organizations, CommandFactory descriptors) {
        this.organizations = organizations;
        this.descriptors = descriptors;
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces(RepresentationFactory.HAL_JSON)
    public Representation newEmployee(final MultivaluedMap<String, String> formParams,
                                      @Context final UriInfo uriInfo,
                                      @Context final HttpServletResponse response,
                                      @Context final SecurityContext securityContext){
        final String orgId = getOrgId(securityContext);
        final NewEmployeeCommand descriptor = descriptors.newEmployeeCommand(formParams, orgId);
        final Organization organization = organizations.get(orgId)
                                                       .orElseThrow(InvalidOrganizationException::new);
        final Employee employee = organization.addEmployee(descriptor);
        final URI employeeLink = resourceLink(uriInfo, EmployeeQuery.class, employee.getId().toString());

        response.setStatus(HttpStatus.CREATED_201);
        response.setHeader(HttpHeader.LOCATION.asString(), employeeLink.toString());
        return rest.newRepresentation()
                .withProperty("status", "created")
                .withProperty("id", employee.getId().toString())
                .withLink("employee", employeeLink);
    }

    private String getOrgId(SecurityContext securityContext) {
        final Principal user = securityContext.getUserPrincipal();
        if (user instanceof User) {
            return ((User) user).getOrganizationId();
        }

        LOGGER.warn("Tried to execute a command on a non-existing organization");
        throw new InvalidOrganizationException();
    }
}
