package io.sytac.resumator.organization;

import io.sytac.resumator.employee.Employee;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.employee.NewEmployeeCommandPayload;
import io.sytac.resumator.model.enums.Nationality;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class OrganizationTest {

    @Test(expected = IllegalArgumentException.class)
    public void yearOfBirthMustBeAnInteger(){
        NewEmployeeCommandPayload employeeCommandPayload = new NewEmployeeCommandPayload("domain", "title", "name", "surname",
                "email", "phonenumber", "github", "linkedin", "1984-04-22T00: 00: 00.000Z", "ITALY", "", null, null, null, null);
        NewEmployeeCommand command = new NewEmployeeCommand(employeeCommandPayload, "domain", Long.toString(new Date().getTime()));
        new Organization("foo", "foo", "foo").addEmployee(command);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nationalityCanOnlyHaveSpecificValues() {
        NewEmployeeCommandPayload employeeCommandPayload = new NewEmployeeCommandPayload("domain", "title", "name", "surname",
                "email", "phonenumber", "github", "linkedin", "1984-04-22T00: 00: 00.000Z", "foo", "", null, null, null, null);
        NewEmployeeCommand command = new NewEmployeeCommand(employeeCommandPayload, "domain", Long.toString(new Date().getTime()));
        new Organization("foo", "foo", "foo").addEmployee(command);
    }

    @Test
    public void happyFlow() {
        NewEmployeeCommandPayload employeeCommandPayload = new NewEmployeeCommandPayload("ACME", "title", "Name", "Surname", "Email",
                "+31000999000", "Github", "Linkedin", "1984-04-22T00:00:00.000Z", "ITALIAN", "", null, null, null, null);
        NewEmployeeCommand command = new NewEmployeeCommand(employeeCommandPayload, "domain", Long.toString(new Date().getTime()));
        final Employee employee = new Organization("ACME", "ACME", "acme.com").addEmployee(command);
        assertEquals("Wrong organization ID in Employee", Nationality.ITALIAN, employee.getNationality());
    }

}