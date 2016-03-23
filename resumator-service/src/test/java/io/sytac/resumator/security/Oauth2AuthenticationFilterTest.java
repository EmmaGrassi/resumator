package io.sytac.resumator.security;

import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.model.Event;
import io.sytac.resumator.organization.NewOrganizationCommand;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class Oauth2AuthenticationFilterTest {

    private Oauth2AuthenticationFilter filter;
    private Oauth2SecurityService service;

    @Mock
    private EventPublisher eventPublisherMock;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(eventPublisherMock.publish(any(NewOrganizationCommand.class))).thenReturn(mock(Event.class));
        service = mock(Oauth2SecurityService.class);
        filter = new Oauth2AuthenticationFilter(service);
    }

    @Test
    public void anonymousHasNoRoles() throws IOException {
        final ContainerRequestContext ctx = mock(ContainerRequestContext.class);
        when(ctx.getCookies()).thenReturn(Collections.emptyMap());
        setURLMock(ctx);

        filter.filter(ctx);
        verify(service, never()).toUser(anyString(),anyString());
    }

    @Test
    public void wrongAuthenticationCookieGetsAnonymous() throws IOException {
        final Map<String, Cookie> wrongCookies = new HashMap<>();
        
        final Cookie authCookie = new Cookie(Oauth2AuthenticationFilter.AUTHENTICATION_COOKIE, "I haz no permission!");
        final Cookie emailCookie = new Cookie(Oauth2AuthenticationFilter.EMAIL_COOKIE, "user@sytac.io");
        final Cookie domainCookie = new Cookie(Oauth2AuthenticationFilter.DOMAIN_COOKIE, "sytac.io");
        
        wrongCookies.put(Oauth2AuthenticationFilter.AUTHENTICATION_COOKIE, authCookie);
        wrongCookies.put(Oauth2AuthenticationFilter.EMAIL_COOKIE, emailCookie);
        wrongCookies.put(Oauth2AuthenticationFilter.DOMAIN_COOKIE, domainCookie);
        
        Optional<Identity> user = Optional.empty();

        final ContainerRequestContext ctx = mock(ContainerRequestContext.class);
       
        setURLMock(ctx);
        
        
        when(ctx.getCookies()).thenReturn(wrongCookies);
        when(service.checkIfCookieValid(eq(Optional.of(authCookie)), eq(Optional.of(emailCookie)),eq(user), anyString())).thenReturn(Optional.empty());
        when(service.decryptCookie(anyString())).thenCallRealMethod();//test if decrpyt is working.
        
        filter.filter(ctx);
        verify(ctx, times(1)).setSecurityContext(argThat(arg -> {
                    final SecurityContext sc = ((SecurityContext) arg);
                    return sc.getUserPrincipal().equals(Identity.ANONYMOUS);
                }
        ));
    }
    
    @Test
    public void correctCookieGetsValidated() throws IOException {
        
        
        Long time=new Date().getTime();
        String authCookieUnencrypted="user@sytac.io,,"+time+",,tokenaccess21321321";
        String cookieEncrytped="7WHZ/324jklsjfds43rklewkrlwejrlkwerew";
        
        when(service.encryptCookie(authCookieUnencrypted)).thenReturn(cookieEncrytped);
        when(service.decryptCookie(cookieEncrytped)).thenReturn(authCookieUnencrypted);

        
        final Map<String, Cookie> correctCookies = new HashMap<>();
        
        final Cookie authCookie = new Cookie(Oauth2AuthenticationFilter.AUTHENTICATION_COOKIE, service.encryptCookie(authCookieUnencrypted));
        final Cookie emailCookie = new Cookie(Oauth2AuthenticationFilter.EMAIL_COOKIE, "user@sytac.io");
        final Cookie domainCookie = new Cookie(Oauth2AuthenticationFilter.DOMAIN_COOKIE, "sytac.io");
        
        correctCookies.put(Oauth2AuthenticationFilter.AUTHENTICATION_COOKIE, authCookie);
        correctCookies.put(Oauth2AuthenticationFilter.EMAIL_COOKIE, emailCookie);
        correctCookies.put(Oauth2AuthenticationFilter.DOMAIN_COOKIE, domainCookie);
        
        Optional<Identity> user = Optional.empty();

        final ContainerRequestContext ctx = mock(ContainerRequestContext.class);
       
        setURLMock(ctx);
        
        HashSet<String> userRoles=new HashSet<>();
        userRoles.add("user");
        when(ctx.getCookies()).thenReturn(correctCookies);
        when(service.checkIfCookieValid(eq(Optional.of(emailCookie)), eq(Optional.of(domainCookie)),eq(user), eq(authCookieUnencrypted))).thenCallRealMethod();
        when(service.toUser(eq(emailCookie.getValue()),eq(domainCookie.getValue()))).thenReturn(Optional.of(new Identity("1", emailCookie.getValue(), userRoles)));
        
        filter.filter(ctx);
        verify(ctx, times(1)).setSecurityContext(argThat(arg -> {
                    final SecurityContext sc = ((SecurityContext) arg);
                    return sc.getUserPrincipal().getName().equals("user@sytac.io");
                }
        ));
    }

	private void setURLMock(final ContainerRequestContext ctx) {
		final UriInfo uriInfo = mock(UriInfo.class);
        URI uri=null;
        try {
			 uri = new URI("http://resumator.sytac.io:8000/#/");
		} catch (URISyntaxException e) {
			
			e.printStackTrace();
		}
        

        when(ctx.getUriInfo()).thenReturn(uriInfo);
        when(uriInfo.getRequestUri()).thenReturn(uri);
	}
}