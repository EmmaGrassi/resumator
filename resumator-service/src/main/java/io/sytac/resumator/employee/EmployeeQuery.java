package io.sytac.resumator.employee;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.security.Roles;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * Retrieve information about one employee
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("employee/{id}")
@RolesAllowed(Roles.USER)
public class EmployeeQuery extends BaseEmployee {

    @Inject
    public EmployeeQuery(final OrganizationRepository organizations) {
        super(organizations);
    }

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Representation fakeEmployee(@PathParam("id") final String id,
                                       @Context final UriInfo uriInfo,
                                       @Context final SecurityContext securityContext) {
        String orgDomain = getOrgDomain(securityContext);
        Organization organization = getOrganizations().fromDomain(orgDomain).get();
        Employee res = organization.getEmployeeById(id);
        return represent(res, uriInfo);
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
                .withProperty("title", employee.getTitle())
                .withProperty("name", employee.getName())
                .withProperty("surname", employee.getSurname())
                .withProperty("email", employee.getEmail())
                .withProperty("phonenumber", employee.getPhonenumber())
                .withProperty("github", employee.getGithub())
                .withProperty("linkedin", employee.getLinkedin())
                .withProperty("dateOfBirth", employee.getDateOfBirth())
                .withProperty("nationality", employee.getNationality())
                .withProperty("aboutMe", employee.getAboutMe())
                .withProperty("education", employee.getEducation())
                .withProperty("courses", employee.getCourses())
                .withProperty("experience", employee.getExperience())
                .withProperty("languages", employee.getLanguages())
                .withLink("self", uriInfo.getRequestUri().toString());
    }

}
