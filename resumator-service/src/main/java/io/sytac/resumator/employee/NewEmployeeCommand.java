package io.sytac.resumator.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sytac.resumator.command.Command;
import io.sytac.resumator.command.CommandHeader;
import io.sytac.resumator.model.Event;

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
public class NewEmployeeCommand implements Command<CommandHeader, NewEmployeeCommandPayload, NewEmployeeCommand> {

    private final CommandHeader header;
    private final NewEmployeeCommandPayload payload;

    public NewEmployeeCommand(final String organizationId,
                                      final String name,
                                      final String surname,
                                      final String yearOfBirth,
                                      final String nationality,
                                      final String currentResidence,
                                      final String timestamp) {
        payload = new NewEmployeeCommandPayload(organizationId,
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
        return "newEmployee";
    }

    @Override
    public Event<NewEmployeeCommand> asEvent() {
        return new NewEmployeeEvent(UUID.randomUUID().toString(), "streamId", this);
    }
}
