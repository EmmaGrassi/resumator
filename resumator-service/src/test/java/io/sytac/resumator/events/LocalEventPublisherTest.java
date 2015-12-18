package io.sytac.resumator.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.Configuration;
import io.sytac.resumator.command.Command;
import io.sytac.resumator.command.CommandHeader;
import io.sytac.resumator.command.CommandPayload;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.employee.NewEmployeeEvent;
import io.sytac.resumator.model.Event;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

public class LocalEventPublisherTest {

    private LocalEventPublisher events;

    @Before
    public void setUp() throws Exception {
        events = new LocalEventPublisher(new Configuration(), new ObjectMapper());
    }

    @Test
    public void canPublishEvents() {
        Event event = events.publish(new NewEmployeeCommand("org", "name", "surname", "1984", "ITALIAN", "Amsterdam", Long.toString(new Date().getTime())));
        assertEquals("Wrong event details", Event.ORDER_UNSET, event.getInsertOrder());
    }

    @Test
    public void canReceiveSpecificEvents() {
        AtomicInteger invoked = new AtomicInteger(0);
        final Consumer<NewEmployeeEvent> consumer = newEmployeeEvent -> invoked.incrementAndGet();

        events.subscribe(consumer, NewEmployeeEvent.class);

        final NewEmployeeCommand command = new NewEmployeeCommand("org", "name", "surname", "1984", "ITALIAN", "Amsterdam", Long.toString(new Date().getTime()));
        Event event = events.publish(command);
        assertEquals("Wrong event details", Event.ORDER_UNSET, event.getInsertOrder());
        assertEquals("Not invoked just one time!", 1, invoked.get());
        events.publish(command);
        assertEquals("Not invoked just one time!", 2, invoked.get());

        events.publish(new BogusCommand());
        assertEquals("Received undesirable events types!", 2, invoked.get());
    }

    private class BogusCommand implements Command {

        @Override
        public CommandHeader getHeader() {
            return null;
        }

        @Override
        public CommandPayload getPayload() {
            return null;
        }

        @Override
        public String getType() {
            return null;
        }

        @Override
        public Event asEvent() {
            return new Event("id", "stream", Event.ORDER_UNSET, Event.ORDER_UNSET, null, null, "bogus");
        }
    }
}