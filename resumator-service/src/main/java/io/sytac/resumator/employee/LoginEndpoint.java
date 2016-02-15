package io.sytac.resumator.employee;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

import javax.inject.Inject;
import javax.naming.NoPermissionException;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;

import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.security.Oauth2AuthenticationFilter;
import io.sytac.resumator.security.Oauth2SecurityService;

/**
 * Login Endpoint to handle google token exchange
 * One-time token is exchanged with a bigger life time token and sent to the client as a cookie.
 *
 * @author Selman Tayyar
 * @since 0.1
 */
@Path("login")
public class LoginEndpoint extends BaseResource {

	private static final String HEADER_PARAM_TOKEN = "user-token";
	Oauth2SecurityService securityService;
	
    @Inject
    public LoginEndpoint(final Oauth2SecurityService securityService) {
        this.securityService = securityService;
    }

    @HEAD
    public Response exchangeTokens(@HeaderParam(HEADER_PARAM_TOKEN) String  token) throws NoPermissionException, IOException {
    	
        Optional<String> authOneTimeToken = Optional.ofNullable(token);
        Optional<String> accessToken=securityService.exchangeTokens(authOneTimeToken.get());
        
        if(!accessToken.isPresent())
        	return Response.status(HttpStatus.BAD_REQUEST_400).build();       	
        
        return buildLoginRepresentation(accessToken.get());

    }
    

    private Response buildLoginRepresentation(String accessToken) {
        
        Calendar calendar=new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        int maxAge=60*60*48;//2 days
      
        Cookie cookie=new Cookie(Oauth2AuthenticationFilter.AUTHENTICATION_COOKIE, accessToken);
       
        return Response.status(HttpStatus.OK_200)
        		.cookie(new NewCookie(cookie, "google access token", maxAge, calendar.getTime(), false, true))                
                .build();
    }
}
