package io.sytac.resumator.events;

import com.google.common.base.Charsets;
import com.google.common.eventbus.AsyncEventBus;
import io.sytac.resumator.Configuration;
import io.sytac.resumator.http.command.model.NewEmployeeCommand;
import io.sytac.resumator.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static io.sytac.resumator.ConfigurationEntries.LOG_TAG;
import static io.sytac.resumator.ConfigurationEntries.THREAD_POOL_SIZE;

/**
 * Publishes event within the local JVM
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class LocalEventPublisher implements EventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalEventPublisher.class);

    private final AsyncEventBus eventBus;

    @Inject
    public LocalEventPublisher(final Configuration configuration) {
        final String id = configuration.getProperty(LOG_TAG).orElse("resumator");
        final ExecutorService executor = createExecutorService(configuration);
        eventBus = new AsyncEventBus(id, executor);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    executor.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    LOGGER.error("Shutdown of the event system wasn't clean, there's potential for data loss");
                }
            }
        });
    }

    protected ExecutorService createExecutorService(final Configuration configuration) {
        final Integer concurrency = configuration.getIntegerProperty(THREAD_POOL_SIZE).orElse(1);
        return Executors.newFixedThreadPool(concurrency);
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
