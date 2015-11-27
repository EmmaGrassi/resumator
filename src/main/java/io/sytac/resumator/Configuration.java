package io.sytac.resumator;

import java.io.File;
import java.util.Optional;
import java.util.Properties;

/**
 * A simple interface towards the application configuration. Will look for configuration entries in the following
 * sources, in order:
 *
 * - System.getProperty()
 * - a user-provided configuration file
 * - the default properties file embedded in the application
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class Configuration {

    private static final File DEFAULT_CONFIG_LOCATION = new File(System.getProperty("user.home"), ".credentials/resumator");
//    private final Properties defaultProperties;
//    private final Properties userProperties;

    public Configuration() {
//        defaultProperties = readDefaultProperties();
//        File configFile = chooseConfigFileLocation();
//        userProperties = readProperties();
    }

    /**
     * Retrieves the configuration entry associated with key from the configured configuration entries
     *
     * @param key The configuration key to look
     * @return The configuration entry found, or nothing
     */
    public Optional<String> getProperty(String key) {
        return Optional.ofNullable(System.getProperty(key));
    }
}
