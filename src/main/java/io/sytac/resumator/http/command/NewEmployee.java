package io.sytac.resumator.http.command;

import io.sytac.resumator.model.Employee;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Creates a new {@link Employee}
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("employee")
@RolesAllowed({"admin"})
public class NewEmployee {

    @POST
    @Consumes("application/x-www-form-urlencoded")
    public void newEmployee(final MultivaluedMap formParams){

    }
}
