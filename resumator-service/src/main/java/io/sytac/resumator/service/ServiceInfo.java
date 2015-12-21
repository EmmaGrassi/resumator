package io.sytac.resumator.service;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.Configuration;
import io.sytac.resumator.employee.EmployeesQuery;
import io.sytac.resumator.http.BaseResource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import static io.sytac.resumator.ConfigurationEntries.*;

/**
 * Main entry point for the API: provides hypermedia links to the other resources
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("info")
public class ServiceInfo extends BaseResource {

    private final Configuration config;

    @Inject
    public ServiceInfo(final Configuration configuration) {
        this.config = configuration;
    }

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    public Representation helloWorld(@Context final UriInfo uriInfo) {
        return rest.newRepresentation()
                .withProperty("app-name", config.getProperty(SERVICE_NAME).orElse("--"))
                .withProperty("app-version", config.getProperty(SERVICE_VERSION).orElse("--"))
                .withLink("self", uriInfo.getRequestUri())
                .withLink("employees", resourceLink(uriInfo, EmployeesQuery.class));
    }
}
