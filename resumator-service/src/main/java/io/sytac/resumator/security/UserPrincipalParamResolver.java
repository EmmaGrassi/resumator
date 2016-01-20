package io.sytac.resumator.security;

import org.glassfish.jersey.server.internal.inject.ParamInjectionResolver;

/**
 * {@link ParamInjectionResolver} for {@link UserPrincipal}.
 */
public class UserPrincipalParamResolver extends ParamInjectionResolver<UserPrincipal> {
    public UserPrincipalParamResolver() {
        super(UserPrincipalFactoryProvider.class);
    }
}
