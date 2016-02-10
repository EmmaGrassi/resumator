package io.sytac.resumator.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.command.CommandHeader;
import io.sytac.resumator.employee.AbstractEmployeeCommand;
import io.sytac.resumator.employee.RemoveEmployeeCommandPayload;
import io.sytac.resumator.model.Event;

import java.util.UUID;

/**
 * Describes the intent to remove an Profile
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoveProfileCommand extends AbstractEmployeeCommand {

    public static final String EVENT_TYPE = "removeProfile";

    @SuppressWarnings("unused")
    @JsonCreator
    public RemoveProfileCommand(@JsonProperty("header") final CommandHeader header,
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
