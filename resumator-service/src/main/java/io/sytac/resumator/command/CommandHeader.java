package io.sytac.resumator.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * Holds the metadata of a command
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
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

    public Optional<String> getId() {
        return Optional.ofNullable(id);
    }

    public Optional<String> getDomain() {
        return Optional.ofNullable(domain);
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Optional<Long> getInsertOrder() {
        return Optional.ofNullable(insertOrder);
    }

    public static class Builder {
        private String id = UUID.randomUUID().toString();
        private String domain = null;
        private Long timestamp = new Date().getTime();
        private Long insertOrder = null;

        public Builder setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setDomain(String domain) {
            this.domain = domain;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setInsertOrder(Long insertOrder) {
            this.insertOrder = insertOrder;
            return this;
        }

        public CommandHeader build() {
            return new CommandHeader(id, domain, timestamp, insertOrder);
        }
    }
}
