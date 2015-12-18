package io.sytac.resumator.employee;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Serialize Employee ids into JSON
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class EmployeeIdSerializer extends JsonSerializer<EmployeeId> {

    @Override
    public void serialize(EmployeeId value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(value.toString());
    }

    @Override
    public Class<EmployeeId> handledType() {
        return EmployeeId.class;
    }
}
