package io.sytac.resumator.http.query;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;
import io.sytac.resumator.http.BaseResource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * Lists the employees stored into the Resumator
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("employees")
@RolesAllowed({"user"})
public class EmployeesQuery extends BaseResource {

    private final RepresentationFactory rest = new JsonRepresentationFactory();

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    public Representation getEmployees(@Context final UriInfo uriInfo) {
        return rest.newRepresentation()
                .withLink("self", uriInfo.getRequestUri())
                .withLink("employees", resourceLink(uriInfo, EmployeesQuery.class));
    }

}