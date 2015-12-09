package io.sytac.resumator.http.query;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.http.BaseResource;
import org.eclipse.jetty.http.HttpStatus;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;

/**
 * REST resource modelling an {@link io.sytac.resumator.model.Employee}
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("employee/{id}")
@RolesAllowed("user")
public class EmployeeResource extends BaseResource {

    @POST
    @Produces(RepresentationFactory.HAL_JSON)
    public Representation storeEmployee() {
        throw new WebApplicationException("Cannot get an employee yet, sorry!", HttpStatus.NOT_IMPLEMENTED_501);
    }

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    public Representation fakeEmployee(@PathParam("id") final String id) {
        if ("666".equals(id)) {
            return rest.newRepresentation()
                    .withProperty("name", "foo")
                    .withProperty("surname", "bar");
        }

        throw new WebApplicationException("Cannot get an employee yet, sorry!", HttpStatus.NOT_IMPLEMENTED_501);
    }

}
