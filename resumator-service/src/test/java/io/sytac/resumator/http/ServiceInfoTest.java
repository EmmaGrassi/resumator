package io.sytac.resumator.http;

import com.theoryinpractise.halbuilder.api.ContentRepresentation;
import com.theoryinpractise.halbuilder.api.Link;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderReaderSupport;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Testing the Service info REST endpoint
 */
public class ServiceInfoTest extends RESTTest {

    @Test
    public void canGetTheResumatorInfo() {
        final WebTarget target = target("info").register(JaxRsHalBuilderReaderSupport.class);
        final ContentRepresentation response = target.request().buildGet().invoke(ContentRepresentation.class);
        assertEquals("The application name was not found", "Sytac Resumator", response.getProperties().get("app-name"));
    }

    @Test
    public void canNavigateToEmployeesList(){
        final WebTarget target = target("info").register(JaxRsHalBuilderReaderSupport.class);
        final ContentRepresentation response = target.request().buildGet().invoke(ContentRepresentation.class);
        final Link employees = response.getLinkByRel("employees");
        assertEquals("The Employees API is not exposed by the service document", "http://localhost:9998/employees", employees.getHref());
    }

    @Test
    public void advertisesCommitHash(){
        final WebTarget target = target("info").register(JaxRsHalBuilderReaderSupport.class);
        final ContentRepresentation response = target.request().buildGet().invoke(ContentRepresentation.class);
        final String hash = response.getProperties().get("hash").toString();
        assertNotEquals("Maven resource filtering is not properly configured", "${buildNumber}", hash);
    }

}