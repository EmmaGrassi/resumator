package io.sytac.resumator.store.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.sytac.resumator.exception.ResumatorInternalException;
import io.sytac.resumator.model.Event;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.store.EventStore;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;

import java.io.IOException;

/**
 * Replays events to build up the in-memory query state
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Slf4j
public class Bootstrap {

    private final EventStore store;
    private final OrganizationRepository orgs;
    private final ObjectMapper objectMapper;

    @Inject
    public Bootstrap(final EventStore store, final OrganizationRepository orgs, final ObjectMapper objectMapper) {
        this.store = store;
        this.orgs = orgs;
        this.objectMapper = objectMapper;
    }

    public void replay() {
        log.info("Replaying events, setting store to read-only");
        store.setReadOnly(true);
        store.getAll().forEach(this::replayEvent);

        log.info("Replayed events successfully, setting store to read-write");
        store.setReadOnly(false);
    }

    private void replayEvent(Event event) {
        try {
            final ReplayEventConfig replayEventConfig = new ReplayEventConfig(orgs, objectMapper);
            EventExecutorFactory.getInstance(event.getType(), replayEventConfig).execute(event);
        } catch (IOException e) {
            throw new ResumatorInternalException("The following exception occurred while replaying events: ", e.getCause());
        }
    }
}
