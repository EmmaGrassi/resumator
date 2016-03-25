package io.sytac.resumator.security;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.collect.Sets;

import io.sytac.resumator.Configuration;
import io.sytac.resumator.ConfigurationEntries;
import io.sytac.resumator.employee.Employee;
import io.sytac.resumator.exception.ResumatorInternalException;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.ws.rs.core.Cookie;
import javax.xml.bind.DatatypeConverter;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static io.sytac.resumator.ConfigurationEntries.ADMIN_ACCOUNT_LIST;
import static io.sytac.resumator.ConfigurationEntries.GOOGLE_CLIENT_ID;
import static io.sytac.resumator.ConfigurationEntries.GOOGLE_SECRET;
import static io.sytac.resumator.security.Roles.ADMIN;
import static io.sytac.resumator.security.Roles.USER;

/**
 * Authentication service that validates Google JWT tokens
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Slf4j
@Getter
public class Oauth2SecurityService {

    private final Configuration config;
    private final OrganizationRepository organizations;

    @Inject
    public Oauth2SecurityService(final Configuration config, final OrganizationRepository organizations) {
        this.config = config;
        this.organizations = organizations;
    }
       

    public Optional<Identity> toUser(String email, String hostedDomain) {

        final Optional<Organization> organization = organizations.fromDomain(hostedDomain);
        log.info("User info : " + hostedDomain + " " + email + " " + organization.isPresent());

        return organization.map(org -> new Identity(org.getId(), email, getRoles(org, email)));

    }

    private Set<String> getRoles(final Organization organization, final String email) {
        return hasAdminRole(organization, email) ? Sets.newHashSet(ADMIN, USER) : Sets.newHashSet(USER);
    }

    private boolean hasAdminRole(final Organization organization, final String email) {
        final Set<String> admins = new HashSet<>(config.getListProperty(ADMIN_ACCOUNT_LIST));
        if (admins.contains(email)) {
            return true;
        }

        final Optional<Employee> employee = Optional.ofNullable(organization.getEmployeeByEmail(email));
        return employee.map(Employee::isAdmin).orElse(false);
    }

    public Optional<GoogleResponse> exchangeTokens(String token) throws IOException {

        String clientId = config.getProperty(GOOGLE_CLIENT_ID).get();
        String clientSecret = config.getProperty(GOOGLE_SECRET).get();

        try {
            GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(), "https://www.googleapis.com/oauth2/v4/token", clientId,
                    clientSecret, token, "postmessage").execute();

            String accessToken = tokenResponse.getIdToken();

            GoogleIdToken idToken = tokenResponse.parseIdToken();
            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.getUnknownKeys().get("given_name");
            String surname = (String) payload.getUnknownKeys().get("family_name");
            String hostedDomain = payload.getHostedDomain();

            return Optional.of(GoogleResponse.builder().accessToken(accessToken).name(name).surname(surname)
                    .email(email).hostedDomain(hostedDomain).build());
        } catch (TokenResponseException e) {

            log.warn("Couldn't validate one-time token.", e);
        }

        return Optional.empty();

    }

    /*
     * Method checking if the cookie is valid and setting the identity accordingly.If emails from the cookie and actual user email are identical and it has not been
     * more than 2 days after cookie is created,it is considered as valid.
     */
    public Optional<Identity> checkIfCookieValid(Optional<Cookie> emailCookie, Optional<Cookie> domainCookie,
            Optional<Identity> user, String cookieDecrypted) {
        
        String cookieItems[]=cookieDecrypted.split(",,");
        String emailFromCookie=cookieItems[0];
        String time=cookieItems[1];
        
        //emails should be identical and time passed after the cookie creation shouldn't be more than two days.
        if (emailCookie.isPresent()&& emailCookie.get().getValue().equals(emailFromCookie)){
            
            Date dateCreation=new Date(Long.parseLong(time));
            Date now=new Date();
            Integer elapsedTime=Days.daysBetween(new LocalDate(dateCreation), new LocalDate(now)).getDays();
            
            if(elapsedTime<=2)
                user = toUser(emailFromCookie, domainCookie.get().getValue());
        }
        return user;
    }

}
