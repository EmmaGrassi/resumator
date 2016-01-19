package io.sytac.resumator.http.command.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import io.sytac.resumator.command.CommandHeader;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.employee.NewEmployeeCommandDeserializer;
import io.sytac.resumator.employee.NewEmployeeCommandPayload;
import io.sytac.resumator.model.Course;
import io.sytac.resumator.model.Education;
import io.sytac.resumator.model.Experience;
import io.sytac.resumator.model.Language;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

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
        final List<Education> educations = Collections.singletonList(new Education(Education.Degree.MASTER_DEGREE, "Field", "School", "City", "Country", 2000, 2005));
        final List<Course> courses = Collections.singletonList(new Course("Course1", "Course 1", "1994"));
        final List<String> technologies = Arrays.asList("Java", "Turbo Pascal");
        final List<String> methodologies = Arrays.asList("Scrum", "Extreme programming");

        Date expStartDate = new GregorianCalendar(2010, Calendar.JANUARY, 1).getTime();
        Date expEndDate = new GregorianCalendar(2014, Calendar.JANUARY, 1).getTime();
        final List<Experience> experiences  = Collections.singletonList(new Experience("CompanyName", "Title", "City", "Country", "Short Description",
                technologies, methodologies, expStartDate, expEndDate));
        final List<Language> languages = Collections.singletonList(new Language("English", Language.Proficiency.FULL_PROFESSIONAL));
        final NewEmployeeCommandPayload payload = new NewEmployeeCommandPayload("sytac.io", "Title", "Foo", "Bar", "Email", "+31000999000",
                "Github", "Linkedin", "1984-04-22", "ITALY", "N", "About ME", educations, courses, experiences, languages);

        final CommandHeader commandHeader = new CommandHeader.Builder().setId("123-a-321-b")
                .setDomain("sytac.io")
                .build();
        final NewEmployeeCommand foobar = new NewEmployeeCommand(payload, commandHeader);

        final Long timestamp = foobar.getHeader().getTimestamp();
        final String expected =
                "{\"header\":" +
                    "{\"id\":\"123-a-321-b\"," +
                    "\"domain\":\"sytac.io\"," +
                    "\"timestamp\":" + timestamp + "}," +
                "\"payload\":" +
                    "{\"organizationDomain\":\"sytac.io\"," +
                    "\"title\":\"Title\"," +
                    "\"name\":\"Foo\"," +
                    "\"surname\":\"Bar\"," +
                    "\"email\":\"Email\"," +
                    "\"phonenumber\":\"+31000999000\"," +
                    "\"github\":\"Github\"," +
                    "\"linkedin\":\"Linkedin\"," +
                    "\"dateOfBirth\":\"1984-04-22\"," +
                    "\"nationality\":\"ITALY\"," +
                    "\"currentResidence\":\"N\"," +
                    "\"aboutMe\":\"About ME\"," +
                    "\"education\":" +
                        "[{\"degree\":\"MASTER_DEGREE\","+
                        "\"fieldOfStudy\":\"Field\"," +
                        "\"school\":\"School\"," +
                        "\"city\":\"City\"," +
                        "\"country\":\"Country\"," +
                        "\"startYear\":2000," +
                        "\"endYear\":2005}]," +
                    "\"courses\":" +
                        "[{\"name\":\"Course1\","+
                        "\"description\":\"Course 1\"," +
                        "\"year\":1994}]," +
                    "\"experience\":" +
                        "[{\"companyName\":\"CompanyName\","+
                        "\"title\":\"Title\"," +
                        "\"city\":\"City\"," +
                        "\"country\":\"Country\"," +
                        "\"shortDescription\":\"Short Description\"," +
                        "\"technologies\":[\"Java\",\"Turbo Pascal\"]," +
                        "\"methodologies\":[\"Scrum\",\"Extreme programming\"]," +
                        "\"startDate\":" + expStartDate.getTime() + "," +
                        "\"endDate\":" + expEndDate.getTime() + "}]," +
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
                            "{\"timestamp\":" + new Date().getTime() + "," +
                            "\"id\":\"123-a-321-b\"," +
                            "\"domain\":\"sytac.io\"}," +
                        "\"payload\":" +
                            "{\"organizationDomain\":\"foobar\"," +
                            "\"title\":\"Title\"," +
                            "\"name\":\"Foo\"," +
                            "\"surname\":\"Bar\"," +
                            "\"email\":\"Email\"," +
                            "\"phonenumber\":\"+31000999000\"," +
                            "\"github\":\"Gihub\"," +
                            "\"linkedin\":\"Linkedin\"," +
                            "\"dateOfBirth\":\"1984-04-22\"," +
                            "\"nationality\":\"ITALY\"," +
                            "\"currentResidence\":\"N\"}," +
                            "\"type\":\"newEmployee\"}";

        final NewEmployeeCommand command = mapper.readValue(expected.getBytes("UTF-8"), NewEmployeeCommand.class);
        assertEquals("Wrong deserialization", "sytac.io", command.getHeader().getDomain().get());
    }
}