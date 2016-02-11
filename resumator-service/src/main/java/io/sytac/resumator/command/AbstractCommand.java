package io.sytac.resumator.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.model.Event;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

/**
 * Describes the intent to create an abstract command
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public abstract class AbstractCommand implements Command<CommandHeader, CommandPayload> {

    private final CommandHeader header;
    private final CommandPayload payload;

    public AbstractCommand(final CommandHeader header, final CommandPayload payload) {
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

    protected Event createEvent(final String eventId, final ObjectMapper objectMapper) {
        try {
            final String asJson = objectMapper.writeValueAsString(this);
            final Long insertOrder = Optional.ofNullable(getHeader().getInsertOrder()).orElse(Event.ORDER_UNSET);
            final Long timestamp = Optional.ofNullable(getHeader().getTimestamp()).orElse(new Date().getTime());
            return new Event(eventId, insertOrder, asJson, new Timestamp(timestamp), getType());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
