package io.sytac.resumator.store.sql;

import io.sytac.resumator.AbstractResumatorTest;
import io.sytac.resumator.Configuration;
import io.sytac.resumator.model.Event;
import io.sytac.resumator.store.StoreException;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

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
    public void connectedToTheTestDB(){
        assertEquals(configuration.getProperty("resumator.db.user").get(), "sa");
    }

    @Test
    public void canStoreOneEvent() {
        Event event = new Event(UUID.randomUUID().toString(), "constant", 1l, 1l, "test".getBytes(), new Date(), "test");
        store.put(event);
    }
}