package io.sytac.resumator.organization;

import io.sytac.resumator.employee.Employee;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.employee.NewEmployeeCommandPayload;
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

    @Mock
    private Map<String, Employee> employees;

    @InjectMocks
    private Organization organization = new Organization("Sytac", "sytac.io");

    @Test(expected = IllegalArgumentException.class)
    public void yearOfBirthMustBeAnInteger(){
        NewEmployeeCommandPayload employeeCommandPayload = new NewEmployeeCommandPayload("domain", "title", "name", "surname",
                "email", "phonenumber", "github", "linkedin", "1984-04-22", "ITALY", "", "", null, null, null, null);
        NewEmployeeCommand command = new NewEmployeeCommand(employeeCommandPayload, "domain", Long.toString(new Date().getTime()));
        organization.addEmployee(command);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nationalityCanOnlyHaveSpecificValues() {
        NewEmployeeCommandPayload employeeCommandPayload = new NewEmployeeCommandPayload("domain", "title", "name", "surname",
                "email", "phonenumber", "github", "linkedin", "1984-04-22", "foo", "", "", null, null, null, null);
        NewEmployeeCommand command = new NewEmployeeCommand(employeeCommandPayload, "domain", Long.toString(new Date().getTime()));
        organization.addEmployee(command);
    }

    @Test
    public void happyFlow() {
        NewEmployeeCommandPayload employeeCommandPayload = new NewEmployeeCommandPayload("ACME", "title", "Name", "Surname", "Email",
                "+31000999000", "Github", "Linkedin", "1984-04-22", "ITALIAN", "", "", null, null, null, null);
        NewEmployeeCommand command = new NewEmployeeCommand(employeeCommandPayload, "domain", Long.toString(new Date().getTime()));
        final Employee employee = organization.addEmployee(command);
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

    private Employee getDummyEmployee(final String id) {
        return new Employee(id, "title", "name", "surname", "email", "phoneNumber", null, null, null, null,
                null, null, null, null, null, null);
    }

}