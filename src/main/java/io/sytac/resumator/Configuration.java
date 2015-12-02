package io.sytac.resumator;

import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

    private static final String STATIC_CONFIG_LOCATION = "resumator.properties";
    private static final File DEFAULT_CONFIG_LOCATION = Paths.get(System.getProperty("user.home"), ".resumator", "config.properties").toFile();
    private final Properties defaultProperties;
    private final Properties userProperties;

    public Configuration() {
        defaultProperties = readDefaultProperties();
        userProperties = getUserProperties();
    }

    private Properties readDefaultProperties() {
        URL url = Resources.getResource(STATIC_CONFIG_LOCATION);
        try {
            // skipping proper null check here as static config should be always embedded
            assert url != null;
            File staticConfig = new File(url.toURI());
            return readProperties(staticConfig);
        } catch (URISyntaxException e) {
            LOGGER.error("Cannot find the static configuration file, exiting");
            throw new IllegalStateException();
        }
    }

    private Properties readProperties(final File propertiesFile) {
        final Properties properties = new Properties();
        try(final InputStream propertiesStream = new FileInputStream(propertiesFile)) {
            properties.load(propertiesStream);
        } catch (FileNotFoundException e) {
            // cannot happen, file should be retrieved through class loader
        } catch (IOException e) {
            LOGGER.error("Cannot read the static configuration file, exiting");
            throw new IllegalStateException(e);
        }

        return properties;
    }

    /**
     * Retrieves the configuration entry associated with key from the configured configuration entries
     *
     * @param key The configuration key to look
     * @return The configuration entry found, or nothing
     */
    public Optional<String> getProperty(final String key) {

        return or(Optional.ofNullable(System.getProperty(key)),
                  Optional.ofNullable(userProperties.getProperty(key)),
                  Optional.ofNullable(defaultProperties.getProperty(key)));
    }

    /**
     * Returns the first {@link Optional} value that's present, or an empty one if none is found
     *
     * @param options The optionals to process
     * @param <T> The type of the optional
     * @return The first optional which is present, or empty
     */
    @SafeVarargs
    static <T> Optional<T> or(Optional<T>... options) {
        for(Optional<T> option : options) {
            if(option.isPresent()) return option;
        }

        return Optional.empty();
    }

    private Properties getUserProperties() {
        Optional<File> properties = getCustomLocationOr(DEFAULT_CONFIG_LOCATION);
        if(properties.isPresent()) {
            return readProperties(properties.get());
        }

        return new Properties();
    }

    private Optional<File> getCustomLocationOr(final File defaultFile) {
        File propertiesFile;
        String customLocation = System.getProperty("resumator.config");
        if(customLocation != null) {
            propertiesFile = Paths.get(customLocation).toFile();
            if(!propertiesFile.isFile()) {
                LOGGER.warn("No configuration file was found at the provided location: {}", customLocation);
                return Optional.empty();
            }
        } else {
            if(defaultFile.isFile()) {
                propertiesFile = defaultFile;
            } else {
                LOGGER.debug("No configuration file was found");
                return Optional.empty();
            }
        }

        return Optional.of(propertiesFile);
    }

    /**
     * Retrieves a configuration entry as a {@link URL}
     *
     * @param key The key of the configuration to retrieve as a {@link URL}
     * @return The URL property if found
     */
    public Optional<URI> getURIProperty(String key) {
        final Optional<String> string = getProperty(key);
        return string.map(value -> {
            try {
                return new URI(value);
            } catch (URISyntaxException e) {
                throw new ConfigurationException(key);
            }
        });
    }
}
