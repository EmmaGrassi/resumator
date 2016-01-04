package io.sytac.resumator.employee;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.model.enums.Nationality;
import org.eclipse.jetty.http.HttpStatus;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * Retrieve information about one employee
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("employee/{id}")
@RolesAllowed({"user"})
public class EmployeeQuery extends BaseResource {

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    @Consumes("application/json")
    public Representation fakeEmployee(@PathParam("id") final String id, @Context final UriInfo uriInfo) {
        //throw new WebApplicationException("Cannot get an employee yet, sorry!", HttpStatus.NOT_IMPLEMENTED_501);
        Employee res = new Employee("Jimi", "Hendrix", 1942, Nationality.AMERICAN, "Heaven");
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
                .withProperty("name", employee.getName())
                .withProperty("surname", employee.getSurname())
                .withProperty("nationality", employee.getNationality())
                .withProperty("current-residence", employee.getCurrentResidence())
                .withProperty("year-of-birth", employee.getYearOfBirth())
                .withLink("self", uriInfo.getRequestUri().toString());
    }

}
