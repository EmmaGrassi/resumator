package io.sytac.resumator.command;

import java.util.Date;
import java.util.Optional;

/**
 * Holds the metadata of a command
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class CommandHeader {
    protected final String id;
    private final String domain;
    private final Long timestamp;
    private final Long insertOrder;

    public CommandHeader() {
        this(null, null, new Date().getTime(), null);
    }

    private CommandHeader(final String id, final String domain, final Long timestamp, final Long insertOrder) {
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
        protected String id = null;
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
