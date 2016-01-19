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
 * Describes the intent to remove an Employee
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoveEmployeeCommand implements Command<CommandHeader, RemoveEmployeeCommandPayload> {

    public static final String EVENT_TYPE = "removeEmployee";
    private final CommandHeader header;
    private final RemoveEmployeeCommandPayload payload;

    public RemoveEmployeeCommand(final RemoveEmployeeCommandPayload payload, final String domain, final String timestamp) {
        final Date date = Optional.ofNullable(timestamp)
                .map(Long::decode)
                .map(Date::new)
                .orElse(new Date());

        this.header = new CommandHeader.Builder().setDomain(domain).setTimestamp(date.getTime()).build();
        this.payload = payload;
    }

    public RemoveEmployeeCommand(final RemoveEmployeeCommandPayload payload, final CommandHeader header) {
        this.header = header;
        this.payload = payload;
    }

    @SuppressWarnings("unused")
    @JsonCreator
    public RemoveEmployeeCommand(@JsonProperty("header") final CommandHeader header,
                                 @JsonProperty("payload") final RemoveEmployeeCommandPayload payload) {
        this.header = header;
        this.payload = payload;
    }

    @Override
    public CommandHeader getHeader() {
        return header;
    }

    @Override
    public RemoveEmployeeCommandPayload getPayload() {
        return payload;
    }

    @Override
    public String getType() {
        return EVENT_TYPE;
    }

    @Override
    public Event asEvent(final ObjectMapper json) {
        try {
            final String asJson = json.writeValueAsString(this);
            final Long insertOrder = header.getInsertOrder().orElse(Event.ORDER_UNSET);
            return new Event(UUID.randomUUID().toString(), insertOrder, asJson, new Timestamp(header.getTimestamp()), getType());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
