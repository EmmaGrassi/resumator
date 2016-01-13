package io.sytac.resumator.employee;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.model.enums.Nationality;
import io.sytac.resumator.model.exceptions.InvalidOrganizationException;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.security.Roles;
import io.sytac.resumator.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.security.Principal;
import java.util.Optional;

/**
 * Retrieve information about one employee
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("employee/{id}")
@RolesAllowed(Roles.USER)
public class EmployeeQuery extends BaseResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeQuery.class);

    private final OrganizationRepository organizations;

    @Inject
    public EmployeeQuery(final OrganizationRepository organizations) {
        this.organizations = organizations;
    }

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Representation fakeEmployee(@PathParam("id") final String id,
                                       @Context final UriInfo uriInfo,
                                       @Context final SecurityContext securityContext) {
        //throw new WebApplicationException("Cannot get an employee yet, sorry!", HttpStatus.NOT_IMPLEMENTED_501);
//        Employee res = new Employee("Jimi", "Hendrix", 1942, Nationality.AMERICAN, "Heaven");

        Organization organization = organizations.fromDomain(getOrgDomain(securityContext)).get();
        Employee res = organization.getEmployeeById(id);

        return represent(res, uriInfo);
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

    /**
     * Translates an {@link Employee} into its HAL representation
     *
     * @param employee The employee to represent
     * @param uriInfo  The current REST endpoint information
     * @return The {@link Representation} of the {@link Employee}
     */
    private Representation represent(final Employee employee, final UriInfo uriInfo) {

        return rest.newRepresentation()
                .withProperty("id", employee.getId().toString())
                .withProperty("name", employee.getName())
                .withProperty("surname", employee.getSurname())
                .withProperty("nationality", employee.getNationality())
                .withProperty("current-residence", employee.getCurrentResidence())
                .withProperty("yearOfBirth", employee.getYearOfBirth())
                .withLink("self", uriInfo.getRequestUri().toString());
    }

}
