package io.sytac.resumator.store;

import io.sytac.resumator.model.Event;

/**
 * Models the retrieval of events
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public interface EventStore {
    void put(Event event) throws StoreException;
}
