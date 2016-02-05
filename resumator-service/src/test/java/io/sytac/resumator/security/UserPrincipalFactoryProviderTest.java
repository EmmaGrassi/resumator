package io.sytac.resumator.security;

import io.sytac.resumator.model.Course;
import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.server.model.Parameter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.SecurityContext;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Test for {@link UserPrincipalFactoryProvider}.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserPrincipalFactoryProviderTest {

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserPrincipalFactoryProvider userPrincipalFactoryProvider = new UserPrincipalFactoryProvider(null, null);

    @Test
    public void testValueFactoryReturnsPresentUser() throws Exception {
        Identity identity = new Identity("orgId", "name", Collections.emptySet());
        when(securityContext.getUserPrincipal()).thenReturn(identity);

        Parameter parameter = mock(Parameter.class);
        doReturn(Identity.class).when(parameter).getRawType();

        Factory<Identity> valueFactory = userPrincipalFactoryProvider.createValueFactory(parameter);
        assertThat(valueFactory.provide(), equalTo(identity));
    }

    @Test(expected = IllegalStateException.class)
    public void testValueFactoryThrowsISEWhenUserNotPresent() throws Exception {
        // Should never happen
        Parameter parameter = mock(Parameter.class);
        doReturn(Identity.class).when(parameter).getRawType();

        userPrincipalFactoryProvider.createValueFactory(parameter).provide();
    }

    @Test
    public void testValueFactoryReturnsNullWithNonUserParameter() {
        Parameter parameter = mock(Parameter.class);
        doReturn(Course.class).when(parameter).getRawType();

        assertThat(userPrincipalFactoryProvider.createValueFactory(parameter), nullValue());
    }
}