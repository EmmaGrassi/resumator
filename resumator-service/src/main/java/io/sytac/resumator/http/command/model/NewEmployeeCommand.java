package io.sytac.resumator.http.command.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Optional;

/**
 * Describes the intent to create a new Employee
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewEmployeeCommand implements Command<CommandHeader, NewEmployeeCommandPayload> {

    private final CommandHeader header;
    private final NewEmployeeCommandPayload payload;

    /* package */  NewEmployeeCommand(final String organizationId,
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
}
