package io.sytac.resumator.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import io.sytac.resumator.Configuration;
import io.sytac.resumator.command.Command;
import io.sytac.resumator.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.function.Consumer;

import static io.sytac.resumator.ConfigurationEntries.LOG_TAG;

/**
 * Publishes event within the local JVM
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class LocalEventPublisher implements EventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalEventPublisher.class);

    private final EventBus eventBus;
    private final ObjectMapper json;

    @Inject
    public LocalEventPublisher(final Configuration configuration, final ObjectMapper json) {
        final String id = configuration.getProperty(LOG_TAG).orElse("resumator");
        this.eventBus = new EventBus(id);
        this.json = json;
    }

    @Override
    public <T extends Command> Event publish(final T command) {
        final Event<T> event = command.asEvent();
        eventBus.post(event);
        return event;
    }

    @Override
    public <T extends Event> void subscribe(final Consumer<T> consumer, final Class<T> clazz) {
        eventBus.register(new Listener<>(consumer, clazz));
    }

    private class Listener<T extends Event> {

        private final Consumer<T> delegate;
        private final Class<T> type;

        private Listener(Consumer<T> delegate, Class<T> type) {
            this.delegate = delegate;
            this.type = type;
        }

        @Subscribe
        public void accept(final T event) {
            if(type.isAssignableFrom(event.getClass())) {
                delegate.accept(event);
            }
        }
    }
}
