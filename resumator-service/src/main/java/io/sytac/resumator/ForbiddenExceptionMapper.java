package io.sytac.resumator;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Exception mapper that returns HttpStatus.SC_UNAUTHORIZED when an exception is thrown in an endpoint.
 *
 * TODO: At this moment Jersey throws ForbiddenException for both cases - when user is not authenticated
 * TODO: or not authorized. Later we have to change this implementation and map these exceptions separately.
 */
@Slf4j
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {

    @Override
    public Response toResponse(ForbiddenException exception) {
        log.error("The following exception was thrown but not caught in an endpoint: ", exception);

        return Response.status(HttpStatus.SC_FORBIDDEN).build();
    }
}
