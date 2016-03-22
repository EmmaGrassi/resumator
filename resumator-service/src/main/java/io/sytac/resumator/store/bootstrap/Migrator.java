package io.sytac.resumator.store.bootstrap;

import io.sytac.resumator.model.Event;
import io.sytac.resumator.store.EventStore;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Migrates some events that had undergone format changes.
 *
 * @author Selman Tayyar
 * @since 0.1
 */
@Slf4j
public class Migrator {

    private final EventStore store;

    @Inject
    public Migrator(final EventStore store) {
        this.store = store;
    }

    /**
     * This method is used to transfer employees having "title" attribute to the
     * new format. We have changed "title" to "role" in our object model,which
     * obliges us to do the transformation for the existing event data.
     */
    public void migrate() {
        List<Event> events = store.getAll();
        log.info("Migrating events in the old format,if any.");

        events.stream().filter(event -> checkIfRecordIsOfTypeEmployee(event)).map(event -> {
            return transformEvent(event);
        }).forEach(event -> store.post(event));

    }

    private Event transformEvent(Event event) {
        String newPayload = event.getPayload().replace("\"title\" :", "\"role\" :");
        Event newEvent = new Event(event.getId(), event.getInsertOrder(), newPayload,
                new Timestamp(new Date().getTime()), event.getType());
        return newEvent;
    }

    private boolean checkIfRecordIsOfTypeEmployee(Event event) {
        //Added a safety check on currentResidence field to make sure that record is employee type.
        return event.getPayload().contains("\"title\" :") && event.getPayload().contains("\"currentResidence\" :");
    }

}
