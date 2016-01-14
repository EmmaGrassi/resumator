package io.sytac.resumator.store.sql;

import io.sytac.resumator.AbstractResumatorTest;
import io.sytac.resumator.Configuration;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.model.Event;
import io.sytac.resumator.store.IllegalInsertOrderException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.UUID;

import static io.sytac.resumator.ConfigurationEntries.SQL_DB_USER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

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
        store = new SqlStore(configuration, mock(EventPublisher.class));
        store.setReadOnly(false);
        new SchemaManager(configuration, store).migrate();
    }

    @Test
    public void connectedToTheTestDB(){
        assertEquals(configuration.getProperty(SQL_DB_USER).get(), "sa");
    }

    @Test
    public void canStoreOneEvent() {
        Event event = createRandomEvent();
        store.put(event);
    }

    private Event createRandomEvent() {
        return new Event(UUID.randomUUID().toString(), 1L, "bogus: 汉语 / 漢語", new Timestamp(0), "NewEmployeeCommand");
    }

    private Event createRandomEvent(final Long insertSequence) {
        return new Event(UUID.randomUUID().toString(), insertSequence, "bogus", new Timestamp(0), "test");
    }

    @Test
    public void canRetrieveOneEvent() {
        assertTrue("DB is not clean at the start of a test", store.getAll().size() == 0);
        Event event = createRandomEvent();
        store.put(event);
        Event retrieved = store.getAll().get(0);
        assertEquals("Retrieved event doesn't match with the stored event", event.getId(), retrieved.getId());
        assertEquals("Retrieved event payload is wrong!", event.getPayload(), retrieved.getPayload());
    }

    @Test(expected = IllegalInsertOrderException.class)
    public void insertSequenceMustBeUnique(){
        Event event1 = createRandomEvent(1L);
        Event event2 = createRandomEvent(1L);

        store.put(event1);
        store.put(event2);
    }

    @Test(expected = IllegalStateException.class)
    public void cannotStoreInReadOnlyMode(){
        store.setReadOnly(true);
        store.put(createRandomEvent());
    }


    @After
    public void cleanDB(){
        store.removeAll();
    }
}