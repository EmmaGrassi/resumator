package io.sytac.resumator.http;

import io.sytac.resumator.Configuration;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.UriInfo;

import java.net.URI;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    }
}