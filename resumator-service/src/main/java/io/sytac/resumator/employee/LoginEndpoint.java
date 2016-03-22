package io.sytac.resumator.employee;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.security.GoogleResponse;
import io.sytac.resumator.security.Oauth2AuthenticationFilter;
import io.sytac.resumator.security.Oauth2SecurityService;

import org.eclipse.jetty.http.HttpStatus;

import javax.inject.Inject;
import javax.naming.NoPermissionException;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

/**
 * Login Endpoint to handle google token exchange One-time token is exchanged
 * with a bigger life time token and sent to the client as a cookie.
 *
 * @author Selman Tayyar
 * @since 0.1
 */
@Path("login")
public class LoginEndpoint extends BaseResource {

    private static final String HEADER_PARAM_TOKEN = "user-token";
    private Oauth2SecurityService securityService;


    private static final String COOKIE_PATH = "/";

    @Inject
    public LoginEndpoint(final Oauth2SecurityService securityService) {
        this.securityService = securityService;
    }

    @POST
    @Produces({ RepresentationFactory.HAL_JSON })
    public Response exchangeTokens(@HeaderParam(HEADER_PARAM_TOKEN) String token, @Context UriInfo ui)
            throws NoPermissionException, IOException {

        Optional<String> authOneTimeToken = Optional.ofNullable(token);
        Optional<GoogleResponse> response = securityService.exchangeTokens(authOneTimeToken.get());
        String domain = ui.getBaseUri().getHost();

        if (response.get().getAccessToken().isEmpty())
            return Response.status(HttpStatus.BAD_REQUEST_400).build();

        return buildLoginRepresentation(response.get(), domain);

    }

    private Response buildLoginRepresentation(GoogleResponse googleResponse,String domain) {
        
    	  final Representation halResource = rest.newRepresentation()
                  .withProperty("email", googleResponse.getEmail());
                  
        Calendar calendar=new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        int maxAge=60*60*48;//2 days
        
        StringBuffer cookieStr=new StringBuffer();
        cookieStr.append(googleResponse.getEmail()).append(",,").append(new Date().getTime()).append(",,").append(googleResponse.getAccessToken());
        
        String cookieEncrypted=securityService.encryptCookie(cookieStr.toString());
      
        Cookie cookieToken=new Cookie(Oauth2AuthenticationFilter.AUTHENTICATION_COOKIE, cookieEncrypted,COOKIE_PATH,domain);
        Cookie cookieName=new Cookie(Oauth2AuthenticationFilter.NAME_COOKIE, googleResponse.getName(),COOKIE_PATH,domain);
        Cookie cookieSurname=new Cookie(Oauth2AuthenticationFilter.SURNAME_COOKIE, googleResponse.getSurname(),COOKIE_PATH,domain);
        Cookie cookieEmail=new Cookie(Oauth2AuthenticationFilter.EMAIL_COOKIE, googleResponse.getEmail(),COOKIE_PATH,domain);
        Cookie cookieDomain=new Cookie(Oauth2AuthenticationFilter.DOMAIN_COOKIE, googleResponse.getHostedDomain(),COOKIE_PATH,domain);
        
        Response response=Response.ok(halResource.toString(RepresentationFactory.HAL_JSON))
        		.status(HttpStatus.OK_200)
        		.cookie(new NewCookie(cookieToken, "google access token", maxAge, calendar.getTime(), false, false)) 
        		.cookie(new NewCookie(cookieName, "name", maxAge, calendar.getTime(), false, false)) 
        		.cookie(new NewCookie(cookieSurname, "surname", maxAge, calendar.getTime(), false, false)) 
        		.cookie(new NewCookie(cookieEmail, "email", maxAge, calendar.getTime(), false, false)) 
        		.cookie(new NewCookie(cookieDomain, "hosted domain", maxAge, calendar.getTime(), false, false)) 
                .build();
       
        return response;
    }
}
