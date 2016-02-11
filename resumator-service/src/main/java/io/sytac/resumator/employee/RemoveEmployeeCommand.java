package io.sytac.resumator.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.command.AbstractCommand;
import io.sytac.resumator.command.CommandHeader;
import io.sytac.resumator.model.Event;

import java.util.UUID;

/**
 * Describes the intent to remove an Employee
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoveEmployeeCommand extends AbstractCommand {

    public static final String EVENT_TYPE = "removeEmployee";

    @SuppressWarnings("unused")
    @JsonCreator
    public RemoveEmployeeCommand(@JsonProperty("header") final CommandHeader header,
                                 @JsonProperty("payload") final RemoveEmployeeCommandPayload payload) {
        super(header, payload);
    }

    @Override
    public String getType() {
        return EVENT_TYPE;
    }

    @Override
    public Event asEvent(final ObjectMapper objectMapper) {
        return createEvent(UUID.randomUUID().toString(), objectMapper);
    }
}
