package io.sytac.resumator;

import io.sytac.resumator.model.Error;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Global exception mapper that returns an {@link Error} when an exception is thrown in an endpoint.
 */
@Slf4j
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        log.error("The following exception was thrown but not caught in an endpoint: ", exception);

        return Response.status(500)
                .entity(new Error(exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
