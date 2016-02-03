package io.sytac.resumator.organization;

import io.sytac.resumator.command.CommandHeader;
import io.sytac.resumator.employee.Employee;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.employee.EmployeeCommandPayload;
import io.sytac.resumator.model.enums.Nationality;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;
import java.util.Date;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrganizationTest {

    @Mock
    private Map<String, Employee> employees;

    @InjectMocks
    private Organization organization = new Organization("Sytac", "sytac.io");

    @Test(expected = IllegalArgumentException.class)
    public void yearOfBirthMustBeAnInteger(){
        final EmployeeCommandPayload employeeCommandPayload = new EmployeeCommandPayload("title", "name", "surname",
                "email", "phonenumber", "github", "linkedin", "1984-04-22", "ITALY", "", "", null, null, null, null, false);
        createCommand(employeeCommandPayload);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nationalityCanOnlyHaveSpecificValues() {
        final EmployeeCommandPayload employeeCommandPayload = new EmployeeCommandPayload("title", "name", "surname",
                "email", "phonenumber", "github", "linkedin", "1984-04-22", "foo", "", "", null, null, null, null, false);
        createCommand(employeeCommandPayload);
    }

    @Test
    public void happyFlow() {
        final EmployeeCommandPayload employeeCommandPayload = new EmployeeCommandPayload("title", "Name", "Surname", "Email",
                "+31000999000", "Github", "Linkedin", "1984-04-22", "ITALIAN", "", "", null, null, null, null, false);
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