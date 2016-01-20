package io.sytac.resumator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents an error that occurred together with its details.
 */
@Getter
@AllArgsConstructor
public class Error {

    private String message;
}
