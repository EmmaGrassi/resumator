package io.sytac.resumator.employee;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Serialize Employee ids into JSON
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class EmployeeIdSerializer extends JsonSerializer<EmployeeId> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeIdSerializer.class);

    @Override
    public void serialize(EmployeeId value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        LOGGER.info("### " + value + " ###");
        jgen.writeString(value.toString());
    }

    @Override
    public Class<EmployeeId> handledType() {
        return EmployeeId.class;
    }
}
