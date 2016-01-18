-- Shamelessly taken from https://github.com/resumator-event-sourcing/rill

CREATE SEQUENCE resumator_events_insert_order_seq;

CREATE TABLE resumator_events (
       event_id VARCHAR(200) UNIQUE NOT NULL,
       insert_order BIGINT UNIQUE DEFAULT nextval('resumator_events_insert_order_seq'),
       payload TEXT NOT NULL,
       created_at TIMESTAMP NOT NULL,
       event_type VARCHAR(512) NOT NULL
);

CREATE INDEX event_type_index ON resumator_events (event_type);

ALTER SEQUENCE resumator_events_insert_order_seq OWNED BY resumator_events.insert_order;