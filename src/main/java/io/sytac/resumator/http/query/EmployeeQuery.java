package io.sytac.resumator.http.query;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.model.Employee;
import io.sytac.resumator.model.EmployeeId;
import io.sytac.resumator.store.EmployeeRepository;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.Optional;

/**
 * Retrieve information about one employee
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("employee/{id}")
@RolesAllowed({"user"})
public class EmployeeQuery extends BaseResource {

    private final EmployeeRepository repository;

    @Inject
    public EmployeeQuery(final EmployeeRepository repository) {
        this.repository = repository;
    }

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    public Representation getEmployee(@PathParam("id") final String id, @Context final UriInfo uriInfo) {
        final EmployeeId employeeId = new EmployeeId(id);
        final Employee employee = repository.find(employeeId).orElseThrow(() -> new WebApplicationException(404));
        return represent(employee, uriInfo);
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
                .withProperty("year-of-birth", employee.getYearOfBirth())
                .withLink("self", uriInfo.getRequestUri().toString());
    }

}
