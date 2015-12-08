package io.sytac.resumator.http;

import com.theoryinpractise.halbuilder.api.Link;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Common functionality required by any REST resource
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class BaseResource {

    protected final RepresentationFactory rest = new JsonRepresentationFactory();

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

    /**
     * Helper method to create a new {@link Link}
     *
     * @param rel The link relationship
     * @param uri The link URI
     * @return The {@link Link} representation
     */
    protected Link link(final String rel, final URI uri) {
        return new Link(rest, rel, uri.toString());
    }

}
