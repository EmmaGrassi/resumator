package io.sytac.resumator.http.query;

import com.theoryinpractise.halbuilder.api.ContentRepresentation;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderReaderSupport;
import io.sytac.resumator.http.RESTTest;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;

import static org.junit.Assert.assertEquals;

/**
 * Tests the Employees resource
 */
public class EmployeesQueryTest extends RESTTest {

    @Test
    public void testGetEmptyEmployees() throws Exception {
        final WebTarget target = target("employees").register(JaxRsHalBuilderReaderSupport.class);
        final ContentRepresentation response = target.request().buildGet().invoke(ContentRepresentation.class);
        assertEquals("Wrong employees count for empty store", 0, response.getResources().size());
    }
}