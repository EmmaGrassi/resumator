package io.sytac.resumator.organization;

import io.sytac.resumator.command.CommandHeader;
import io.sytac.resumator.employee.Employee;
import io.sytac.resumator.employee.EmployeeCommandPayload;
import io.sytac.resumator.employee.EmployeeType;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.model.enums.Nationality;
import io.sytac.resumator.user.Profile;
import io.sytac.resumator.user.ProfileCommandPayload;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrganizationTest {

    private static final String ORGANIZATION_DOMAIN = "sytac.io";

    @Mock
    private Map<String, Employee> employees;

    @InjectMocks
    private Organization organization = new Organization("Sytac", ORGANIZATION_DOMAIN);

    @Test(expected = IllegalArgumentException.class)
    public void yearOfBirthMustBeAnInteger(){
        final ProfileCommandPayload profileCommandPayload = new ProfileCommandPayload("title", "name", "surname", "BLA",
                "email", "phonenumber", "ITALIAN", "ROME", "ITALY", "ABOUT", "github", "linkedin", false);
        final EmployeeCommandPayload employeeCommandPayload = new EmployeeCommandPayload(null, profileCommandPayload, null, null, null, null);
        createCommand(employeeCommandPayload);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nationalityCanOnlyHaveSpecificValues() {
        final ProfileCommandPayload profileCommandPayload = new ProfileCommandPayload("title", "name", "surname", "1984-04-22",
                "email", "phonenumber", "foo", "bar", "foobar", "ABOUT", "github", "linkedin", false);
        final EmployeeCommandPayload employeeCommandPayload = new EmployeeCommandPayload(null, profileCommandPayload, null, null, null, null);
        createCommand(employeeCommandPayload);
    }

    @Test
    public void happyFlow() {
        final ProfileCommandPayload profileCommandPayload = new ProfileCommandPayload("title", "name", "surname", "1984-04-22",
                "email", "phonenumber", "ITALIAN", "ROME", "ITALY", "ABOUT", "Github", "Linkedin", false);
        final EmployeeCommandPayload employeeCommandPayload = new EmployeeCommandPayload(null, profileCommandPayload, null, null, null, null);
        final Employee employee = createCommand(employeeCommandPayload);
        assertEquals("Wrong organization ID in Employee", Nationality.ITALIAN, employee.getProfile().getNationality());
    }

    @Test
    public void getEmployeesReturnsListOfEmployees() {
        List<Employee> expectedEmployees = Arrays.asList(getDummyEmployee("id1"), getDummyEmployee("id2"));
        when(employees.values()).thenReturn(expectedEmployees);

        List<Employee> actualEmployees = organization.getEmployees();

        assertThat(actualEmployees, equalTo(expectedEmployees));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getEmployeesReturnsAnImmutableList() {
        when(employees.values()).thenReturn(Collections.emptyList());

        organization.getEmployees().add(getDummyEmployee("dummy"));
    }

    @Test
    public void addEmployee_withOrganizationEmailAndTypeNotSet_typeShouldBeSetToEmployee() {
        final ProfileCommandPayload profileCommandPayload = new ProfileCommandPayload(null, null, null, "1984-04-22",
                "email@" + ORGANIZATION_DOMAIN, null, "ITALIAN", null, null, null, null, null, false);
        final EmployeeCommandPayload payload = new EmployeeCommandPayload(null, profileCommandPayload, null, null, null, null);

        Employee actual = createCommand(payload);
        assertThat(actual.getType(), equalTo(EmployeeType.EMPLOYEE));
    }

    @Test
    public void addEmployee_withNonOrganizationEmailAndTypeNotSet_typeShouldBeSetToProspect() {
        final ProfileCommandPayload profileCommandPayload = new ProfileCommandPayload(null, null, null, "1984-04-22",
                "email@dummy.com", null, "ITALIAN", null, null, null, null, null, false);
        final EmployeeCommandPayload payload = new EmployeeCommandPayload(null, profileCommandPayload, null, null, null, null);

        Employee actual = createCommand(payload);
        assertThat(actual.getType(), equalTo(EmployeeType.PROSPECT));
    }

    @Test
    public void addEmployee_withTypeSet_typeShouldRemainAsSet() {
        final ProfileCommandPayload profileCommandPayload = new ProfileCommandPayload(null, null, null, "1984-04-22",
                "email@dummy.com", null, "ITALIAN", null, null, null, null, null, false);
        final EmployeeCommandPayload payload = new EmployeeCommandPayload(EmployeeType.FREELANCER, profileCommandPayload, null, null, null, null);

        Employee actual = createCommand(payload);
        assertThat(actual.getType(), equalTo(payload.getType()));
    }

    private Employee createCommand(EmployeeCommandPayload employeeCommandPayload) {
        final CommandHeader header = CommandHeader.builder().id(UUID.randomUUID().toString())
                .domain("domain").timestamp(new Date().getTime()).build();
        final NewEmployeeCommand command = new NewEmployeeCommand(header, employeeCommandPayload);
        return organization.addEmployee(command);
    }

    private Employee getDummyEmployee(final String id) {
        final Profile profile = Profile.builder().email("dummy@email.com").build();
        return Employee.builder().id(id).profile(profile).build();
    }

}