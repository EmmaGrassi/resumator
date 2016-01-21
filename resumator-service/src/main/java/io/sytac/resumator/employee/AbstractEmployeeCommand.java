package io.sytac.resumator.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.command.Command;
import io.sytac.resumator.command.CommandHeader;
import io.sytac.resumator.command.CommandPayload;
import io.sytac.resumator.model.Event;

import java.sql.Timestamp;

/**
 * Describes the intent to create a new Employee
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public abstract class AbstractEmployeeCommand implements Command<CommandHeader, CommandPayload> {

    private final CommandHeader header;
    private final CommandPayload payload;

    public AbstractEmployeeCommand(final CommandHeader header, final CommandPayload payload) {
        this.payload = payload;
        this.header = header;
    }

    @Override
    public CommandHeader getHeader() {
        return header;
    }

    @Override
    public CommandPayload getPayload() {
        return payload;
    }

    public abstract Event asEvent(final ObjectMapper json);

    protected Event createEvent(final String eventId, final ObjectMapper json) {
        try {
            final String asJson = json.writeValueAsString(this);
            final Long insertOrder = getHeader().getInsertOrder().orElse(Event.ORDER_UNSET);
            return new Event(eventId, insertOrder, asJson, new Timestamp(getHeader().getTimestamp()), getType());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
