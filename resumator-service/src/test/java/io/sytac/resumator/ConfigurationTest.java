package io.sytac.resumator;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static io.sytac.resumator.ConfigTestUtils.withConfig;
import static io.sytac.resumator.ConfigurationEntries.BASE_URI;
import static io.sytac.resumator.ConfigurationEntries.SERVICE_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
        final String random = "resumator-" + Long.toString(new Random().nextLong());
        final String value = "Check!";

        withConfig(() -> {
            String property = configuration.getProperty(random).get();
            assertEquals("System properties are not read properly", value, property);
            return true;
        }, random, value);
    }

    @Test
    public void systemPropertiesWinOverDefault(){
        final String serviceName = configuration.getProperty(SERVICE_NAME).get();
        assertEquals("Static configuration was not found", "The Resumator", serviceName);

        final String override = "foobar";
        withConfig(() -> {
            final String overridden = configuration.getProperty(SERVICE_NAME).get();
            assertEquals("System properties don't override static default properties", override, overridden);
            return true;
        }, "resumator.service.name", override);
    }

    @Test
    public void canGetURIsOutOfConfig() {
        final String fakeUrl = "http://1.1.1.1:8080/resumator";

        withConfig(() -> {
            URI uri = configuration.getURIProperty(BASE_URI).get();
            try {
                assertEquals("URIs are not properly supported", new URI(fakeUrl), uri);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            return true;
        }, BASE_URI, "http://1.1.1.1:8080/resumator");
    }

    @Test
    public void canGetListOutOfConfig() {
        final String listPropKey = "some.list";
        final String listPropVal = "one;two;three";

        withConfig(() -> {
            final String prop = configuration.getListProperty(listPropKey).stream().collect(Collectors.joining(":"));
            assertEquals("Wrong list property handling", listPropVal, prop);
            return true;
        }, listPropKey, listPropVal);
    }

    @Test
    public void canGetIntegerOutOfConfig() {
        final String propKey = "some.int";
        final String propVal = "42";

        withConfig(() -> {
            final Integer prop = configuration.getIntegerProperty(propKey).get();
            assertEquals("Wrong list property handling", new Integer(42), prop);

            final Optional<Integer> empty = configuration.getIntegerProperty("nonexistent");
            assertFalse("Nonexistent property did not result in an empty integer config", empty.isPresent());

            return true;
        }, propKey, propVal);
    }

    @Test
    public void malformedIntegerAreDetected() {
        final String propKey = "some.int";
        final String propVal = "42-wooot!";

        withConfig(() -> {
            final Optional<Integer> nonexistent = configuration.getIntegerProperty("nonexistent");
            assertFalse("Malformed number property did not result in an empty config", nonexistent.isPresent());
            return true;
        }, propKey, propVal);
    }

}