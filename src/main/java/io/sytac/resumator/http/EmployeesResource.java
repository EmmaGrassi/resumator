package io.sytac.resumator.http;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Lists the employees stored into the Resumator
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("employees")
public class EmployeesResource {

    private final RepresentationFactory rest = new JsonRepresentationFactory();

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    public Representation getEmployees(@Context final UriInfo uriInfo) {
        return rest.newRepresentation()
                .withLink("self", uriInfo.getRequestUri())
                .withLink("employees", resourceLink(uriInfo, EmployeesResource.class));
    }

    /**
     * Build the URI for the link connected to the provided resource
     *
     * @param uriInfo  The base URI for the service
     * @param resource The resource for which to build the link
     * @return The link to the provided resoruce
     */
    protected URI resourceLink(final UriInfo uriInfo, final Class<?> resource) {
        return UriBuilder.fromUri(uriInfo.getBaseUri())
                .path(resource)
                .build();
    }

}
