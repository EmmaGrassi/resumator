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
public abstract class AbstractEmployeeCommand implements Command<CommandHeader, EmployeeCommandPayload> {

    private final CommandHeader header;
    private final EmployeeCommandPayload payload;


    /*@SuppressWarnings("unused")
    @JsonCreator
    public AbstractEmployeeCommand(@JsonProperty("header") final CommandHeader header,
                                   @JsonProperty("payload") final EmployeeCommandPayload payload) {
        this.payload = payload;
        this.header = header;
    }*/

    public AbstractEmployeeCommand(final CommandHeader header, final EmployeeCommandPayload payload) {
        this.payload = payload;
        this.header = header;
    }

    @Override
    public CommandHeader getHeader() {
        return header;
    }

    @Override
    public EmployeeCommandPayload getPayload() {
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
