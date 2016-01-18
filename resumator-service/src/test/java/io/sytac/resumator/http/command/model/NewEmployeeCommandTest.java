package io.sytac.resumator.http.command.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.employee.NewEmployeeCommandDeserializer;
import io.sytac.resumator.employee.NewEmployeeCommandPayload;
import io.sytac.resumator.model.Course;
import io.sytac.resumator.model.Education;
import io.sytac.resumator.model.Experience;
import io.sytac.resumator.model.Language;
import io.sytac.resumator.model.enums.Degree;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NewEmployeeCommandTest {

    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper();
        final SimpleModule module = new SimpleModule();
        module.addDeserializer(NewEmployeeCommand.class, new NewEmployeeCommandDeserializer());
        mapper.registerModule(module);
        mapper.registerModule(new Jdk8Module());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    @Test
    public void canSerializeAsJSON(){
        assertTrue("Commands must be serializable as they end up in the DB",
                mapper.canSerialize(NewEmployeeCommand.class));
    }

    @Test
    public void JSONisPredictable() throws JsonProcessingException {
        final List<Education> education = Collections.singletonList(new Education(Degree.MASTER_DEGREE, "Field", "University", true, 2000));
        final List<Course> courses = Collections.singletonList(new Course("Course1", "Course 1", "1994-01-01T00:00:00.000Z"));
        final List<String> technologies = Arrays.asList("Java", "Turbo Pascal");
        final List<String> methodologies = Arrays.asList("Scrum", "Extreme programming");
        final List<Experience> experience  = Collections.singletonList(new Experience("CompanyName", "Title", "Location", "Short Description",
                technologies, methodologies, "1994-01-01T00:00:00.000Z", "1998-01-31T00:00:00.000Z"));
        final List<Language> languages = Collections.singletonList(new Language("English", "FULL_PROFESSIONAL"));
        final NewEmployeeCommandPayload payload = new NewEmployeeCommandPayload("ACME", "Title", "Foo", "Bar", "Email", "+31000999000",
                "Github", "Linkedin", "1984-04-22T00: 00: 00.000Z", "ITALY", "About ME", education, courses, experience, languages);
        final NewEmployeeCommand foobar = new NewEmployeeCommand(payload, "ACME", Long.toString(new Date().getTime()));

        final Date timestamp = foobar.getHeader().getTimestamp();
        final String expected =
                "{\"header\":" +
                    "{\"timestamp\":" + timestamp.getTime() + "}," +
                "\"payload\":" +
                    "{\"organizationDomain\":\"ACME\"," +
                    "\"title\":\"Title\"," +
                    "\"name\":\"Foo\"," +
                    "\"surname\":\"Bar\"," +
                    "\"email\":\"Email\"," +
                    "\"phonenumber\":\"+31000999000\"," +
                    "\"github\":\"Github\"," +
                    "\"linkedin\":\"Linkedin\"," +
                    "\"dateOfBirth\":\"1984-04-22T00: 00: 00.000Z\"," +
                    "\"nationality\":\"ITALY\"," +
                    "\"aboutMe\":\"About ME\"," +
                    "\"education\":" +
                        "[{\"degree\":\"MASTER_DEGREE\","+
                        "\"fieldOfStudy\":\"Field\"," +
                        "\"university\":\"University\"," +
                        "\"graduated\":true," +
                        "\"graduationYear\":2000}]," +
                    "\"courses\":" +
                        "[{\"name\":\"Course1\","+
                        "\"description\":\"Course 1\"," +
                        "\"date\":757378800000}]," +
                    "\"experience\":" +
                        "[{\"companyName\":\"CompanyName\","+
                        "\"title\":\"Title\"," +
                        "\"location\":\"Location\"," +
                        "\"shortDescription\":\"Short Description\"," +
                        "\"technologies\":[\"Java\",\"Turbo Pascal\"]," +
                        "\"methodologies\":[\"Scrum\",\"Extreme programming\"]," +
                        "\"startDate\":757378800000," +
                        "\"endDate\":886201200000}]," +
                    "\"languages\":" +
                        "[{\"name\":\"English\","+
                        "\"proficiency\":\"FULL_PROFESSIONAL\"}]}," +
                    "\"type\":\"newEmployee\"}";

        assertEquals("Json is different than expected",
                expected,
                mapper.writeValueAsString(foobar));
    }

    @Test
    public void canRestoreFromString() throws IOException {
        final String expected =
                        "{\"header\":" +
                            "{\"timestamp\":" + new Date().getTime() + "}," +
                        "\"payload\":" +
                            "{\"organizationDomain\":\"foobar\"," +
                            "\"title\":\"Title\"," +
                            "\"name\":\"Foo\"," +
                            "\"surname\":\"Bar\"," +
                            "\"email\":\"Email\"," +
                            "\"phonenumber\":\"+31000999000\"," +
                            "\"github\":\"Gihub\"," +
                            "\"linkedin\":\"Linkedin\"," +
                            "\"dateOfBirth\":\"1984-04-22T00: 00: 00.000Z\"," +
                            "\"nationality\":\"ITALY\"}," +
                            "\"type\":\"newEmployee\"}";

        final NewEmployeeCommand command = mapper.readValue(expected.getBytes("UTF-8"), NewEmployeeCommand.class);
        assertEquals("Wrong deserialization", "foobar", command.getPayload().getOrganizationDomain());
    }
}