package io.sytac.resumator.employee;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

import javax.inject.Inject;
import javax.naming.NoPermissionException;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.security.GoogleResponse;
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
	
	private static final String COOKIE_PATH="/";
	private static final String COOKIE_DOMAIN="resumator.sytac.io";
	
    @Inject
    public LoginEndpoint(final Oauth2SecurityService securityService) {
        this.securityService = securityService;
    }

    @POST
    @Produces({RepresentationFactory.HAL_JSON})
    public Response exchangeTokens(@HeaderParam(HEADER_PARAM_TOKEN) String  token) throws NoPermissionException, IOException {
    	
        Optional<String> authOneTimeToken = Optional.ofNullable(token);
        Optional<GoogleResponse> response=securityService.exchangeTokens(authOneTimeToken.get());
        
        if(response.get().getAccessToken().isEmpty())
        	return Response.status(HttpStatus.BAD_REQUEST_400).build();       	
        
        return buildLoginRepresentation(response.get());

    }
    

    private Response buildLoginRepresentation(GoogleResponse googleResponse) {
        
    	  final Representation halResource = rest.newRepresentation()
                  .withProperty("email", googleResponse.getEmail());
                  
        Calendar calendar=new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        int maxAge=60*60*48;//2 days
      
        Cookie cookieToken=new Cookie(Oauth2AuthenticationFilter.AUTHENTICATION_COOKIE, googleResponse.getAccessToken(),COOKIE_PATH,COOKIE_DOMAIN);
        Cookie cookieName=new Cookie("name", googleResponse.getName(),COOKIE_PATH,COOKIE_DOMAIN);
        Cookie cookieSurname=new Cookie("surname", googleResponse.getSurname(),COOKIE_PATH,COOKIE_DOMAIN);
        Cookie cookieEmail=new Cookie("email", googleResponse.getEmail(),COOKIE_PATH,COOKIE_DOMAIN);
        
        Response response=Response.ok(halResource.toString(RepresentationFactory.HAL_JSON))
        		.status(HttpStatus.OK_200)
        		.cookie(new NewCookie(cookieToken, "google access token", maxAge, calendar.getTime(), false, false)) 
        		.cookie(new NewCookie(cookieName, "name", maxAge, calendar.getTime(), false, false)) 
        		.cookie(new NewCookie(cookieSurname, "surname", maxAge, calendar.getTime(), false, false)) 
        		.cookie(new NewCookie(cookieEmail, "email", maxAge, calendar.getTime(), false, false)) 
                .build();
       
        return response;
    }
}
