package io.sytac.resumator.store;

import io.sytac.resumator.model.Event;

import java.util.List;

/**
 * Models the retrieval of events
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public interface EventStore {
    /**
     * Puts a new event into the store
     *
     * @param event The event to store
     * @throws IllegalInsertOrderException when the event contains an insert order number which is already used in the store
     * @throws IllegalStreamOrderException when the event contains a stream order which is already used in the store for the given event stream
     * @since 0.1
     */
    void put(Event event);

    /**
     * Retrieves all the events in the store
     *
     * @return All the events found in the store
     * @since 0.1
     */
    List<Event> getAll();

    /**
     * Clears out all the events from the store, effectively making it empty.
     *
     * @since 0.1
     */
    void removeAll();

    /**
     * Controls whether the store accepts new events or not. When set to true the system will reject any new event
     *
     * @param mode The status of the read-only mode
     * @since 0.1
     */
    void setReadOnly(boolean mode);
}
