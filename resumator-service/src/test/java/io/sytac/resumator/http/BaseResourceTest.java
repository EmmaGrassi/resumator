package io.sytac.resumator.http;

import io.sytac.resumator.security.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.SecurityContext;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaseResourceTest {

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private BaseResource baseResource;

    @Test
    public void testHappyFlow() {
        User user = new User("orgId", "name", Collections.emptySet());
        when(securityContext.getUserPrincipal()).thenReturn(user);

        assertThat(baseResource.getUser(), equalTo(user));
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalStateExceptionWhenUserNotPresent() {
        // Should never happen
        baseResource.getUser();
    }
}