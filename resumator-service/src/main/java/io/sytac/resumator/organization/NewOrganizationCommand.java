package io.sytac.resumator.organization;

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
 * Declares the intent to create a new Organization
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewOrganizationCommand implements Command<CommandHeader, NewOrganizationCommandPayload> {

    public static final String EVENT_TYPE = "newOrganization";

    final CommandHeader header;
    final NewOrganizationCommandPayload payload;

    @SuppressWarnings("unused")
    @JsonCreator
    public NewOrganizationCommand(@JsonProperty("header")  final CommandHeader header,
                                  @JsonProperty("payload") final NewOrganizationCommandPayload payload) {
        this.header = header;
        this.payload = payload;
    }

    public NewOrganizationCommand(final String name, final String domain, final String timestamp) {
        final Date time = Optional.ofNullable(timestamp)
                .map(Long::decode)
                .map(Date::new)
                .orElse(new Date());

        this.header  = CommandHeader.builder().timestamp(time.getTime()).build();
        this.payload = new NewOrganizationCommandPayload(name, domain);
    }

    @Override
    public CommandHeader getHeader() {
        return header;
    }

    @Override
    public NewOrganizationCommandPayload getPayload() {
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

        final String eventId = Optional.ofNullable(header.getId()).orElse(randomId());
        final Long insertOrder = Optional.ofNullable(header.getInsertOrder()).orElse(Event.ORDER_UNSET);
        return new Event(eventId, insertOrder, asJson, new Timestamp(header.getTimestamp()), getType());
    }

    private String randomId() {
        return UUID.randomUUID().toString();
    }
}
