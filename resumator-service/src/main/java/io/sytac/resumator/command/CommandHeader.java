package io.sytac.resumator.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * Holds the metadata of a command
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Getter
@Setter
@Builder
public class CommandHeader {
    private final String id;
    private final String domain;
    private final Long timestamp;
    private final Long insertOrder;


    @JsonCreator
    public CommandHeader(@JsonProperty("id") final String id,
                         @JsonProperty("domain") final String domain,
                         @JsonProperty("timestamp") final Long timestamp,
                         @JsonProperty("insertOrder") final Long insertOrder) {
        this.id = id;
        this.domain = domain;
        this.timestamp = timestamp;
        this.insertOrder = insertOrder;
    }
}
