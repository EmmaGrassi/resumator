package io.sytac.resumator.http.query;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.model.Employee;
import io.sytac.resumator.model.EmployeeId;
import io.sytac.resumator.store.EmployeeRepository;

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
public class EmployeeQuery extends BaseResource {

    private final EmployeeRepository repository;

    @Inject
    public EmployeeQuery(final EmployeeRepository repository) {
        this.repository = repository;
    }

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    public Employee getEmployee(@PathParam("id") final String id, @Context final UriInfo uriInfo) {
        final EmployeeId employeeId = new EmployeeId(id);
        final Optional<Employee> employee = repository.find(employeeId);
        return employee.orElseThrow(() -> new WebApplicationException(404));
    }

}
