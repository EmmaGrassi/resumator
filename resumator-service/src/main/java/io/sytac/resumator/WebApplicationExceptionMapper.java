package io.sytac.resumator;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * {@link ExceptionMapper} that returns the response of {@link WebApplicationException}s.
 */
@Slf4j
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException exception) {
        log.error("The following WebApplicationException occurred: ", exception);

        return exception.getResponse();
    }
}
