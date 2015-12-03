package io.sytac.resumator.http;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import org.eclipse.jetty.http.HttpStatus;

import javax.ws.rs.*;

/**
 * REST resource modelling an {@link io.sytac.resumator.model.Employee}
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("employee")
public class EmployeeResource {

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    public Representation getEmployee() {
        throw new WebApplicationException("Cannot get an employee yet, sorry!", HttpStatus.NOT_IMPLEMENTED_501);
    }

    @POST
    @Produces(RepresentationFactory.HAL_JSON)
    public Representation storeEmployee() {
        throw new WebApplicationException("Cannot get an employee yet, sorry!", HttpStatus.NOT_IMPLEMENTED_501);
    }

}