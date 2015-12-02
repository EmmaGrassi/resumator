package io.sytac.resumator.http;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Main entry point for the API: provides hypermedia links to the other resources
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("info")
public class ServiceInfo {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() {
        return "hello, world!";
    }
}
