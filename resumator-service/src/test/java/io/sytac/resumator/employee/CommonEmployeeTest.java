package io.sytac.resumator.employee;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.UriInfo;

import com.google.common.collect.Lists;
import io.sytac.resumator.user.Profile;
import io.sytac.resumator.user.ProfileCommandPayload;
import io.sytac.resumator.utils.DateUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.mockito.Mock;

import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.model.Course;
import io.sytac.resumator.model.Education;
import io.sytac.resumator.model.Education.Degree;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.security.Identity;
import io.sytac.resumator.security.Roles;

/**
 * Tests the NewEmployees resource
 */
@Ignore
public class CommonEmployeeTest {

    protected static final String ORG_ID = "org";
    protected static final String DOMAIN = "domain";
    protected static final String EMAIL = "email@dot.com";
    protected static final String WRONG_EMAIL = "wrong.email@dot.com";
    protected static final String UUID = "6743e653-f3cc-4580-84e8-f44ee8531128";
    protected static final String URI_BASE = "http://base.uri";
    protected static final String URI_ABSOLUTE_PATH = URI_BASE + "/employees";

    @Mock
    protected OrganizationRepository organizationRepositoryMock;

    @Mock
    protected Organization organizationMock;

    @Mock
    protected CommandFactory descriptorsMock;

    @Mock
    protected EventPublisher eventsMock;

    @Mock
    protected Identity identityMock;

    @Mock
    protected UriInfo uriInfoMock;

    @Mock
    protected Employee employeeMock;

    @Mock
    protected Profile profileMock;


    @Before
    public void before() throws URISyntaxException {
        when(organizationRepositoryMock.get(eq(ORG_ID))).thenReturn(Optional.of(organizationMock));

        when(organizationMock.getDomain()).thenReturn(DOMAIN);
        when(organizationMock.getEmployeeByEmail(eq(EMAIL))).thenReturn(employeeMock);

        when(identityMock.getName()).thenReturn(EMAIL);
        when(identityMock.hasRole(eq(Roles.ADMIN))).thenReturn(true);
        when(identityMock.getOrganizationId()).thenReturn(ORG_ID);

        when(employeeMock.getId()).thenReturn(UUID);
        when(employeeMock.getProfile()).thenReturn(profileMock);
        when(profileMock.getEmail()).thenReturn(EMAIL);

        when(uriInfoMock.getAbsolutePath()).thenReturn(new URI(URI_ABSOLUTE_PATH));
        when(uriInfoMock.getBaseUri()).thenReturn(new URI(URI_BASE));
    }

    protected EmployeeCommandPayload getEmployeeCommandPayload() {
        return getEmployeeCommandPayload(false, null);
    }


    protected EmployeeCommandPayload getEmployeeCommandPayload(boolean isAdmin) {
    	 return getEmployeeCommandPayload(isAdmin, null);
    }
    

    protected EmployeeCommandPayload getEmployeeCommandPayload(boolean isAdmin, EmployeeType type) {
        final ProfileCommandPayload profileCommandPayload = new ProfileCommandPayload("title", "name", "surname", "1966-01-01", EMAIL,
                "0212238989", "ANDORRAN", "Amsterdam", "Netherlands", "about", null, null, isAdmin);
        return new EmployeeCommandPayload(type, profileCommandPayload, new ArrayList<>(),new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }
    
    protected EmployeeCommandPayload getEmployeeDetailedValidatableCommandPayload() {
    	final List<Education> educations = Lists.newArrayList(
                new Education(Degree.ASSOCIATE_DEGREE, "economics", "Baroek", "New York", "USA", 2012, 2005));
    	final List<Course> courses = Lists.newArrayList(
                new Course("course", "IT intro", 2020));

        final ProfileCommandPayload profileCommandPayload = new ProfileCommandPayload("title", "name", "surname",
                "2020-01-01", EMAIL, "02122381132", "BOLIVIANIAN", "Amsterdam", "Netherlands", "about", null, null, true);
      	return new EmployeeCommandPayload(null, profileCommandPayload, educations, courses, new ArrayList<>(), new ArrayList<>());

	}
    
    protected EmployeeCommandPayload getEmployeeValidatableCommandPayload() {
        final ProfileCommandPayload profileCommandPayload = new ProfileCommandPayload("title", "name", null,
                "2010-01-01", "email", "0212238sa32", "ANDORRAN", "Amsterdam", "Netherlands", "about", null, null, true);
     	return new EmployeeCommandPayload(null, profileCommandPayload, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
 	}
}