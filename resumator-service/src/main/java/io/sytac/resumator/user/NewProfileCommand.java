package io.sytac.resumator.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.command.AbstractCommand;
import io.sytac.resumator.command.CommandHeader;
import io.sytac.resumator.model.Event;

import java.util.Optional;
import java.util.UUID;

/**
 * Describes the intent to create a new Employee
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewProfileCommand extends AbstractCommand {

    public static final String EVENT_TYPE = "newProfile";

    @SuppressWarnings("unused")
    @JsonCreator
    public NewProfileCommand(@JsonProperty("header") final CommandHeader header,
                             @JsonProperty("payload") final ProfileCommandPayload payload) {
        super(header, payload);
    }

    @Override
    public String getType() {
        return EVENT_TYPE;
    }

    @Override
    public Event asEvent(final ObjectMapper objectMapper) {
        final String eventId = Optional.ofNullable(getHeader().getId()).orElse(UUID.randomUUID().toString());
        return createEvent(eventId, objectMapper);
    }
}
