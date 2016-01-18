package io.sytac.resumator.model;

/**
 * Represents an error that occurred together with its details.
 */
public class Error {

    private String message;

    public Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
