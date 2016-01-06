package io.sytac.resumator;

/**
 * Enumeration of all available configuration entries
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public interface ConfigurationEntries {

    // HTTP config
    String CONTEXT_PATH = "resumator.http.context.path";
    String BASE_URI     = "resumator.http.uri";

    // Security
    String GOOGLE_CLIENT_ID        = "resumator.sec.google.client.id";
    String GOOGLE_SECRET           = "resumator.sec.google.secret";
    String GOOGLE_APPS_DOMAIN_NAME = "resumator.sec.google.domain";
    String ADMIN_ACCOUNT_LIST      = "resumator.sec.admins";

    // Advertised service details
    String SERVICE_NAME    = "resumator.service.name";
    String SERVICE_VERSION = "resumator.service.version";

    // SQL database access
    String SQL_FILES_DIR_CONFIG = "resumator.db.sql.dir";
    String SQL_DB_DRIVER        = "resumator.db.driver";
    String SQL_DB_URL           = "resumator.db.url";
    String SQL_DB_USER          = "resumator.db.user";
    String SQL_DB_PASS          = "resumator.db.password";

    // Various
    String USER_CONFIG_FILE_LOCATION = "resumator.config";
    String THREAD_POOL_SIZE = "resumator.sys.threadpool.size";
    String LOG_TAG = "resumator.logs.events.tag";
}
