package io.sytac.resumator.store.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.sytac.resumator.exception.ResumatorInternalException;
import io.sytac.resumator.model.Event;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.store.EventStore;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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

    /**
     * This method is used to transfor employees having "title" attribute to the new format.
     * We have changed "title" to "role" in our object model,which obliges us to do the transformation for the existing event data.
     */
    public void migrate() {
        List<Event> events = store.getAll();
        log.info("Migrating events in the old format,if any.");
 
	events.stream().filter(event->event.getPayload().contains("\"title\" :") && event.getPayload().contains("\"currentResidence\" :")).map(event->{
	    String newPayload=event.getPayload().replace("\"title\" :", "\"role\" :");
	    Event newEvent=new Event(event.getId(),event.getInsertOrder(),newPayload, new Timestamp(new Date().getTime()),event.getType());
	    return newEvent;
	}).forEach(event->store.post(event));

    }
    public void replay() {
	List<Event> events= store.getAll();
        log.info("Replaying events, setting store to read-only");
        store.setReadOnly(true);

        events.forEach(this::replayEvent);

        log.info("Replayed events successfully, setting store to read-write");
        store.setReadOnly(false);
    }

    private void replayEvent(Event event) {
        try {
            final ReplayEventConfig replayEventConfig = new ReplayEventConfig(orgs, objectMapper);
            EventExecutorFactory.getInstance(event.getType(), replayEventConfig).execute(event);
        } catch (IOException e) {
            throw new ResumatorInternalException("The following exception occurred while replaying events: ", e);
        }
    }
    
}
