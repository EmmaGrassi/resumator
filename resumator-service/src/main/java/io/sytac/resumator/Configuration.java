package io.sytac.resumator;

import io.sytac.resumator.exception.ResumatorInternalException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

import static io.sytac.resumator.ConfigurationEntries.USER_CONFIG_FILE_LOCATION;

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
@Slf4j
public class Configuration {
    
    public static final String LIST_SEPARATOR = ":";

    private static final String STATIC_CONFIG_LOCATION = "resumator.properties";
    private static final File DEFAULT_CONFIG_LOCATION = Paths.get(System.getProperty("user.home"), ".resumator", "config.properties").toFile();
    private final Properties defaultProperties;
    private final Properties userProperties;

    public Configuration() {
        defaultProperties = readDefaultProperties();
        userProperties = getUserProperties();
    }

    private Properties readDefaultProperties() {
        final InputStream stream = getDefaultConfigurationResource();
        // skipping proper null check here as static config should be always embedded
        assert stream != null;
        return readProperties(stream);
    }

    private InputStream getDefaultConfigurationResource() {
        return this.getClass().getClassLoader().getResourceAsStream(STATIC_CONFIG_LOCATION);
    }

    private Properties readProperties(final File propertiesFile) {
        final FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(propertiesFile);
            return readProperties(fileInputStream);
        } catch (FileNotFoundException e) {
            log.error("Couldn't read properties file, ignoring");
        }

        return new Properties();
    }

    private Properties readProperties(final InputStream stream) {
        final Properties properties = new Properties();
        try(final InputStream propertiesStream = stream) {
            properties.load(propertiesStream);
        } catch (FileNotFoundException e) {
            // cannot happen, file should be retrieved through class loader
        } catch (IOException e) {
            log.error("Cannot read the static configuration file, exiting");
            throw new ResumatorInternalException("Cannot read the static configuration file",e);
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
            if(option.isPresent()) {
                return option;
            }
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
        String customLocation = System.getProperty(USER_CONFIG_FILE_LOCATION);
        if(customLocation != null) {
            propertiesFile = Paths.get(customLocation).toFile();
            if(!propertiesFile.isFile()) {
                log.warn("No configuration file was found at the provided location: {}", customLocation);
                return Optional.empty();
            }
        } else {
            if(defaultFile.isFile()) {
                propertiesFile = defaultFile;
            } else {
                log.debug("No configuration file was found");
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
                return null;
            }
        });
    }

    /**
     * Retrieves a configuration entry as an Integer
     *
     * @param key The key of the configuration to retrieve as an Integer
     * @return The Integer property if found
     */
    public Optional<Integer> getIntegerProperty(final String key) {
        final Optional<String> string = getProperty(key);
        return string.map(value -> {
            try {
                return Integer.valueOf(value);
            } catch (NumberFormatException e) {
                log.warn("Cannot parse config entry {} as integer, ignoring: {}", key, value);
                return null;
            }
        });
    }

    /**
     * Returns the list of colon-separated values found as the configuration options for the provided key
     *
     * @param key The key of the configuration to retrieve as a List
     * @return The list of values found at key, or an empty list
     */
    public List<String> getListProperty(final String key) {
        return getProperty(key)
                .map(str -> Arrays.asList(str.split(LIST_SEPARATOR)))
                .orElse(Collections.emptyList());
    }
}
