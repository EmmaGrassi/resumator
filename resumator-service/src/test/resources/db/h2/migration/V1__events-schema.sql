-- Shamelessly taken from https://github.com/resumator-event-sourcing/rill

CREATE SEQUENCE resumator_events_insert_order_seq;

CREATE TABLE resumator_events (
       event_id VARCHAR(200) UNIQUE NOT NULL,
       insert_order BIGINT DEFAULT resumator_events_insert_order_seq.nextval,
       payload CLOB NOT NULL,
       created_at TIMESTAMP NOT NULL,
       event_type VARCHAR(512) NOT NULL
);

CREATE INDEX event_type_index ON resumator_events (event_type);