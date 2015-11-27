package io.sytac.resumator.store.sql;

import io.sytac.resumator.Configuration;
import org.flywaydb.core.Flyway;

import java.util.Optional;

/**
 * Manages the database schema of the Resumator, including creation of the DB and migrations
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class SchemaManager {

    public static final String SQL_FILES_DIR_CONFIG = "resumator.db.sql.dir";

    private final Flyway flyway;

    public SchemaManager(final Configuration configuration, final SqlStore sqlStore){
        // Create the Flyway instance
        flyway = new Flyway();
        flyway.setDataSource(sqlStore.getDataSource());
        monitorSQLFiles(configuration);
    }

    private void monitorSQLFiles(Configuration configuration) {
        Optional<String> location = configuration.getProperty(SQL_FILES_DIR_CONFIG);
        if(location.isPresent()) {
            flyway.setLocations(location.get());
        }
    }

    public void migrate(){
        // Start the migration
        flyway.migrate();
    }

}
