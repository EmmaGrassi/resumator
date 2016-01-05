package io.sytac.resumator.http;

import io.sytac.resumator.Configuration;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;

/**
 * Test that the filter can process url rewritten URIs
 */
public class UriRewriteSupportFilterTest {

    private UriRewriteSupportFilter filter;

    @Before
    public void setup(){
        final Configuration config = new Configuration();
        filter = new UriRewriteSupportFilter(config);
    }

    @Test
    public void noConfigurationIsANOOP() throws Exception {
        final URI original = new URI("http://www.sytac.nl/resumator/api");

        final UriInfo info = mock(UriInfo.class);
        when(info.getBaseUri()).thenReturn(original);

        final ContainerRequestContext ctx = mock(ContainerRequestContext.class);
        when(ctx.getUriInfo()).thenReturn(info);

        filter.filter(ctx);
        verify(ctx).setRequestUri(original, info.getRequestUri());
    }

    @Test
    public void configuredContextPathAltersRequestContext() throws URISyntaxException, IOException {
        try {
            System.setProperty("resumator.http.context.path", "foobar/");
            final URI original = new URI("http://www.sytac.nl/resumator/api/");

            final UriInfo info = mock(UriInfo.class);
            when(info.getBaseUri()).thenReturn(original);
            when(info.getRequestUri()).thenReturn(new URI("http://www.sytac.nl/resumator/api/foobar/wooot"));

            final ContainerRequestContext ctx = mock(ContainerRequestContext.class);
            when(ctx.getUriInfo()).thenReturn(info);

            filter.filter(ctx);

            verify(ctx).setRequestUri(new URI("http://www.sytac.nl/resumator/api/foobar/"), info.getRequestUri());
        } finally {
            System.clearProperty("resumator.http.context.path");
        }
    }
}