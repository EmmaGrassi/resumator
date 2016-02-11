package io.sytac.resumator.user;

import com.theoryinpractise.halbuilder.api.Representation;
import io.sytac.resumator.security.Identity;
import io.sytac.resumator.security.Roles;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.NoPermissionException;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Tests the ProfilesQuery resource
 */
@RunWith(MockitoJUnitRunner.class)
public class ProfilesQueryTest {

    private static final String URI_BASE = "http://base.uri";

    private static final String URI_ABSOLUTE_PATH = URI_BASE + "/profiles";

    private static final String URI_REQUEST = URI_ABSOLUTE_PATH + "?page=1";

    @Mock
    private ProfileRepository profileRepositoryMock;

    @Mock
    private Identity identity;

    @Mock
    private UriInfo uriInfo;

    @InjectMocks
    private ProfilesQuery profilesQuery;


    @Before
    public void before() throws URISyntaxException {
        when(identity.hasRole(eq(Roles.ADMIN))).thenReturn(true);

        when(uriInfo.getAbsolutePath()).thenReturn(new URI(URI_ABSOLUTE_PATH));
        when(uriInfo.getBaseUri()).thenReturn(new URI(URI_BASE));
        when(uriInfo.getRequestUri()).thenReturn(new URI(URI_REQUEST));
    }

    @Test
    public void getProfilesPageLowerThan1ReturnsPage1() throws NoPermissionException {
        final List<Profile> profiles = getNumberOfProfiles(ProfilesQuery.DEFAULT_PAGE_SIZE + 1);

        when(profileRepositoryMock.getAll()).thenReturn(profiles);

        Representation actual = profilesQuery.getProfiles(0, identity, uriInfo);
        assertThat(actual.getResourcesByRel("profiles").size(), equalTo(ProfilesQuery.DEFAULT_PAGE_SIZE));
    }

    @Test
    public void getProfilesPage2ReturnsPage2() throws NoPermissionException {
        final List<Profile> profiles = getNumberOfProfiles(ProfilesQuery.DEFAULT_PAGE_SIZE + 1);

        when(profileRepositoryMock.getAll()).thenReturn(profiles);

        final Representation actual = profilesQuery.getProfiles(2, identity, uriInfo);
        assertThat(actual.getResourcesByRel("profiles").size(), equalTo(1));
    }

    @Test
    public void getProfilesPageHigherThanNumberPagesReturnsNoProfiles() throws NoPermissionException {
        final List<Profile> profiles = getNumberOfProfiles(5);

        when(profileRepositoryMock.getAll()).thenReturn(profiles);

        final Representation actual = profilesQuery.getProfiles(2, identity, uriInfo);
        assertThat(actual.getResourcesByRel("profiles").size(), equalTo(0));
    }

    @Test
    public void getProfilesReturnsExpectedLinks() throws NoPermissionException {
        final List<Profile> profiles = getNumberOfProfiles(ProfilesQuery.DEFAULT_PAGE_SIZE + 1);

        when(profileRepositoryMock.getAll()).thenReturn(profiles);

        final Representation actual = profilesQuery.getProfiles(1, identity, uriInfo);
        assertThat(actual.getLinkByRel("self").getHref(), equalTo(URI_REQUEST));
        assertThat(actual.getLinkByRel("profiles").getHref(), equalTo(URI_ABSOLUTE_PATH));
        assertThat(actual.getLinkByRel("next").getHref(), equalTo(URI_ABSOLUTE_PATH + "?page=2"));
    }

    @Test
    public void eachProfileInGetProfilesHasSelfLink() throws NoPermissionException {
        final List<Profile> profiles = getNumberOfProfiles(5);

        when(profileRepositoryMock.getAll()).thenReturn(profiles);

        final Representation actual = profilesQuery.getProfiles(1, identity, uriInfo);
        actual.getResourcesByRel("profiles")
                .forEach(resource -> assertThat(resource.getLinkByRel("self").getHref(),
                        equalTo(URI_ABSOLUTE_PATH + "/" + resource.getProperties().get("email"))));
    }

    private List<Profile> getNumberOfProfiles(final int numberOfProfile) {
        return Stream.generate(() -> getDummyProfile(UUID.randomUUID().toString()))
                .limit(numberOfProfile)
                .collect(toList());
    }

    private Profile getDummyProfile(final String id) {
        return new Profile(id, "title", "name", "surname", null, "email", "phoneNumber", null, null, null, null, null, null, false);
    }
}