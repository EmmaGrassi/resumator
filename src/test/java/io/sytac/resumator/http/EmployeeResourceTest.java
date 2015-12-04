package io.sytac.resumator.http;

import com.theoryinpractise.halbuilder.api.Representation;
import io.sytac.resumator.AbstractResumatorTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by skuro on 04/12/15.
 */
public class EmployeeResourceTest extends AbstractResumatorTest {

    private EmployeeResource employeeResource;

    @Before
    public void setup(){
        employeeResource = new EmployeeResource();
    }

    @Test
    public void canStoreOneEmployee() throws Exception {
        final Representation representation = employeeResource.storeEmployee();

        final String id = representation.getProperties().get("uid").toString();
        assertNotNull("Employees must be given an ID upon storage", id);
    }
}