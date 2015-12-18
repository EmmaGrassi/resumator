package io.sytac.resumator.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import io.sytac.resumator.Configuration;
import io.sytac.resumator.command.Command;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.employee.NewEmployeeEvent;
import io.sytac.resumator.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.UUID;
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
    public Event publish(NewEmployeeCommand command) {
        final Event event;
        try {
            event = asEvent(command);
            eventBus.post(event);
        } catch (JsonProcessingException e) {
            LOGGER.warn("Couldn't serialize event, skipping");
            throw new IllegalArgumentException(e);
        }

        return event;
    }

    private Event asEvent(NewEmployeeCommand command) throws JsonProcessingException {
        return new NewEmployeeEvent(UUID.randomUUID().toString(),
                                            "streamId",
                                            command);
    }

    @Override
    public <T extends Event> void subscribe(final Consumer<T> consumer, final Class<T> type) {
        eventBus.register(new Consumer<T>() {
            @Override
            @Subscribe
            public void accept(T t) {
                if(type.isAssignableFrom(t.getClass())) {
                    consumer.accept(t);
                }
            }
        });
    }
}
