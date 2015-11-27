package io.sytac.resumator.store.sql;

import io.sytac.resumator.AbstractResumatorTest;
import io.sytac.resumator.Configuration;
import io.sytac.resumator.model.Event;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        new SchemaManager(configuration, store).migrate();
    }

    @Test
    public void connectedToTheTestDB(){
        assertEquals(configuration.getProperty("resumator.db.user").get(), "sa");
    }

    @Test
    public void canStoreOneEvent() {
        Event event = createRandomEvent();
        store.put(event);
    }

    private Event createRandomEvent() {
        return new Event(UUID.randomUUID().toString(), "constant", 1l, 1l, "test".getBytes(), new Timestamp(0), "test");
    }

    @Test
    public void canRetrieveOneEvent() {
        assertTrue("DB is not clean at the start of a test", store.getAll().size() == 0);
        Event event = createRandomEvent();
        store.put(event);
        Event retrieved = store.getAll().get(0);
        assertEquals("Retrieved event doesn't match with the stored event", event.getId(), retrieved.getId());
    }

    @After
    public void cleanDB(){
        store.removeAll();
    }
}