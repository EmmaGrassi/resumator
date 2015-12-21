package io.sytac.resumator.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.model.Event;
import io.sytac.resumator.organization.OrganizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Replays events to build up the in-memory query state
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class Bootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);

    private final EventStore store;
    private final OrganizationRepository orgs;
    private final ObjectMapper json;

    @Inject
    public Bootstrap(EventStore store, OrganizationRepository orgs, ObjectMapper json) {
        this.store = store;
        this.orgs = orgs;
        this.json = json;
    }

    public void start(final Consumer<BootstrapResult> callback) {
        store.setReadOnly(true);
        final List<Event> events = store.getAll();
        final BootstrapResult result = replay(events);
        store.setReadOnly(false);
        callback.accept(result);
    }

    private BootstrapResult replay(List<Event> events) {
        final BootstrapResult result = new BootstrapResult();

        events.forEach(event -> {
            try {
                if(replayEvent(event)) {
                    result.successfullyReplayed.incrementAndGet();
                } else {
                    result.failures.put(event, new IllegalArgumentException("Unsopported event type: " + event.getType()));
                }
            } catch (Exception e) {
                result.failures.put(event, e);
            }
        });

        return result;
    }

    private boolean replayEvent(Event event) throws IOException {
        switch (event.getType()) {
            case "newEmployee":
                final NewEmployeeCommand command = json.readValue(event.getPayload(), NewEmployeeCommand.class);
                orgs.get(command.getPayload().getOrganizationId())
                        .orElseThrow(() -> new IllegalArgumentException("Cannot replay new employee for unknown organization"))
                        .addEmployee(command);
                return true;
            default:
                LOGGER.error("Ignoring unsupported event:\n{}", event);
                return false;
        }
    }

    public static class BootstrapResult {
        protected final AtomicInteger successfullyReplayed = new AtomicInteger(0);
        protected final Map<Event, Exception> failures = new HashMap<>();

        public AtomicInteger getSuccessfullyReplayed() {
            return successfullyReplayed;
        }

        public Map<Event, Exception> getFailures() {
            return failures;
        }
    }
}
