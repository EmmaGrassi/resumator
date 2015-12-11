package io.sytac.resumator.http.command.model;

import javax.ws.rs.core.MultivaluedMap;
import java.util.Date;
import java.util.Optional;

/**
 * Describes the intent to create a new Employee
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class NewEmployeeCommand implements Command<CommandHeader, NewEmployeeCommandPayload> {

    private final CommandHeader header;
    private final NewEmployeeCommandPayload payload;

    /* package */  NewEmployeeCommand(final MultivaluedMap<String, String> formParams, final String organizationId) {
        payload = new NewEmployeeCommandPayload(organizationId,
                                                formParams.getFirst("name"),
                                                formParams.getFirst("surname"),
                                                formParams.getFirst("yearOfBirth"),
                                                formParams.getFirst("nationality"),
                                                formParams.getFirst("currentResidence"));
        final Optional<String> timestamp = Optional.ofNullable(formParams.getFirst("timestamp"));
        final Date time = timestamp.map(Long::decode)
                                   .map(Date::new)
                                   .orElse(new Date());

        header = new CommandHeader(time);
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
