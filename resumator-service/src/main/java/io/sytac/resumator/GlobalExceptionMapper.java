package io.sytac.resumator;

import io.sytac.resumator.model.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Global exception mapper that returns an {@link Error} when an exception is thrown in an endpoint.
 */
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionMapper.class);


    @Override
    public Response toResponse(Exception exception) {
        LOGGER.error("The following exception was thrown but not caught in an endpoint: ", exception);

        return Response.status(500)
                .entity(new Error(exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
