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
import java.util.Optional;
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
        final Event event = command.asEvent(json);
        eventBus.post(event);
        return event;
    }

    @Override
    public void subscribe(final Consumer<Event> consumer, final String type) {
        eventBus.register(new Listener(consumer, type));
    }

    @Override
    public void subscribe(Consumer<Event> consumer) {
        eventBus.register(new Listener(consumer));
    }

    private class Listener {

        private final Consumer<Event> delegate;
        private final Optional<String> type;

        private Listener(final Consumer<Event> delegate, final String type) {
            this.delegate = delegate;
            this.type = Optional.ofNullable(type);
            if(!this.type.isPresent()) {
                LOGGER.warn("Trying to register an event listener for a specific type, but the type is null: {}", delegate.getClass());
            }
        }

        private Listener(final Consumer<Event> delegate) {
            this.delegate = delegate;
            this.type = Optional.empty();
        }

        @SuppressWarnings("unused")
        @Subscribe
        public void accept(final Event event) {
            if(applies(event)) {
                delegate.accept(event);
            }
        }

        private boolean applies(final Event event) {
            return type.map(type -> type.equals(event.getType()))
                       .orElse(true);
        }
    }
}
