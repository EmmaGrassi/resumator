package io.sytac.resumator.http;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by skuro on 02/12/15.
 */
@Path("info")
public class ServiceInfo {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() {
        return "hello, world!";
    }
}
