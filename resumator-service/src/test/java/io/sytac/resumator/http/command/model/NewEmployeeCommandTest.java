package io.sytac.resumator.http.command.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.employee.NewEmployeeCommandDeserializer;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NewEmployeeCommandTest {

    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        final SimpleModule module = new SimpleModule();
        module.addDeserializer(NewEmployeeCommand.class, new NewEmployeeCommandDeserializer());
        mapper.registerModule(module);
    }

    @Test
    public void canSerializeAsJSON(){
        assertTrue("Commands must be serializable as they end up in the DB",
                mapper.canSerialize(NewEmployeeCommand.class));
    }

    @Test
    public void JSONisPredictable() throws JsonProcessingException {
        final MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.putSingle("name", "Foo");
        params.putSingle("surname", "Bar");
        params.putSingle("yearOfBirth", "1984");
        params.putSingle("nationality", "ITALY");
        final NewEmployeeCommand foobar = new NewEmployeeCommand("ACME", "Foo", "Bar", "1984", "ITALY", null, null);
        final Date timestamp = foobar.getHeader().getTimestamp();
        final String expected =
                "{\"header\":" +
                        "{\"timestamp\":" + timestamp.getTime() + "}," +
                 "\"payload\":" +
                        "{\"organizationId\":\"ACME\"," +
                        "\"name\":\"Foo\"," +
                        "\"surname\":\"Bar\"," +
                        "\"yearOfBirth\":\"1984\"," +
                        "\"nationality\":\"ITALY\"}," +
                        "\"type\":\"newEmployee\"}";
        assertEquals("JSon is different than expected",
                expected,
                mapper.writeValueAsString(foobar));
    }

    @Test
    public void canRestoreFromString() throws IOException {
        final String expected =
                "{\"header\":" +
                        "{\"timestamp\":" + new Date().getTime() + "}," +
                        "\"payload\":" +
                        "{\"organizationId\":\"foobar\"," +
                        "\"name\":\"Foo\"," +
                        "\"surname\":\"Bar\"," +
                        "\"yearOfBirth\":\"1984\"," +
                        "\"nationality\":\"ITALY\"}," +
                        "\"type\":\"newEmployee\"}";
        final NewEmployeeCommand command = mapper.readValue(expected.getBytes("UTF-8"), NewEmployeeCommand.class);
        assertEquals("Wrong deserialization", "foobar", command.getPayload().getOrganizationId());
    }
}