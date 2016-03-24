package io.sytac.resumator.security;

import io.sytac.resumator.ConfigurationEntries;
import lombok.extern.slf4j.Slf4j;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.SecurityContext;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

/**
 * Sets up a security context
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Priority(Priorities.AUTHENTICATION)
@Slf4j
public class Oauth2AuthenticationFilter implements ContainerRequestFilter {

    public static final String AUTHENTICATION_COOKIE = "resumatorJWT";
    public static final String NAME_COOKIE = "name";
    public static final String SURNAME_COOKIE = "surname";
    public static final String EMAIL_COOKIE = "email";
    public static final String DOMAIN_COOKIE = "domain";

    final Oauth2SecurityService security;
    private final AuthenticationService authService;
    

    @Inject
    public Oauth2AuthenticationFilter(Oauth2SecurityService security,final AuthenticationService authService) {
        this.security = security;
        this.authService=authService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
    	if(!"/api/login".equals(requestContext.getUriInfo().getRequestUri().getPath())){
            Optional<Cookie> authCookie   = Optional.ofNullable(requestContext.getCookies().get(AUTHENTICATION_COOKIE));
            Optional<Cookie> emailCookie  = Optional.ofNullable(requestContext.getCookies().get(Oauth2AuthenticationFilter.EMAIL_COOKIE));
            Optional<Cookie> domainCookie = Optional.ofNullable(requestContext.getCookies().get(Oauth2AuthenticationFilter.DOMAIN_COOKIE));
            
            log.info("Authentication Cookie retrieved: "+authCookie.isPresent());
            Optional<Identity> user = Optional.empty();
            
            try {
                if(authCookie.isPresent()){
                    String key=security.getConfig().getProperty(ConfigurationEntries.COOKIE_KEY).get();
                    String cookieDecrypted=authService.decryptEntity(authCookie.get().getValue(),key);

                    user = security.checkIfCookieValid(emailCookie, domainCookie, user,cookieDecrypted);
                      
                }
            } catch (Exception e) {
                log.error("Error occured during cookie validation,cookie is being invalidated."+e.getMessage()+" "+e.getCause());
                //do nothing since empty user will be set to the context.
            }
            
            SecurityContext securityContext = new Oauth2SecurityContext(user);
            requestContext.setSecurityContext(securityContext);
            

    	}
    }



}
