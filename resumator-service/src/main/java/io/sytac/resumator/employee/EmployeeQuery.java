package io.sytac.resumator.employee;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.model.*;
import io.sytac.resumator.model.enums.Nationality;
import io.sytac.resumator.docx.DocxGenerator;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Retrieve information about one employee
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("employee/{id}")
@RolesAllowed({"user"})
public class EmployeeQuery extends BaseResource {

    private static final String CONTENT_TYPE_DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    private final DocxGenerator docxGenerator;

    @Inject
    public EmployeeQuery(final DocxGenerator docxGenerator) {
        this.docxGenerator = docxGenerator;
    }

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    @Consumes("application/json")
    public Representation fakeEmployee(@PathParam("id") final String id, @Context final UriInfo uriInfo) {
        //throw new WebApplicationException("Cannot get an employee yet, sorry!", HttpStatus.NOT_IMPLEMENTED_501);
        Employee res = new Employee("Jimi", "Hendrix", 1942, Nationality.AMERICAN, "Heaven");
        return represent(res, uriInfo);
    }

    @GET
    @Produces(CONTENT_TYPE_DOCX)
    public Response getPdf(@PathParam("id") final String id) throws IOException {
        Resume stubResume = getStubResume();
        Employee employee = stubResume.getEmployee();

        return Response
                .ok(docxGenerator.generate(getTemplateStream(), getPlaceholderMappings(stubResume)), CONTENT_TYPE_DOCX)
                .header("content-disposition", String.format("attachment; filename = %s_%s.docx", employee.getName(), employee.getSurname()))
                .build();
    }

    /**
     * Translates an {@link Employee} into its HAL representation
     *
     * @param employee The employee to represent
     * @param uriInfo  The current REST endpoint information
     * @return The {@link Representation} of the {@link Employee}
     */
    private Representation represent(final Employee employee, final UriInfo uriInfo) {

        return rest.newRepresentation()
                .withProperty("id", employee.getId().toString())
                .withProperty("name", employee.getName())
                .withProperty("surname", employee.getSurname())
                .withProperty("nationality", employee.getNationality())
                .withProperty("current-residence", employee.getCurrentResidence())
                .withProperty("year-of-birth", employee.getYearOfBirth())
                .withLink("self", uriInfo.getRequestUri().toString());
    }

    private InputStream getTemplateStream() {
        return getClass().getClassLoader().getResourceAsStream("resume-template.docx");
    }

    private Resume getStubResume() {
        String jobTitle = "Senior Node.js Developer";
        String bio = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi magna ante, convallis vel auctor " +
                "sed, tristique nec velit. In lacinia vitae massa at porttitor. In pretium justo urna, sit amet " +
                "vulputate ipsum interdum vel. Morbi pretium diam ac leo laoreet, in aliquet mi iaculis. Donec a tempor " +
                "neque.";

        return new Resume(jobTitle, getEmployee(), bio, getLanguages(), getCourses(), getEducations(), getExperiences());
    }

    private Employee getEmployee() {
        return new Employee("Peter", "Janssen", 1980, Nationality.DUTCH, "Amsterdam");
    }

    private List<Education> getEducations() {
        List<Education> result = new ArrayList<>();

        result.add(new Education(Education.Degree.MASTER_DEGREE, "Computer Science", "Universiteit Utrecht", "Utrecht",
                "The Netherlands", 2012, 2014));
        result.add(new Education(Education.Degree.BACHELOR_DEGREE, "Computer Engineering", "Hogeschool van Amsterdam",
                "Amsterdam", "The Netherlands", 2008, 2012));

        return result;
    }

    private List<Course> getCourses() {
        List<Course> result = new ArrayList<>();

        result.add(new Course("Scala Basics", "New thinking..", 2015));
        result.add(new Course("Java EE Fundamentals", "The basics of..", 2013));
        result.add(new Course("Certified ScrumMaster", "Not only technical..", 2013));
        result.add(new Course("AngularJS", "Still new..",  2012));
        result.add(new Course("Javascript", "In depth..", 2010));

        return result;
    }

    private List<Language> getLanguages() {
        List<Language> result = new ArrayList<>();

        result.add(new Language("Dutch", Language.Proficiency.NATIVE));
        result.add(new Language("English", Language.Proficiency.FULL_PROFESSIONAL));
        result.add(new Language("German", Language.Proficiency.ELEMENTARY));

        return result;
    }

    private List<Experience> getExperiences() {
        List<Experience> result = new ArrayList<>();

        List<String> technologies = Arrays.asList("Javascript", "AngularJS", "jQuery", "Underscore.js", "NPM", "Bower",
                "Grunt", "Karma", "Jasmine", "Protractor", "Bootstrap" ,"Git", "SASS");
        List<String> methodologies = Arrays.asList("Scrum", "Kanban");
        Date startDate = new GregorianCalendar(2014, Calendar.JANUARY, 1).getTime();
        Date endDate = null;

        result.add(new Experience("Company 1", "Front-End Developer", "Den Haag", "The Netherlands", "Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit. Phasellus vel ante sit amet velit accumsan posuere. Interdum et malesuada " +
                "fames ac ante ipsum primis in faucibus. Etiam in enim vel mauris posuere imperdiet. Donec et mattis " +
                "risus. Nunc lobortis tellus eu facilisis dapibus. Vivamus iaculis risus aliquam tellus vestibulum " +
                "dapibus. In mauris sem, fringilla vitae bibendum et, luctus in urna. Pellentesque mattis eget felis id " +
                "laoreet. Duis molestie eu ligula a tincidunt. In gravida justo nec erat interdum, nec tincidunt sapien " +
                "rutrum.\n\nQuisque suscipit laoreet pulvinar. Integer a orci ex. Aliquam bibendum posuere nulla, vitae " +
                "commodo elit vehicula sit amet. Phasellus blandit justo porta lacus dapibus interdum. Quisque sagittis " +
                "nec ante bibendum scelerisque. Pellentesque vitae magna nibh. Donec lacus turpis, faucibus vel " +
                "pellentesque vel, tincidunt at augue. In at laoreet lacus. Curabitur malesuada suscipit turpis et rutrum. " +
                "Aenean eget consequat purus. In quis pulvinar dolor, nec placerat risus. Class aptent taciti sociosqu " +
                "ad litora torquent per conubia nostra, per inceptos himenaeos.", technologies, methodologies,
                startDate, endDate));

        technologies = Arrays.asList("Java EE", "Spring Web", "Hibernate", "Oracle DB", "JUnit", "Mockito", "Cucumber",
                "Logstash", "Kibana", "ElasticSearch", "AOP", "Git", "REST");

        methodologies = Collections.singletonList("Scrum");
        startDate = new GregorianCalendar(2010, Calendar.MARCH, 17).getTime();
        endDate = new GregorianCalendar(2013, Calendar.DECEMBER, 30).getTime();

        result.add(new Experience("Company 2", "Back-End Developer", "Amsterdam", "The Netherlands", "In quis lacus nec " +
                "purus rhoncus molestie in vitae erat. Integer tristique consequat lectus, et blandit augue. Nam dictum " +
                "vestibulum massa, iaculis commodo erat pellentesque non. Vestibulum nec turpis tortor. Sed in accumsan " +
                "tortor, nec consequat nunc. Quisque massa dui, hendrerit sed odio mollis, luctus congue risus. " +
                "Phasellus interdum enim sit amet arcu imperdiet ullamcorper. Curabitur porttitor ornare risus eu tempus. " +
                "Nam id pretium dui. Praesent sodales, odio vitae laoreet posuere, nulla sem fermentum ligula, id hendrerit " +
                "ipsum nunc at nisi. Maecenas egestas ultricies ante, non ornare ex dictum sed. Morbi vel dolor sed ligula maximus " +
                "mattis sed et felis.\n\nInteger ut consectetur libero, nec interdum lorem. Ut ante magna, vehicula nec " +
                "lectus sit amet, pulvinar elementum orci. Sed viverra eleifend nulla, rutrum dapibus neque ultrices a. " +
                "Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Morbi " +
                "blandit fringilla nisi ac viverra. Vivamus in tincidunt ex, quis mollis tortor. Curabitur lectus lectus, " +
                "venenatis a pellentesque id, lobortis vel lorem. Pellentesque vulputate, erat at aliquam cursus, enim " +
                "neque ultricies libero, et blandit magna augue quis tellus.", technologies, methodologies,
                startDate, endDate));

        return result;
    }


    private Map<String, String> getPlaceholderMappings(Resume resume) {
        Map<String, String> result = new HashMap<>();

        result.putAll(getPersonaliaMappings(resume));
        result.putAll(getExperienceMappings(resume.getExperiences()));
        result.putAll(getEducationMappings(resume.getEducations()));
        result.putAll(getCourseMappings(resume.getCourses()));
        result.putAll(getLanguageMappings(resume.getLanguages()));

        return result;
    }

    private Map<String, String> getPersonaliaMappings(Resume resume) {
        Map<String, String> result = new HashMap<>();
        result.put("JobTitle", resume.getJobTitle());
        result.put("FirstName", resume.getEmployee().getName());
        result.put("LastName", resume.getEmployee().getSurname());
        result.put("YearOfBirth", String.valueOf(resume.getEmployee().getYearOfBirth()));
        result.put("CurrentResidence", resume.getEmployee().getCurrentResidence());
        result.put("Nationality", resume.getEmployee().getNationality().toString());
        result.put("Bio", resume.getShortBio());
        return result;
    }

    private Map<String, String> getExperienceMappings(List<Experience> experiences) {
        Map<String, String> result = new HashMap<>();

        for (int i = 1; i <= experiences.size(); i++) {
            Experience experience = experiences.get(i - 1);
            result.put("Experience.Position" + i, experience.getTitle());
            result.put("Experience.Period" + i, getPeriod(experience.getStartDate(), experience.getEndDate()));
            result.put("Experience.CompanyName" + i, experience.getCompanyName());
            result.put("Experience.City" + i, experience.getCity());
            result.put("Experience.Country" + i, experience.getCountry());
            result.put("Experience.Description" + i, experience.getShortDescription());
            result.put("Experience.Technologies" + i, StringUtils.join(experience.getTechnologies(), ", "));
            result.put("Experience.Methodologies" + i, StringUtils.join(experience.getMethodologies(), ", "));
        }

        return result;
    }

    private Map<String, String> getEducationMappings(List<Education> educations) {
        Map<String, String> result = new HashMap<>();

        for (int i = 1; i <= educations.size(); i++) {
            Education education = educations.get(i - 1);
            result.put("Education.Degree" + i, education.getDegree().asText());
            result.put("Education.Field" + i, education.getFieldOfStudy());
            result.put("Education.School" + i, education.getSchool());
            result.put("Education.City" + i, education.getCity());
            result.put("Education.Country" + i, education.getCountry());
            result.put("Education.Period" + i, getPeriod(education.getStartYear(), education.getEndYear()));
        }

        return result;
    }

    private Map<String, String> getCourseMappings(List<Course> courses) {
        Map<String, String> result = new HashMap<>();

        for (int i = 1; i <= courses.size(); i++) {
            Course course = courses.get(i - 1);

            result.put("Course.Name" + i, course.getName());
            result.put("Course.Year" + i, String.valueOf(course.getYear()));
        }

        return result;
    }

    private Map<String, String> getLanguageMappings(List<Language> languages) {
        Map<String, String> result = new HashMap<>();

        for (int i = 1; i <= languages.size(); i++) {
            Language language = languages.get(i - 1);

            result.put("Language.Name" + i, language.getName());
            result.put("Language.Proficiency" + i, language.getProficiency().asText());
        }

        return result;
    }

    private String getPeriod(int startYear, int endYear) {
        if (startYear != endYear) {
            return startYear + " - " + endYear;
        } else {
            return String.valueOf(startYear);
        }
    }

    private String getPeriod(Date startDate, Date endDate) {
        SimpleDateFormat df = new SimpleDateFormat("MMM. yyyy");

        String period = df.format(startDate) + " - ";

        if (endDate == null) {
            return period + "present";
        } else {
            return period + df.format(endDate);
        }
    }
}
