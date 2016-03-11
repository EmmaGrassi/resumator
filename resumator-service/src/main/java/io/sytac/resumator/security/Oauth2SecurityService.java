package io.sytac.resumator.security;

import static io.sytac.resumator.ConfigurationEntries.ADMIN_ACCOUNT_LIST;
import static io.sytac.resumator.ConfigurationEntries.GOOGLE_APPS_DOMAIN_NAME;
import static io.sytac.resumator.ConfigurationEntries.GOOGLE_CLIENT_ID;
import static io.sytac.resumator.ConfigurationEntries.GOOGLE_SECRET;
import static io.sytac.resumator.security.Roles.ADMIN;
import static io.sytac.resumator.security.Roles.USER;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import javax.inject.Inject;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.collect.Sets;

import io.sytac.resumator.Configuration;
import io.sytac.resumator.employee.Employee;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Authentication service that validates Google JWT tokens
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Slf4j
public class Oauth2SecurityService {

    private final Configuration config;
    private final OrganizationRepository organizations;

    @Inject
    public Oauth2SecurityService(final Configuration config, final OrganizationRepository organizations) {
        this.config = config;
        this.organizations = organizations;
    }

    /**
     * Authenticate the token Id against the GoogleId verifier, and return the associated user if authenticated.
     *
     * @param idToken the Id token provided by the Google Authentication on Front End
     * @return the associated user
     */
    public Optional<Identity> authenticateUser(final String idToken) {

    	log.info("user authentication in google started");
    	final GoogleIdTokenVerifier verifier = buildVerifier();
        final Optional<GoogleIdToken> idtoken = verify(verifier, idToken);
        return toUser(idtoken);
    }

    private Optional<Identity> toUser(final Optional<GoogleIdToken> idToken) {
        return idToken.flatMap(token -> {
            final Optional<Organization> organization = organizations.fromDomain(token.getPayload().getHostedDomain());
            log.info("token to user: "+token.getPayload().getHostedDomain()+" "+token.getPayload().getEmail()+" "+organization.isPresent());
            return organization.map(org -> new Identity(org.getId(),
                    token.getPayload().getEmail(),
                    getRoles(org, token.getPayload())));
        });
    }

    private Set<String> getRoles(final Organization organization, final GoogleIdToken.Payload payload) {
        return hasAdminRole(organization, payload) ? Sets.newHashSet(ADMIN, USER) : Sets.newHashSet(USER);
    }

    private boolean hasAdminRole(final Organization organization, final GoogleIdToken.Payload payload) {
        final Set<String> admins = new HashSet<>(config.getListProperty(ADMIN_ACCOUNT_LIST));
        if (admins.contains(payload.getEmail())) {
            return true;
        }

        final Optional<Employee> employee = Optional.ofNullable(organization.getEmployeeByEmail(payload.getEmail()));
        return employee.map(Employee::isAdmin).orElse(false);
    }

    private Optional<GoogleIdToken> verify(final GoogleIdTokenVerifier verifier, final String idtoken) {
        final GoogleIdToken token;
        try {
            token = verifier.verify(idtoken);
            return validate(token);
        } catch (GeneralSecurityException | IOException e) {
            log.warn("Couldn't validate the Google JWT", e);
        }

        return Optional.empty();
    }

    private Optional<GoogleIdToken> validate(final GoogleIdToken token) {
        if (token != null) {
            log.info("Token verify finished: "+token.getPayload().getHostedDomain()+" "+token.getPayload().getAuthorizedParty()+" "+token.getPayload().getEmail());
            log.info("Token validation starting ");
            GoogleIdToken.Payload payload = token.getPayload();
            if (assertCondition("Wrong Google domain", () ->
                    config.getProperty(GOOGLE_APPS_DOMAIN_NAME)
                          .map(domain -> domain.equals(payload.getHostedDomain()))
                          .orElse(false)
                    && assertCondition("Wrong client application ID", () ->
                            config.getProperty(GOOGLE_CLIENT_ID)
                                  .map(id -> id.equals(payload.getAuthorizedParty()))
                                  .orElse(false)))) {
            	log.info("Token validation succeeded ");
                return Optional.of(token);
            }
        }
        log.info("Token validation failed ");
        return Optional.empty();
    }

    private Boolean assertCondition(final String errorMessage, final Supplier<Boolean> assertion) {
        final Boolean result = assertion.get();
        if(!result) {
            log.info(errorMessage);
        }

        return result;
    }

    private GoogleIdTokenVerifier buildVerifier() {
        final JsonFactory jsonFactory = new JacksonFactory();
        final HttpTransport httpTransport = new NetHttpTransport();
        return new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
                .setAudience(Collections.singletonList(config.getProperty(GOOGLE_CLIENT_ID).get()))
                .build();
    }
    
	public Optional<GoogleResponse> exchangeTokens(String token) throws IOException {

		String clientId = config.getProperty(GOOGLE_CLIENT_ID).get();
		String clientSecret = config.getProperty(GOOGLE_SECRET).get();

		try {
			GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(new NetHttpTransport(),
					JacksonFactory.getDefaultInstance(), "https://www.googleapis.com/oauth2/v4/token", clientId, clientSecret,
					token, "postmessage").execute();

			String accessToken = tokenResponse.getIdToken();
			
			GoogleIdToken idToken = tokenResponse.parseIdToken();
			GoogleIdToken.Payload payload = idToken.getPayload();
			String email = payload.getEmail();	
			String name = (String) payload.getUnknownKeys().get("given_name");
			String surname = (String) payload.getUnknownKeys().get("family_name");
			
			return Optional.of(GoogleResponse.builder().accessToken(accessToken).name(name).surname(surname).email(email).build());
		} catch (TokenResponseException e) {
			
			log.warn("Couldn't validate one-time token.", e);
		}
		
		return Optional.empty();

	}

}
