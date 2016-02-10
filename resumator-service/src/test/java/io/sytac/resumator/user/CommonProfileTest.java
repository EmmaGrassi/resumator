package io.sytac.resumator.user;

import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.model.enums.Nationality;
import io.sytac.resumator.security.Identity;
import io.sytac.resumator.security.Roles;
import org.junit.Before;
import org.junit.Ignore;
import org.mockito.Mock;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Tests the NewProfile resource
 */
@Ignore
public class CommonProfileTest {

    protected static final String EMAIL = "email@dot.com";
    protected static final String WRONG_EMAIL = "wrong.email@dot.com";
    protected static final String UUID = "6743e653-f3cc-4580-84e8-f44ee8531128";
    protected static final String URI_BASE = "http://base.uri";
    protected static final String URI_ABSOLUTE_PATH = URI_BASE + "/profiles";
    protected static final String NATIONALITY = Nationality.ANDORRAN.toString().toUpperCase();
    protected static final String CITY_OF_RESIDENCE = "Amsterdam";
    protected static final String COUNTRY_OF_RESIDENCE = "Netherlands";
    protected static final String TITLE = "title";
    protected static final String NAME = "name";
    protected static final String SURNAME = "surname";
    protected static final String DATE_OF_BIRTH = "2010-01-01";
    protected static final String ABOUT_ME = "about me";

    @Mock
    protected ProfileRepository profileRepositoryMock;

    @Mock
    protected CommandFactory descriptorsMock;

    @Mock
    protected EventPublisher eventsMock;

    @Mock
    protected Identity identityMock;

    @Mock
    protected UriInfo uriInfoMock;

    @Mock
    protected Profile profileMock;


    @Before
    public void before() throws URISyntaxException {
        when(identityMock.getName()).thenReturn(EMAIL);
        when(identityMock.hasRole(eq(Roles.ADMIN))).thenReturn(true);

        when(profileMock.getEmail()).thenReturn(EMAIL);

        when(uriInfoMock.getAbsolutePath()).thenReturn(new URI(URI_ABSOLUTE_PATH));
        when(uriInfoMock.getBaseUri()).thenReturn(new URI(URI_BASE));
    }

    protected ProfileCommandPayload getProfileCommandPayload() {
        return getProfileCommandPayload(false);
    }


    protected ProfileCommandPayload getProfileCommandPayload(boolean isAdmin) {
        return new ProfileCommandPayload(TITLE, NAME, SURNAME, DATE_OF_BIRTH,
                EMAIL, "+30612365430", NATIONALITY, CITY_OF_RESIDENCE, COUNTRY_OF_RESIDENCE, ABOUT_ME, null, null, isAdmin);
    }

    protected ProfileCommandPayload getProfileDetailedValidatableCommandPayload() {
        return new ProfileCommandPayload(TITLE, NAME, SURNAME, "2020-01-01",
                EMAIL, "02122381132", "BOLIVIANIAN", CITY_OF_RESIDENCE, COUNTRY_OF_RESIDENCE, ABOUT_ME, null, null, true);
	}
    
    protected ProfileCommandPayload getProfileValidatableCommandPayload() {
        return new ProfileCommandPayload(TITLE, NAME, null, DATE_OF_BIRTH,
                "email", "0212238sa32", NATIONALITY, CITY_OF_RESIDENCE, COUNTRY_OF_RESIDENCE, ABOUT_ME, null, null, true);
 	}
}