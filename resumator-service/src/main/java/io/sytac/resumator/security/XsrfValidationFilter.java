package io.sytac.resumator.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.sytac.resumator.Configuration;
import io.sytac.resumator.ConfigurationEntries;
import io.sytac.resumator.utils.ResumatorConstants;
import lombok.extern.slf4j.Slf4j;

import org.joda.time.Hours;
import org.joda.time.LocalDate;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Validator to check if XSRF header exists
 *
 * @author Selman Tayyar
 * @since 0.1
 */
@Priority(Priorities.AUTHORIZATION)
@Slf4j
public class XsrfValidationFilter implements ContainerRequestFilter {



    private final ObjectMapper objectMapper;
    final AuthenticationService authService;
    private final Configuration config;

    @Inject
    public XsrfValidationFilter(final AuthenticationService authService, final ObjectMapper objectMapper,final Configuration config) {
        this.authService=authService;
        this.objectMapper=objectMapper;
        this.config = config;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        if (!"/api/login".equals(requestContext.getUriInfo().getRequestUri().getPath())) {

            if (requestContext.getMethod().equals("POST") || requestContext.getMethod().equals("PUT")
                    || requestContext.getMethod().equals("DELETE")) {
                List<String> xsrfHeaders = Optional.ofNullable(requestContext.getHeaders().get(ResumatorConstants.XSRF_HEADER_NAME))
                        .orElse(new ArrayList<>());
                boolean isRequestValid = false;
                log.info("Filtering request for XSRF token check");

                if (!xsrfHeaders.isEmpty()) {

                    try {
                        String key = config.getProperty(ConfigurationEntries.XSRF_KEY).get();
                        String decryptedToken = authService.decryptEntity(xsrfHeaders.get(0), key);
                        Nonce nonce = objectMapper.readValue(decryptedToken, Nonce.class);

                        if (nonce.getUserName().equals(requestContext.getSecurityContext().getUserPrincipal().getName())) {
                            Date dateCreation = new Date(Long.parseLong(nonce.getTimeStamp()));
                            Date now = new Date();
                            Integer elapsedTime = Hours.hoursBetween(new LocalDate(dateCreation), new LocalDate(now))
                                    .getHours();

                            if (elapsedTime < 7)
                                isRequestValid = true;

                        }
                    } catch (Exception e) {
                        log.error("An error occured decrypting the CSRF token. Request being aborted."+e.getMessage()+" "+e.getCause());
                    } 

                }
                
                if(!isRequestValid)
                    requestContext.abortWith(Response
                            .status(Response.Status.UNAUTHORIZED)
                            .entity("{\"status\":\"failed\", \"error\":\"XSRF token validation failed.\"}")
                            .build());
                    
            }

        }
    }

}
