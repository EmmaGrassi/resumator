package io.sytac.resumator.store.sql;

import io.sytac.resumator.AbstractResumatorTest;
import io.sytac.resumator.Configuration;
import io.sytac.resumator.events.LocalEventPublisher;
import org.junit.Before;
import org.junit.Test;

import static io.sytac.resumator.ConfigurationEntries.*;
import static org.mockito.Mockito.mock;

/**
 * Test the handling of DB schemas
 */
public class SchemaManagerTest extends AbstractResumatorTest {

    private SchemaManager manager;

    @Before
    public void setUp() throws Exception {
        System.setProperty(SQL_FILES_DIR_CONFIG, "classpath:db/h2/migration");
        Configuration properties = new Configuration();
        manager = new SchemaManager(properties, new SqlStore(properties, mock(LocalEventPublisher.class), null));
    }

    @Test
    public void testMigrate() throws Exception {
        manager.migrate();
        // No exceptions -> we're good!
    }
}