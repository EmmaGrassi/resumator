package io.sytac.resumator.employee;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.security.Roles;
import io.sytac.resumator.security.User;
import io.sytac.resumator.security.UserPrincipal;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.*;

/**
 * Lists the employees stored into the Resumator
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("employees")
@RolesAllowed(Roles.USER)
public class EmployeesQuery extends BaseResource {

    private static final String REL_SELF = "self";

    private static final String REL_EMPLOYEES = "employees";

    private static final String REL_NEXT = "next";

    private static final String QUERY_PARAM_PAGE = "page";

    private static final int FIRST_PAGE = 1;

    static final int DEFAULT_PAGE_SIZE = 25;

    private OrganizationRepository organizations;

    @Inject
    public EmployeesQuery(final OrganizationRepository organizations) {
        this.organizations = organizations;
    }

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    public Representation getEmployees(@QueryParam(QUERY_PARAM_PAGE) Integer page,
                                       @UserPrincipal final User user,
                                       @Context final UriInfo uriInfo) {
        int pageNumber = Math.max(Optional.ofNullable(page).orElse(FIRST_PAGE), FIRST_PAGE);

        Representation representation = rest.newRepresentation()
                .withLink(REL_SELF, uriInfo.getRequestUri())
                .withLink(REL_EMPLOYEES, resourceLink(uriInfo, EmployeesQuery.class));

        List<Employee> allEmployees = organizations
                .get(user.getOrganizationId())
                .map(Organization::getEmployees)
                .orElse(Collections.emptyList());

        allEmployees
                .stream()
                .sorted(Comparator.comparing(Employee::getSurname).thenComparing(Employee::getName))
                .skip(DEFAULT_PAGE_SIZE * (pageNumber - 1))
                .limit(DEFAULT_PAGE_SIZE)
                .forEach(employee -> representation.withRepresentation(REL_EMPLOYEES, represent(employee, uriInfo)));

        if (hasNextPage(allEmployees.size(), pageNumber)) {
            representation.withLink(REL_NEXT, createURIForPage(uriInfo, pageNumber + 1));
        }

        return representation;
    }

    /**
     * Translates an {@link Employee} coarsely into a HAL representation
     *
     * @param employee The employee to represent
     * @param uriInfo  The current REST endpoint information
     * @return The {@link Representation} of the {@link Employee}
     */
    private Representation represent(final Employee employee, final UriInfo uriInfo) {
        return rest.newRepresentation()
                .withProperty("id", employee.getId())
                .withProperty("name", employee.getName())
                .withProperty("surname", employee.getSurname())
                .withLink(REL_SELF, resourceLink(uriInfo, EmployeeQuery.class, employee.getId()));
    }

    private boolean hasNextPage(int numberOfEmployees, int currentPage) {
        return currentPage * DEFAULT_PAGE_SIZE < numberOfEmployees;
    }

    private URI createURIForPage(UriInfo uriInfo, int page) {
        return UriBuilder.fromUri(uriInfo.getAbsolutePath()).queryParam(QUERY_PARAM_PAGE, page).build();
    }
}
