package io.sytac.resumator.organization;

import io.sytac.resumator.command.CommandHeader;
import io.sytac.resumator.employee.Employee;
import io.sytac.resumator.employee.EmployeeCommandPayload;
import io.sytac.resumator.employee.EmployeeType;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.model.enums.Nationality;
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
        final EmployeeCommandPayload employeeCommandPayload = new EmployeeCommandPayload(null, "title", "name", "surname",
                "email", "phonenumber", "github", "linkedin", "1984-04-22", "ITALY","Netherlands","Amsterdam", "", "", null, null, null, null, false);
        createCommand(employeeCommandPayload);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nationalityCanOnlyHaveSpecificValues() {
        final EmployeeCommandPayload employeeCommandPayload = new EmployeeCommandPayload(null, "title", "name", "surname",
                "email", "phonenumber", "github", "linkedin", "1984-04-22", "foo","Netherlands","Amsterdam", "", "", null, null, null, null, false);
        createCommand(employeeCommandPayload);
    }

    @Test
    public void happyFlow() {
        final EmployeeCommandPayload employeeCommandPayload = new EmployeeCommandPayload(null, "title", "Name", "Surname", "Email",
                "+31000999000", "Github", "Linkedin", "1984-04-22", "ITALIAN","Netherlands","Amsterdam", "", "", null, null, null, null, false);
        final Employee employee = createCommand(employeeCommandPayload);
        assertEquals("Wrong organization ID in Employee", Nationality.ITALIAN, employee.getNationality());
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
        final EmployeeCommandPayload payload = new EmployeeCommandPayload(null, null, null, null,
                "email@" + ORGANIZATION_DOMAIN, null, null, null, "1984-04-22", "ITALIAN","Netherlands","Amsterdam", null, null,
                null, null, null, null, false);

        Employee actual = createCommand(payload);
        assertThat(actual.getType(), equalTo(EmployeeType.EMPLOYEE));
    }

    @Test
    public void addEmployee_withNonOrganizationEmailAndTypeNotSet_typeShouldBeSetToProspect() {
        final EmployeeCommandPayload payload = new EmployeeCommandPayload(null, null, null, null,
                "email@dummy.com", null, null, null, "1984-04-22", "ITALIAN","Netherlands","Amsterdam", null, null,
                null, null, null, null, false);

        Employee actual = createCommand(payload);
        assertThat(actual.getType(), equalTo(EmployeeType.PROSPECT));
    }

    @Test
    public void addEmployee_withTypeSet_typeShouldRemainAsSet() {
        final EmployeeCommandPayload payload = new EmployeeCommandPayload(EmployeeType.FREELANCER, null, null, null,
                "email@dummy.com", null, null, null, "1984-04-22", "ITALIAN","Netherlands","Amsterdam", null, null,
                null, null, null, null, false);

        Employee actual = createCommand(payload);
        assertThat(actual.getType(), equalTo(payload.getType()));
    }

    @Test
    public void employeeExists_employeeExists_returnsTrue() {
        final String email = "dummy@sytac.io";

        when(employees.containsKey(email)).thenReturn(true);

        assertThat(organization.employeeExists(email), equalTo(true));
    }

    @Test
    public void employeeExists_employeeDoesNotExist_returnsFalse() {
        final String email = "dummy@sytac.io";

        when(employees.containsKey(email)).thenReturn(false);

        assertThat(organization.employeeExists(email), equalTo(false));
    }

    private Employee createCommand(EmployeeCommandPayload employeeCommandPayload) {
        final CommandHeader header = new CommandHeader.Builder()
                .setId(UUID.randomUUID().toString())
                .setDomain("domain")
                .setTimestamp(new Date().getTime())
                .build();
        final NewEmployeeCommand command = new NewEmployeeCommand(header, employeeCommandPayload);
        return organization.addEmployee(command);
    }

    private Employee getDummyEmployee(final String id) {
        return Employee.builder().id(id).email("dummy@email.com").build();
    }

}