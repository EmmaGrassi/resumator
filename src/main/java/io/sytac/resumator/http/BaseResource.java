package io.sytac.resumator.http;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Created by skuro on 03/12/15.
 */
public class BaseResource {

    /**
     * Build the URI for the link connected to the provided resource
     *
     * @param uriInfo  The base URI for the service
     * @param resource The resource for which to build the link
     * @return The link to the provided resoruce
     */
    protected URI resourceLink(final UriInfo uriInfo, final Class<?> resource) {
        return UriBuilder.fromUri(uriInfo.getBaseUri())
                .path(resource)
                .build();
    }

}
