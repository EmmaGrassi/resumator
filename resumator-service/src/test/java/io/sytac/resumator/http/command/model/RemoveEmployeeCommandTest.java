package io.sytac.resumator.http.command.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.sytac.resumator.command.CommandHeader;
import io.sytac.resumator.employee.RemoveEmployeeCommand;
import io.sytac.resumator.employee.RemoveEmployeeCommandPayload;
import io.sytac.resumator.model.Event;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class RemoveEmployeeCommandTest extends CommonCommandTest {

    public static final String REMOVE_EMPLOYEE_TYPE = "removeEmployee";

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void canSerializeAsJSON(){
        canSerializeAsJSON(RemoveEmployeeCommand.class);
    }

    @Test
    public void JSONisPredictable() throws JsonProcessingException {
        final RemoveEmployeeCommand removeEmployeeCommand = getRemoveEmployeeCommand();
        final String json = getJson(String.valueOf(removeEmployeeCommand.getHeader().getTimestamp()));

        assertEquals("Json is different than expected", json, getObjectMapper().writeValueAsString(removeEmployeeCommand));
    }

    @Test
    public void canRestoreFromString() throws IOException {
        final byte[] expectedBytes = getJson(String.valueOf(new Date().getTime())).getBytes("UTF-8");
        final RemoveEmployeeCommand command = getObjectMapper().readValue(expectedBytes, RemoveEmployeeCommand.class);
        assertEquals("Wrong deserialization", DOMAIN, command.getHeader().getDomain());
    }

    @Test
    public void canCreateEvent() {
        final RemoveEmployeeCommand removeEmployeeCommand = getRemoveEmployeeCommand();
        final String json = getJson(String.valueOf(removeEmployeeCommand.getHeader().getTimestamp()));
        final Event event = removeEmployeeCommand.asEvent(getObjectMapper());

        assertNotNull(event);
        assertEquals(REMOVE_EMPLOYEE_TYPE, event.getType());
        assertEquals(json, event.getPayload());
    }

    private RemoveEmployeeCommand getRemoveEmployeeCommand() {
        final RemoveEmployeeCommandPayload payload = new RemoveEmployeeCommandPayload(UUID);
        final CommandHeader commandHeader = createCommandHeader(UUID, DOMAIN, new Date().getTime());
        return new RemoveEmployeeCommand(commandHeader, payload);
    }

    private String getJson(final String headerTimestamp) {
        return "{" +
            "\"header\":{" +
                "\"id\":\"" + UUID + "\"," +
                "\"domain\":\"" + DOMAIN + "\"," +
                "\"timestamp\":" + headerTimestamp +
            "}," +
            "\"payload\":{" +
                "\"employeeId\":\"" + UUID + "\"" +
            "}," +
            "\"type\":\"" + REMOVE_EMPLOYEE_TYPE + "\"" +
        "}";
    }
}