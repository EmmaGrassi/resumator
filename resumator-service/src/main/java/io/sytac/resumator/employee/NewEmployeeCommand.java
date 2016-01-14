package io.sytac.resumator.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.command.Command;
import io.sytac.resumator.command.CommandHeader;
import io.sytac.resumator.model.Event;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * Describes the intent to create a new Employee
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewEmployeeCommand implements Command<CommandHeader, NewEmployeeCommandPayload> {

    public static final String EVENT_TYPE = "newEmployee";
    private final CommandHeader header;
    private final NewEmployeeCommandPayload payload;

    public NewEmployeeCommand(final String organizationDomain,
                                      final String name,
                                      final String surname,
                                      final String yearOfBirth,
                                      final String nationality,
                                      final String currentResidence,
                                      final String timestamp) {
        payload = new NewEmployeeCommandPayload(organizationDomain,
                                                name,
                                                surname,
                                                yearOfBirth,
                                                nationality,
                                                currentResidence);
        final Date time = Optional.ofNullable(timestamp)
                                    .map(Long::decode)
                                    .map(Date::new)
                                    .orElse(new Date());

        header = new CommandHeader(time);
    }

    @SuppressWarnings("unused")
    @JsonCreator
        /* package */ NewEmployeeCommand(@JsonProperty("header") final CommandHeader header,
                                         @JsonProperty("payload") final NewEmployeeCommandPayload payload) {
        this.header = header;
        this.payload = payload;
    }

    @Override
    public CommandHeader getHeader() {
        return header;
    }

    @Override
    public NewEmployeeCommandPayload getPayload() {
        return payload;
    }

    @Override
    public String getType() {
        return EVENT_TYPE;
    }

    @Override
    public Event asEvent(final ObjectMapper json) {
        final String asJson;
        try {
            asJson = json.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }

        return new Event(header.getId().orElse(randomId()),
                        header.getInsertOrder().orElse(Event.ORDER_UNSET),
                        asJson,
                        new Timestamp(header.getTimestamp().getTime()),
                        getType());
    }

    private String randomId() {
        return UUID.randomUUID().toString();
    }
}
