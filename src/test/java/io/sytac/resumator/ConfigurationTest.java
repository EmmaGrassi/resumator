package io.sytac.resumator;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

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

}