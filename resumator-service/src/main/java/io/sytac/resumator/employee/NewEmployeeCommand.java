package io.sytac.resumator.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.command.CommandHeader;
import io.sytac.resumator.model.Event;

import java.util.UUID;

/**
 * Describes the intent to create a new Employee
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewEmployeeCommand extends AbstractEmployeeCommand {

    public static final String EVENT_TYPE = "newEmployee";

    @SuppressWarnings("unused")
    @JsonCreator
    public NewEmployeeCommand(@JsonProperty("header") final CommandHeader header,
                              @JsonProperty("payload") final EmployeeCommandPayload payload) {
        super(header, payload);
    }

    @Override
    public String getType() {
        return EVENT_TYPE;
    }

    @Override
    public Event asEvent(final ObjectMapper objectMapper) {
        final String eventId = getHeader().getId().orElse(UUID.randomUUID().toString());
        return createEvent(eventId, objectMapper);
    }
}
