package io.sytac.resumator;

import org.junit.Before;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Common code for all Resumator tests
 */
public class AbstractResumatorTest {

    @Before
    public void installTestProperties() throws URISyntaxException {
        System.setProperty("resumator.config", getTestProperties());
    }

    private String getTestProperties() throws URISyntaxException {
        return new File(this.getClass().getClassLoader().getResource("test.properties").toURI()).toString();
    }

}
