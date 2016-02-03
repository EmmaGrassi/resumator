package io.sytac.resumator.employee;

import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.model.Course;
import io.sytac.resumator.model.Education;
import io.sytac.resumator.model.Education.Degree;
import io.sytac.resumator.model.exceptions.InvalidOrganizationException;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.security.User;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Tests the Employees resource
 */
@RunWith(MockitoJUnitRunner.class)
public class UpdateEmployeeTest {

    public static final String ORG_ID = "org";
    private static final String URI_BASE = "http://base.uri";
    private static final String URI_ABSOLUTE_PATH = URI_BASE + "/employees";
    public static final String UUID = "6743e653-f3cc-4580-84e8-f44ee8531128";
    public static final String DOMAIN = "domain";

    @Mock
    private OrganizationRepository organizationRepositoryMock;

    @Mock
    private Organization organizationMock;

    @Mock
    private CommandFactory descriptorsMock;

    @Mock
    private EventPublisher eventsMock;

    @Mock
    private User userMock;

    @Mock
    private UriInfo uriInfoMock;

    @Mock
    private Employee employeeMock;

    @Mock
    private UpdateEmployeeCommand updateEmployeeCommandMock;

    @InjectMocks
    private UpdateEmployee updateEmployee;


    @Before
    public void before() throws URISyntaxException {
        when(organizationRepositoryMock.get(eq(ORG_ID))).thenReturn(Optional.of(organizationMock));
        when(descriptorsMock.updateEmployeeCommand(eq(UUID), any(EmployeeCommandPayload.class), eq(DOMAIN))).thenReturn(updateEmployeeCommandMock);
        when(organizationMock.getDomain()).thenReturn(DOMAIN);
        when(organizationMock.updateEmployee(eq(updateEmployeeCommandMock))).thenReturn(employeeMock);
        when(userMock.getOrganizationId()).thenReturn(ORG_ID);
        when(employeeMock.getId()).thenReturn(UUID);

        when(uriInfoMock.getAbsolutePath()).thenReturn(new URI(URI_ABSOLUTE_PATH));
        when(uriInfoMock.getBaseUri()).thenReturn(new URI(URI_BASE));
    }

    @Test
    public void testNewEmployeesOk() {
        final Response response = updateEmployee.updateEmployee(UUID, getEmployeeCommandPayload(), userMock, uriInfoMock);
        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.SC_OK);
        assertEquals(response.getHeaderString("Location"), URI_ABSOLUTE_PATH + "/" + UUID);
    }

    @Test(expected = InvalidOrganizationException.class)
    public void testNewEmployeesWrongOrganisation() {
        when(organizationRepositoryMock.get(eq(ORG_ID))).thenThrow(InvalidOrganizationException.class);
        updateEmployee.updateEmployee(UUID, getEmployeeCommandPayload(), userMock, uriInfoMock);
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void testUpdateEmployeesValidation() {
        final Response response = updateEmployee.updateEmployee(UUID,getEmployeeValidatableCommandPayload(), userMock, uriInfoMock);
        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.SC_BAD_REQUEST);
        Map<String,Object> validationErrors= new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        Reader reader = new StringReader(response.getEntity().toString());
        
        try {
			validationErrors = objectMapper.readValue(reader, HashMap.class);
		} catch (Exception e) {
			throw new IllegalArgumentException("An error occured with http response");
		}
        Map<String,String> fields=(Map<String, String>) validationErrors.get("fields");
        assertNotNull("Email validation is not done.", fields.get("email"));
        assertNotNull("phonenumber validation is not done.", fields.get("phonenumber"));
        assertNotNull("surname validation is not done.", fields.get("surname"));

    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void testUpdateEmployeesDetailedValidation() {
        final Response response = updateEmployee.updateEmployee(UUID,getEmployeeDetailedValidatableCommandPayload(), userMock, uriInfoMock);
        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.SC_BAD_REQUEST);
        Map<String,Object> validationErrors= new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        Reader reader = new StringReader(response.getEntity().toString());
        
        try {
			validationErrors = objectMapper.readValue(reader, HashMap.class);
		} catch (Exception e) {
			throw new IllegalArgumentException("An error occured with http response");
		}
        Map<String,String> fields=(Map<String, String>) validationErrors.get("fields");
        assertNotNull("Education startyear validation is not done.", fields.get("Education.startyear"));
        assertNotNull("dateOfBirth validation is not done.", fields.get("dateOfBirth"));
        assertNotNull("nationality validation is not done.", fields.get("nationality"));
        assertNotNull("course year validation is not done.", fields.get("course.year"));

    }

    private EmployeeCommandPayload getEmployeeDetailedValidatableCommandPayload() {

    	Education education=new  Education(Degree.ASSOCIATE_DEGREE, "economics", "Baroek", "New York", "USA", 2012, 2005);
    	List<Education> educations=new ArrayList<>();
    	educations.add(education);
    	
    	Course course=new  Course("course", "IT intro", 2020);
    	List<Course> courses=new ArrayList<>();
    	courses.add(course);
    	
      	return new EmployeeCommandPayload("title", "name", "surname", "email@email.com", "02122381132", null,null,"2020-01-01","HAYMATLOS", "Netherlands", "about", educations,courses,
        		new ArrayList<>(), new ArrayList<>());

	}

	private EmployeeCommandPayload getEmployeeValidatableCommandPayload() {
		
    	return new EmployeeCommandPayload("title", "name", null, "email", "0212238sa32", null,null,"2020-01-01","ANDORRAN", "Netherlands", "about", new ArrayList<>(),new ArrayList<>(),
        		new ArrayList<>(), new ArrayList<>());
	}

	private EmployeeCommandPayload getEmployeeCommandPayload() {
        return new EmployeeCommandPayload("title", "name", "surname", "email@email.com", "0212238989", null,null,"1966-01-01","ANDORRAN", "Netherlands", "about", new ArrayList<>(),new ArrayList<>(),
        		new ArrayList<>(), new ArrayList<>());
    }
}