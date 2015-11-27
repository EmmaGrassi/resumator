package io.sytac.resumator.store.sql;

import io.sytac.resumator.AbstractResumatorTest;
import io.sytac.resumator.Configuration;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test SQL connections
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class SqlStoreTest extends AbstractResumatorTest {

    private SqlStore store;
    private Configuration configuration;

    @Before
    public void setUp() throws Exception {
        configuration = new Configuration();
        store = new SqlStore(configuration);
    }

    @Test
    public void testDBConnection(){
        assertEquals(configuration.getProperty("resumator.db.user").get(), "sa");
    }
}