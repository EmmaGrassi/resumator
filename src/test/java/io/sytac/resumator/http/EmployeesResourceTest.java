package io.sytac.resumator.http;

import com.theoryinpractise.halbuilder.api.ContentRepresentation;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderReaderSupport;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Tests the Employees resource
 */
public class EmployeesResourceTest extends RESTTest {

    @Test
    public void testGetEmptyEmployees() throws Exception {
        final WebTarget target = target("employees").register(JaxRsHalBuilderReaderSupport.class);
        final ContentRepresentation response = target.request().buildGet().invoke(ContentRepresentation.class);
        assertEquals("Wrong employees count for empty store", 0, response.getResources().size());
    }
}