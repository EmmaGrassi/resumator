package io.sytac.resumator.http;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Main entry point for the API: provides hypermedia links to the other resources
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("info")
public class ServiceInfo {

    private final RepresentationFactory rest = new JsonRepresentationFactory();

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    public Representation helloWorld() {

        return rest.newRepresentation()
                .withProperty("app-name", "Resumator")
                .withProperty("app-version", "0.1")
                .withLink("self", "/info");
    }
}
