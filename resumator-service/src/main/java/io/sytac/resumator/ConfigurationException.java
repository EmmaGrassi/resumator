package io.sytac.resumator;

/**
 * Thrown when configuration is not properly done
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class ConfigurationException extends RuntimeException {

    public ConfigurationException(final String configEntry) {
        super(String.format("%s was not properly configured", configEntry));
    }

}
