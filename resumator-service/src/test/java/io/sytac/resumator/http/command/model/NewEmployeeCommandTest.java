package io.sytac.resumator.http.command.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Joiner;
import io.sytac.resumator.command.CommandHeader;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.employee.EmployeeCommandPayload;
import io.sytac.resumator.model.*;
import io.sytac.resumator.organization.NewOrganizationCommand;
import io.sytac.resumator.utils.DateUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class NewEmployeeCommandTest extends CommonCommandTest {

    public static final String NEW_EMPLOYEE_TYPE = "newEmployee";

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void canSerializeAsJSON(){
        canSerializeAsJSON(NewOrganizationCommand.class);
    }

    @Test
    public void JSONisPredictable() throws JsonProcessingException {
        final NewEmployeeCommand newEmployeeCommand = getNewEmployeeCommand();
        final String json = getJson(String.valueOf(newEmployeeCommand.getHeader().getTimestamp()),
                DateUtils.convert(getExperienceStartDate()),
                DateUtils.convert(getExperienceEndDate()));

        assertEquals("Json is different than expected", json, getObjectMapper().writeValueAsString(newEmployeeCommand));
    }

    @Test
    public void canRestoreFromString() throws IOException {
        final String json = getJson(String.valueOf(new Date().getTime()),
                DateUtils.convert(getExperienceStartDate()),
                DateUtils.convert(getExperienceEndDate()));

        final byte[] expectedBytes = json.getBytes("UTF-8");
        final NewEmployeeCommand command = getObjectMapper().readValue(expectedBytes, NewEmployeeCommand.class);
        assertEquals("Wrong deserialization", DOMAIN, command.getHeader().getDomain().get());
    }

    @Test
    public void canCreateEvent() {
        final NewEmployeeCommand newEmployeeCommand = getNewEmployeeCommand();
        final String json = getJson(String.valueOf(newEmployeeCommand.getHeader().getTimestamp()),
                DateUtils.convert(getExperienceStartDate()),
                DateUtils.convert(getExperienceEndDate()));
        final Event event = newEmployeeCommand.asEvent(getObjectMapper());

        assertNotNull(event);
        assertEquals(NEW_EMPLOYEE_TYPE, event.getType());
        assertEquals(json, event.getPayload());
    }

    private NewEmployeeCommand getNewEmployeeCommand() {
        final List<Experience> experiences  = Collections.singletonList(createExperience());
        final List<Education> educations = Collections.singletonList(createEducation());
        final List<Course> courses = Collections.singletonList(createCourse());
        final List<Language> languages = Collections.singletonList(createLanguage(ENGLISH_LANGUAGE));
        final EmployeeCommandPayload payload = createEmployeeCommandPayload(experiences, educations, courses, languages);
        final CommandHeader commandHeader = createCommandHeader(UUID, DOMAIN, new Date().getTime());
        return new NewEmployeeCommand(commandHeader, payload);
    }

    private String getJson(final String headerTimestamp, final String experienceStartDate, final String experienceEndDate) {
        return "{" +
            "\"header\":{" +
                "\"id\":\"" + UUID + "\"," +
                "\"domain\":\"" + DOMAIN + "\"," +
                "\"timestamp\":" + headerTimestamp +
            "}," +
            "\"payload\":{" +
                "\"title\":\"" + TITLE + "\"," +
                "\"name\":\"" + NAME + "\"," +
                "\"surname\":\"" + SURNAME + "\"," +
                "\"email\":\"" + EMAIL + "\"," +
                "\"phonenumber\":\"" + PHONENUMBER + "\"," +
                "\"github\":\"" + GITHUB + "\"," +
                "\"linkedin\":\"" + LINKEDIN + "\"," +
                "\"dateOfBirth\":\"" + DATE_OF_BIRTH + "\"," +
                "\"nationality\":\"" + NATIONALITY + "\"," +
                "\"currentResidence\":\"" + CURRENT_RESIDENCE + "\"," +
                "\"aboutMe\":\"" + ABOUT_ME + "\"," +
                "\"education\":[{" +
                    "\"degree\":\"" + Education.Degree.MASTER_DEGREE + "\","+
                    "\"fieldOfStudy\":\"" + FIELD_OF_STUDY + "\"," +
                    "\"school\":\"" + SCHOOL + "\"," +
                    "\"city\":\"" + CITY + "\"," +
                    "\"country\":\"" + COUNTRY + "\"," +
                    "\"startYear\":2000," +
                    "\"endYear\":2005" +
                "}]," +
                "\"courses\":[{" +
                    "\"name\":\"" + COURSE_NAME + "\","+
                    "\"description\":\"" + COURSE_DESCRIPTION + "\"," +
                    "\"year\":" + COURSE_YEAR +
                "}]," +
                "\"experience\":[{" +
                    "\"companyName\":\"" + COMPANY_NAME + "\","+
                    "\"title\":\"" + TITLE + "\"," +
                    "\"city\":\"" + CITY + "\"," +
                    "\"country\":\"" + COUNTRY + "\"," +
                    "\"shortDescription\":\"" + SHORT_DESCRIPTION + "\"," +
                    "\"technologies\":[\"" + Joiner.on("\",\"").join(getTechnologies())  + "\"]," +
                    "\"methodologies\":[\"" + Joiner.on("\",\"").join(getMethodologies()) + "\"]," +
                    "\"startDate\":\"" + experienceStartDate + "\"," +
                    "\"endDate\":\"" + experienceEndDate + "\"" +
                "}]," +
                "\"languages\":[{" +
                    "\"name\":\"" + ENGLISH_LANGUAGE + "\","+
                    "\"proficiency\":\"" + Language.Proficiency.FULL_PROFESSIONAL + "\"" +
                "}]" +
            "}," +
            "\"type\":\"" + NEW_EMPLOYEE_TYPE + "\"" +
        "}";
    }
}