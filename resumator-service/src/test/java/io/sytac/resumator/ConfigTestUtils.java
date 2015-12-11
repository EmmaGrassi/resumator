package io.sytac.resumator;

import java.util.*;
import java.util.function.Supplier;

import static org.junit.Assert.assertTrue;

/**
 * Some handy methods to work with Configuration while testing stuff
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class ConfigTestUtils {

    /**
     * Group properties by pairs, using the first element as the configuration key and the second as the config value.
     * The function is run within a context where the configuration includes the supplied properties
     * @param test       The testing logic to run
     * @param properties The configuration properties to set, must be an even value of Strings
     */
    public static <T> void withConfig(final Supplier<T> test, final String... properties) {
        assertTrue("Properties must be given as <key, value> pairs", (properties.length % 2) == 0);

        final int configsNumber = properties.length / 2;
        List<Config> configs = new ArrayList<>(configsNumber);
        Map<String, String> previous = new HashMap<>(configsNumber);

        for(int i = 0; i < configsNumber; i = i + 2) {
            final int index = i;
            configs.add(new Config(properties[i], properties[i + 1]));
            Optional.ofNullable(System.getProperty(properties[i]))
                    .ifPresent(prop -> previous.put(properties[index], prop));
        }

        try {
            for(final Config config : configs) {
                System.setProperty(config.key, config.value);
            }

            test.get();
        } finally {
            for(final Config config : configs) {
                if(previous.containsKey(config.key)) {
                    System.setProperty(config.key, previous.get(config.key));
                } else {
                    System.clearProperty(config.key);
                }
            }
        }
    }

    /**
     * Plain value object to store configuration overrides
     */
    private static class Config {
        final String key;
        final String value;

        private Config(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

}
