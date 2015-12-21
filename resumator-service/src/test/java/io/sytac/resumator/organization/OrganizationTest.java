package io.sytac.resumator.organization;

import io.sytac.resumator.employee.Employee;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.model.enums.Nationality;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class OrganizationTest {

    @Test(expected = IllegalArgumentException.class)
    public void yearOfBirthMustBeAnInteger(){
        NewEmployeeCommand command = new NewEmployeeCommand("foo", "foo", "foo", "foo", "ITALY", "foo", Long.toString(new Date().getTime()));
        new Organization("foo", "foo", "foo").addEmployee(command);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nationalityCanOnlyHaveSpecificValues() {
        NewEmployeeCommand command = new NewEmployeeCommand("foo", "foo", "foo", "1984", "smurfland", "foo", Long.toString(new Date().getTime()));
        new Organization("foo", "foo", "foo").addEmployee(command);
    }

    @Test
    public void happyFlow() {
        NewEmployeeCommand command = new NewEmployeeCommand("ACME", "Name", "Surname", "1984", "ITALIAN", "Amsterdam", Long.toString(new Date().getTime()));
        final Employee employee = new Organization("ACME", "ACME", "acme.com").addEmployee(command);
        assertEquals("Wrong organization ID in Employee", Nationality.ITALIAN, employee.getNationality());
    }

}