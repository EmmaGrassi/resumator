package io.sytac.resumator.http;

import com.theoryinpractise.halbuilder.api.ContentRepresentation;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;

import static org.junit.Assert.assertEquals;

/**
 * Testing the Service info REST endpoint
 */
public class ServiceInfoTest extends RESTTest {

    @Test
    public void canGetTheResumatorInfo() {
        final WebTarget target = target("info").register(HALMessageBodyReader.class);
        final ContentRepresentation response = target.request().buildGet().invoke(ContentRepresentation.class);
        assertEquals("Wooot", "The Resumator", response.getProperties().get("app-name"));
    }

}