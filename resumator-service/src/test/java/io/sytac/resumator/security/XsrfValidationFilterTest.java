package io.sytac.resumator.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.sytac.resumator.Configuration;
import io.sytac.resumator.utils.ResumatorConstants;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class XsrfValidationFilterTest {


    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private AuthenticationService authService;

    @Mock
    private Configuration config;

    @InjectMocks
    private XsrfValidationFilter filter;

    @Test
    public void noXSRFHeaderAbortsRequestTest() {
        final ContainerRequestContext context = mock(ContainerRequestContext.class);
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        when(context.getHeaders()).thenReturn(headers);
        setURLMock(context);
        when(context.getMethod()).thenReturn("POST");
        filter.filter(context);
        
        verify(context,times(1)).abortWith(any());
    }
    
    @Test
    public void dummyXSRFHeaderAbortsRequestTest() {
        final ContainerRequestContext context = mock(ContainerRequestContext.class);
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        List<String> xsrfHeaders=new ArrayList<>();
        xsrfHeaders.add("dummyHeader121");
        headers.put(ResumatorConstants.XSRF_HEADER_NAME, xsrfHeaders);
        when(context.getHeaders()).thenReturn(headers);
        setURLMock(context);
        when(context.getMethod()).thenReturn("POST");
        filter.filter(context);
        
        verify(context,times(1)).abortWith(any());
    }
    
    private void setURLMock(final ContainerRequestContext ctx) {
        final UriInfo uriInfo = mock(UriInfo.class);
        URI uri = null;
        try {
            uri = new URI("http://resumator.sytac.io:8000/#/");
        } catch (URISyntaxException e) {

            e.printStackTrace();
        }

        when(ctx.getUriInfo()).thenReturn(uriInfo);
        when(uriInfo.getRequestUri()).thenReturn(uri);
    }
    
}
