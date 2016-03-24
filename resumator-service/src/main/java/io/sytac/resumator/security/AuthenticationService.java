package io.sytac.resumator.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.sytac.resumator.Configuration;
import io.sytac.resumator.ConfigurationEntries;
import io.sytac.resumator.exception.ResumatorInternalException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

@Slf4j
@Getter
public class AuthenticationService {

    private final Configuration config;
    private final Oauth2SecurityService securityService;
    private final ObjectMapper objectMapper;

    @Inject
    public AuthenticationService(final Configuration config, final ObjectMapper objectMapper,
            final Oauth2SecurityService securityService) {
        this.config = config;
        this.securityService = securityService;
        this.objectMapper = objectMapper;
    }

    /*
     * Method encrypting the cookie on AES standart.Key is retrieved from
     * config.properties file placed on ~home directory.
     */
    public String encryptEntity(String entity, String key) {
        try {
            Cipher aes = createChiper(Cipher.ENCRYPT_MODE, key);
            byte[] bytes = SerializationUtils.serialize(entity);
            String encryptedCookie = DatatypeConverter.printHexBinary(aes.doFinal(bytes));
            String signature = calculateSignature(bytes).toUpperCase();
            return encryptedCookie + signature;
        } catch (Exception e) {
            log.error("Can't encrypt the cookie", e);
            throw new ResumatorInternalException("Can't encrypt the cookie", e);
        }
    }

    public String decryptEntity(String entity, String key) {
        try {
            String signature = entity.substring(entity.length() - 40);
            String encryptedCookie = entity.substring(0, entity.length() - 40);

            Cipher aes = createChiper(Cipher.DECRYPT_MODE, key);
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

    private Cipher createChiper(int mode, String key) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException {
        Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");

        if (!StringUtils.isEmpty(key)) {
            aes.init(mode, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(new byte[16]));
            return aes;
        } else
            throw new InvalidKeyException("Encryption key should be defined!");
    }

    private String calculateSignature(byte[] serialisedSession) {
        try {
            MessageDigest cript = MessageDigest.getInstance("SHA-1");
            cript.reset();
            cript.update(serialisedSession);
            return String.format("%1$40s", new BigInteger(1, cript.digest()).toString(16));
        } catch (Exception e) {
            log.error("Can't calculate signature", e);
        }
        return null;
    }

    public String produceXsrfToken(String userName) {

        Long timeStamp = new Date().getTime();
        String salt = generateSalt();

        Nonce nonce = new Nonce(userName, timeStamp.toString(), salt);

        String key = config.getProperty(ConfigurationEntries.XSRF_KEY).get();

        String jsonValue;
        try {
            jsonValue = objectMapper.writeValueAsString(nonce);
        } catch (JsonProcessingException e) {
            
            log.error("An error occured encrypting the CSRF token." +e.getMessage()+" "+e.getCause());
            
            return null;
        }
        String encryptedToken = encryptEntity(jsonValue, key);
        
        return encryptedToken;
    }

    private String generateSalt() {

        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }

        return sb.toString();
    }

}
