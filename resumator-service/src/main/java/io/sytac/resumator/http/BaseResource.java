package io.sytac.resumator.http;

import com.theoryinpractise.halbuilder.api.Link;
import io.sytac.resumator.halbuilder.ResumatorJsonRepresentationFactory;

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

    protected final ResumatorJsonRepresentationFactory rest = new ResumatorJsonRepresentationFactory();

    /**
     * Build the URI for the link connected to the provided resource
     *
     * @param uriInfo  The base URI for the service
     * @param resource The resource for which to build the link
     * @return The link to the provided resource
     */
    protected URI resourceLink(final UriInfo uriInfo, final Class<?> resource) {
        return UriBuilder.fromUri(uriInfo.getBaseUri())
                .path(resource)
                .build();
    }

    /**
     * Build the URI for the link connected to the provided resource
     *
     * @param uriInfo  The base URI for the service
     * @param resource The resource for which to build the link
     * @param params   The parameters to set to the URI
     * @return The link to the provided resource
     */
    protected URI resourceLink(final UriInfo uriInfo, final Class<?> resource, final String... params) {
        return UriBuilder.fromUri(uriInfo.getBaseUri())
                .path(resource)
                .build(params);
    }

    /**
     * Build the URI for the method on the provided resource
     *
     * @param uriInfo The base URI for the service
     * @param resource The resource to which to create a link
     * @param method The method on the resource to which to create a link
     * @param params Parameters to fill in in case the URI to link to is parametrized
     */
    protected URI resourceMethodLink(final UriInfo uriInfo, final Class<?> resource, final String method, final String... params) {
        return UriBuilder.fromUri(uriInfo.getBaseUri())
                .path(resource, method)
                .build(params);
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
