package io.sytac.resumator.employee;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.model.Experience;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.security.Identity;
import io.sytac.resumator.security.Roles;
import io.sytac.resumator.security.UserPrincipal;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.naming.NoPermissionException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Lists the employees stored into the Resumator
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("employees")
@RolesAllowed(Roles.ADMIN)
public class EmployeesQuery extends BaseResource {

    private static final String REL_SELF = "self";

    private static final String REL_EMPLOYEES = "employees";

    private static final String REL_NEXT = "next";

    private static final String QUERY_PARAM_PAGE = "page";

    private static final String QUERY_PARAM_EMPLOYEE_TYPE = "type";

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
                                       @QueryParam(QUERY_PARAM_EMPLOYEE_TYPE) EmployeeType type,
                                       @UserPrincipal final Identity identity,
                                       @Context final UriInfo uriInfo) throws NoPermissionException {

        final Representation representation = rest.newRepresentation()
                .withLink(REL_SELF, uriInfo.getRequestUri())
                .withLink(REL_EMPLOYEES, resourceLink(uriInfo, EmployeesQuery.class));

        final List<Employee> allEmployees = getEmployees(identity.getOrganizationId());
        final List<Employee> filteredEmployees = allEmployees.stream()
                .filter(employee -> type == null || employee.getType() == type)
                .collect(Collectors.toList());

        final int pageNumber = Math.max(Optional.ofNullable(page).orElse(FIRST_PAGE), FIRST_PAGE);
        filteredEmployees.stream()
                .sorted(Comparator.comparing(Employee::getSurname).thenComparing(Employee::getName))
                .skip(DEFAULT_PAGE_SIZE * (pageNumber - 1))
                .limit(DEFAULT_PAGE_SIZE)
                .forEach(employee -> representation.withRepresentation(REL_EMPLOYEES, represent(employee, uriInfo)));

        if (hasNextPage(filteredEmployees.size(), pageNumber)) {
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
    	String client=getCurrentClient(employee);
        return rest.newRepresentation()
                .withProperty("email", employee.getEmail())
                .withProperty("fullName", employee.getName()+" "+employee.getSurname())
                .withProperty("client", client)
                .withProperty("role", employee.getRole())
                .withProperty("phone", employee.getPhoneNumber())
                .withLink(REL_SELF, resourceLink(uriInfo, EmployeeQuery.class, employee.getEmail()));
    }

    private List<Employee> getEmployees(final String organizationId) {
        return organizations
                .get(organizationId)
                .map(Organization::getEmployees)
                .orElse(Collections.emptyList());
    }

    private boolean hasNextPage(int numberOfEmployees, int currentPage) {
        return currentPage * DEFAULT_PAGE_SIZE < numberOfEmployees;
    }

    private URI createURIForPage(UriInfo uriInfo, int page) {
        return UriBuilder.fromUri(uriInfo.getRequestUri()).replaceQueryParam(QUERY_PARAM_PAGE, page).build();
    }
    
    private String getCurrentClient(Employee employee){
    	
    	List<Experience> experiences=Optional.ofNullable(employee.getExperiences()).orElse(new ArrayList<Experience>());
    	
    	String currentClient=experiences.stream().filter(exp->!exp.getEndDate().isPresent()).sorted(Comparator.comparing(Experience::getStartDate)).findFirst().map(Experience::getCompanyName).orElse("");
    	
    	return currentClient;
    	
   
    }
}
