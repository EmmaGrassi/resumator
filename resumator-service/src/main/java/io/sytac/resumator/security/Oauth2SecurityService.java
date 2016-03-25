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
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.SerializationUtils;
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
public class Oauth2SecurityService {

    private final Configuration config;
    private final OrganizationRepository organizations;
    
    private final String STR_SHA="SHA-1";   
    private final String STR_AES="AES";
    private final String STR_SIGNATURE_FORMAT="%1$40s";
    
    

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
     * Method encrypting the cookie on AES standart.Key is retrieved from config.properties file placed on ~home directory.
     */
    public String encryptCookie(String cookie) {
        try {
            Cipher aes = createChiper(Cipher.ENCRYPT_MODE);
            byte[] bytes = SerializationUtils.serialize(cookie);
            String encryptedCookie = DatatypeConverter.printHexBinary(aes.doFinal(bytes));
            String signature = calculateSignature(bytes).toUpperCase();
            return encryptedCookie + signature;
        } catch (Exception e) {
            log.error("Can't encrypt the cookie", e);
            throw new ResumatorInternalException("Can't encrypt the cookie", e);
        }
    }

    public String decryptCookie(String cookie) {
        try {
            String signature = cookie.substring(cookie.length() - 40);
            String encryptedCookie = cookie.substring(0, cookie.length() - 40);

            Cipher aes = createChiper(Cipher.DECRYPT_MODE);
            byte[] bytes = aes.doFinal(DatatypeConverter.parseHexBinary(encryptedCookie));

            if (!signature.equals(calculateSignature(bytes).toUpperCase())) {
                log.error("Session has been tampered with");
                return null;
            }

            return SerializationUtils.deserialize(bytes);
        } catch (Exception e) {
            log.error("Can't decrypt cookie", e);

            throw new ResumatorInternalException("Error decrypting the cookie", e);
        }
    }

    private Cipher createChiper(int mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException {
        Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
        Optional<String> key = config.getProperty(ConfigurationEntries.COOKIE_KEY);
       
        if(key.isPresent())
            {
                aes.init(mode, new SecretKeySpec(key.get().getBytes(), STR_AES), new IvParameterSpec(new byte[16]));
                return aes;
            }
        else
            throw new InvalidKeyException("Encryption key should be defined!");
    }

    private String calculateSignature(byte[] serialisedSession) {
        try {
            MessageDigest cript = MessageDigest.getInstance(STR_SHA);
            cript.reset();
            cript.update(serialisedSession);
            return String.format(STR_SIGNATURE_FORMAT, new BigInteger(1, cript.digest()).toString(16));
        } catch (Exception e) {
            log.error("Can't calculate signature", e);
        }
        return null;
    }
    
    /*
     * Method checking if the cookie is valid and setting the identity accordingly.If emails from the cookie and actual user email areidentical and it has not been
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
