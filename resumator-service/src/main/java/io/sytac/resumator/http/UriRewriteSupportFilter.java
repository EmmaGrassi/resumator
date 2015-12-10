package io.sytac.resumator.http;

import io.sytac.resumator.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static io.sytac.resumator.ConfigurationEntries.*;

/**
 * Enables Jersey routes to work even when the Resumator is serving requests from a rewritten context path
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@PreMatching
public class UriRewriteSupportFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(UriRewriteSupportFilter.class);

    private final Configuration config;

    @Inject
    public UriRewriteSupportFilter(Configuration config) {
        this.config = config;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        adaptRewrittenUri(requestContext);
    }

    protected void adaptRewrittenUri(ContainerRequestContext requestContext) {
        final UriInfo uriInfo = requestContext.getUriInfo();
        final URI baseUri = appendContextPath(uriInfo.getBaseUri());
        requestContext.setRequestUri(baseUri, uriInfo.getRequestUri());
    }

    protected URI appendContextPath(URI uri) {
        final String path = getRewrittenContextPath();
        if(!"".equals(path)) {
            final String uriString = uri.toString();
            try {
                return new URI(uriString + path);
            } catch (URISyntaxException e) {
                LOGGER.error("Wrong value for config entry resumator.http.context.path, ignoring: {}", path);
                return uri;
            }
        }

        return uri;
    }

    private String getRewrittenContextPath() {
        return config.getProperty(CONTEXT_PATH)
                     .orElse("");
    }
}
