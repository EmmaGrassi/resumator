package io.sytac.resumator.http.command.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import io.sytac.resumator.command.CommandHeader;
import io.sytac.resumator.employee.DateToStringSerializer;
import io.sytac.resumator.employee.EmployeeCommandPayload;
import io.sytac.resumator.employee.EmployeeType;
import io.sytac.resumator.employee.RemoveEmployeeCommand;
import io.sytac.resumator.model.Course;
import io.sytac.resumator.model.Education;
import io.sytac.resumator.model.Experience;
import io.sytac.resumator.model.Language;
import lombok.Getter;
import lombok.Setter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

@Ignore
public class CommonCommandTest {

    public static final String UUID = "6743e653-f3cc-4580-84e8-f44ee8531128";
    public static final String DOMAIN = "sytac.io";
    public static final String CITY = "City";
    public static final String COUNTRY = "Country";
    public static final String SHORT_DESCRIPTION = "Short Description";
    public static final String FIELD_OF_STUDY = "Field of study";
    public static final String SCHOOL = "School";
    public static final EmployeeType TYPE = EmployeeType.EMPLOYEE;
    public static final String TITLE = "Role";
    public static final String COMPANY_NAME = "CompanyName";
    public static final String NAME = "Foo";
    public static final String SURNAME = "Bar";
    public static final String EMAIL = "Email";
    public static final String PHONENUMBER = "+31000999000";
    public static final String GITHUB = "Github";
    public static final String LINKEDIN = "Linkedin";
    public static final String DATE_OF_BIRTH = "1984-04-22";
    public static final String NATIONALITY = "ITALY";
    public static final String CURRENT_RESIDENCE = "ITALY";
    public static final String ABOUT_ME = "About ME";
    public static final String ENGLISH_LANGUAGE = "English";
    public static final String DUTCH_LANGUAGE = "Dutch";
    public static final String COURSE_NAME = "Scala for dummies";
    public static final String COURSE_DESCRIPTION = "Scala for dummies description";
    public static final int COURSE_YEAR = 1994;

    @Getter
    private ObjectMapper objectMapper;

    @Getter
    @Setter
    private Date experienceStartDate = new GregorianCalendar(2010, Calendar.JANUARY, 1).getTime();
    @Getter
    @Setter
    private Date experienceEndDate = new GregorianCalendar(2014, Calendar.JANUARY, 1).getTime();
    @Getter
    @Setter
    private List<String> technologies = Arrays.asList("Java", "Turbo Pascal");
    @Getter
    @Setter
    private List<String> methodologies = Arrays.asList("Scrum", "Extreme programming");



    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        final SimpleModule module = new SimpleModule();
        module.addSerializer(Date.class, new DateToStringSerializer());
        objectMapper.registerModule(module);
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    protected Experience createExperience() {
        return new Experience(COMPANY_NAME, TITLE, CITY, COUNTRY, SHORT_DESCRIPTION,
                getTechnologies(), getMethodologies(), getExperienceStartDate(), Optional.of(getExperienceEndDate()));
    }

    protected Education createEducation() {
        return new Education(Education.Degree.MASTER_DEGREE, FIELD_OF_STUDY, SCHOOL, CITY, COUNTRY, 2000, 2005,"");
    }

    protected Course createCourse() {
        return new Course(COURSE_NAME, COURSE_DESCRIPTION, COURSE_YEAR);
    }

    protected Language createLanguage(final String language) {
        return new Language(language, Language.Proficiency.FULL_PROFESSIONAL);
    }

    protected CommandHeader createCommandHeader(final String uuid, final String domain, final Long timestamp) {
        return new CommandHeader.Builder().setId(uuid).setDomain(domain).setTimestamp(timestamp).build();
    }

    protected EmployeeCommandPayload createEmployeeCommandPayload(List<Experience> experiences,
                                                                  List<Education> educations,
                                                                  List<Course> courses,
                                                                  List<Language> languages) {

        return new EmployeeCommandPayload(TYPE, TITLE, NAME, SURNAME, EMAIL, PHONENUMBER, GITHUB, LINKEDIN, DATE_OF_BIRTH,
                NATIONALITY, CURRENT_RESIDENCE, ABOUT_ME, educations, courses, experiences, languages, false);
    }

    public void canSerializeAsJSON(Class clazz) {
        assertTrue("Commands must be serializable as they end up in the DB",
                getObjectMapper().canSerialize(clazz));
    }
}