package io.sytac.resumator.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.Configuration;
import org.glassfish.hk2.api.Factory;

import javax.inject.Inject;

/**
 * Creates the {@link EventPublisher} for the local environment
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class EventPublisherFactory implements Factory<EventPublisher> {

    private final Configuration config;
    private final ObjectMapper json;

    @Inject
    public EventPublisherFactory(final Configuration config, final ObjectMapper json) {
        this.config = config;
        this.json = json;
    }

    @Override
    public EventPublisher provide() {
        return new LocalEventPublisher(config, json);
    }

    @Override
    public void dispose(EventPublisher instance) {

    }
}
