package io.sytac.resumator.employee;


import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link Employee}s.
 */
public class EmployeeTest {

    @Test
    public void build_typeSet_typeBecomesAsSet() {
        Employee actualEmployee = Employee.builder().email("me@sytac.io").type(EmployeeType.FREELANCER).build();

        assertThat(actualEmployee.getType(), equalTo(EmployeeType.FREELANCER));
    }

    @Test
    public void build_sytacIoEmailAndTypeNotSet_typeBecomesEmployee() {
        Employee actualEmployee = Employee.builder().email("me@sytac.io").build();

        assertThat(actualEmployee.getType(), equalTo(EmployeeType.EMPLOYEE));
    }

    @Test
    public void build_nonSytacIoEmailAndTypeNotSet_typeBecomesEmployee() {
        Employee actualEmployee = Employee.builder().email("first.lastname@domain.tld").build();

        assertThat(actualEmployee.getType(), equalTo(EmployeeType.PROSPECT));
    }
}