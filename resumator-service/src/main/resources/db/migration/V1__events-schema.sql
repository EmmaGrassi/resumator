-- Shamelessly taken from https://github.com/resumator-event-sourcing/rill

CREATE SEQUENCE resumator_events_insert_order_seq;

CREATE TABLE resumator_events (
       event_id VARCHAR(200) UNIQUE NOT NULL,
       stream_id VARCHAR(512) NOT NULL,
       insert_order BIGINT UNIQUE,
       stream_order BIGINT NOT NULL,
       payload TEXT NOT NULL,
       created_at TIMESTAMP NOT NULL,
       event_type VARCHAR(512) NOT NULL,
       UNIQUE(stream_id, stream_order)
);

CREATE INDEX stream_id_index ON resumator_events (stream_id);
CREATE INDEX event_type_index ON resumator_events (event_type);

ALTER SEQUENCE resumator_events_insert_order_seq OWNED BY resumator_events.insert_order;

CREATE FUNCTION resumator_set_insert_order() RETURNS trigger AS $$
       DECLARE
        event RECORD;
       BEGIN
          -- ensure that insert order is generated in order of *visibility*
          PERFORM pg_advisory_xact_lock(3333, 'resumator_events'::regclass::oid::integer);
          FOR event IN SELECT stream_id, stream_order FROM resumator_events WHERE insert_order IS NULL ORDER BY stream_id ASC, stream_order ASC LOOP
            UPDATE resumator_events SET insert_order = nextval('resumator_events_insert_order_seq') WHERE stream_id = event.stream_id AND stream_order = event.stream_order;
          END LOOP;
          RETURN NULL;
       END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER resumator_set_insert_order AFTER INSERT ON resumator_events
       FOR EACH STATEMENT EXECUTE PROCEDURE resumator_set_insert_order();