package io.sytac.resumator;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import static org.junit.Assert.*;

import static io.sytac.resumator.ConfigurationEntries.*;

/**
 * Test class for {@link Configuration}
 */
public class ConfigurationTest {

    private Configuration configuration;

    @Before
    public void setup(){
        configuration = new Configuration();
    }

    @Test
    public void canReadSystemProperties(){
        String random = "resumator-" + Long.toString(new Random().nextLong());
        System.setProperty(random, "Check!");

        String property = configuration.getProperty(random).get();
        assertEquals("System properties are not read properly", "Check!", property);
    }

    @Test
    public void systemPropertiesWinOverDefault(){
        String serviceName = configuration.getProperty(SERVICE_NAME).get();
        assertEquals("Static configuration was not found", "The Resumator", serviceName);

        System.setProperty("resumator.service.name", "foobar");
        serviceName = configuration.getProperty(SERVICE_NAME).get();
        assertEquals("System properties don't override static default properties", "foobar", serviceName);
        System.clearProperty(SERVICE_NAME);
    }

    @Test
    public void canGetURIsOutOfConfig() throws URISyntaxException {
        System.setProperty(BASE_URI, "http://1.1.1.1:8080/resumator");
        URI uri = configuration.getURIProperty(BASE_URI).get();
        assertEquals("URIs are not properly supported", new URI("http://1.1.1.1:8080/resumator"), uri);
        System.clearProperty(BASE_URI);
    }

}