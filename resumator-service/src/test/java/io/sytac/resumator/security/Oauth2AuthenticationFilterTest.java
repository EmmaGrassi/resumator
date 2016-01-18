package io.sytac.resumator.security;

import io.sytac.resumator.command.Command;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.model.Event;
import io.sytac.resumator.organization.InMemoryOrganizationRepository;
import io.sytac.resumator.organization.NewOrganizationCommand;
import io.sytac.resumator.organization.OrganizationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class Oauth2AuthenticationFilterTest {

    private Oauth2AuthenticationFilter filter;
    private Oauth2SecurityService service;
    private OrganizationRepository orgs;

    @Mock
    private EventPublisher eventPublisherMock;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(eventPublisherMock.publish(any(NewOrganizationCommand.class))).thenReturn(mock(Event.class));
        orgs = new InMemoryOrganizationRepository(eventPublisherMock);
        service = mock(Oauth2SecurityService.class);
        filter = new Oauth2AuthenticationFilter(service);
    }

    @Test
    public void anonymousHasNoRoles() throws IOException {
        final ContainerRequestContext ctx = mock(ContainerRequestContext.class);
        when(ctx.getCookies()).thenReturn(Collections.emptyMap());

        filter.filter(ctx);
        verify(service, never()).authenticateUser(anyString());
    }

    @Test
    public void wrongAuthenticationCookieGetsAnonymous() throws IOException {
        final Map<String, Cookie> wrongCookies = new HashMap<>();
        final Cookie cookie = new Cookie(Oauth2AuthenticationFilter.AUTHENTICATION_COOKIE, "I haz no permission!");
        wrongCookies.put(Oauth2AuthenticationFilter.AUTHENTICATION_COOKIE, cookie);

        final ContainerRequestContext ctx = mock(ContainerRequestContext.class);
        when(ctx.getCookies()).thenReturn(wrongCookies);
        when(service.authenticateUser(eq(cookie.getValue()))).thenReturn(Optional.empty());

        filter.filter(ctx);
        verify(ctx, times(1)).setSecurityContext(argThat(arg -> {
                    final SecurityContext sc = ((SecurityContext) arg);
                    return sc.getUserPrincipal().equals(User.ANONYMOUS);
                }
        ));
    }
}