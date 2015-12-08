package io.sytac.resumator;

import com.google.common.io.Resources;
import io.sytac.resumator.store.sql.SchemaManager;
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
        return new File(Resources.getResource("test.properties").toURI()).toString();
    }

}
