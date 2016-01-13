package io.sytac.resumator.model;

/**
 * Created by jacktol on 13/01/16.
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
