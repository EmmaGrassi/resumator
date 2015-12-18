package io.sytac.resumator.events;

import com.google.common.base.Charsets;
import com.google.common.eventbus.EventBus;
import io.sytac.resumator.Configuration;
import io.sytac.resumator.http.command.model.NewEmployeeCommand;
import io.sytac.resumator.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.UUID;

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

    @Inject
    public LocalEventPublisher(final Configuration configuration) {
        final String id = configuration.getProperty(LOG_TAG).orElse("resumator");
        eventBus = new EventBus(id);
    }

    @Override
    public Event publish(NewEmployeeCommand command) {
        final Event event = new Event(UUID.randomUUID().toString(),
                                        command.getPayload().getOrganizationId(),
                                        -1L, // TODO
                                        -1L, // TODO
                                        command.getPayload().toString().getBytes(Charsets.UTF_8),
                                        new Timestamp(command.getHeader().getTimestamp().getTime()),
                                        command.getType());
        eventBus.post(event);
        return event;
    }
}
