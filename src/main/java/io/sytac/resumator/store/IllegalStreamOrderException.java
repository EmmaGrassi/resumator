package io.sytac.resumator.store;

/**
 * Thrown when trying to store an event with a non-unique stream order number
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class IllegalStreamOrderException extends StoreException {
}
