package io.sytac.resumator.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.Configuration;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.employee.NewEmployeeEvent;
import io.sytac.resumator.model.Event;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

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
    public void canReceiveEvents() {
        Consumer consumer = mock(Consumer.class);
        events.subscribe(consumer, NewEmployeeEvent.class);

        Event event = events.publish(new NewEmployeeCommand("org", "name", "surname", "1984", "ITALIAN", "Amsterdam", Long.toString(new Date().getTime())));
        assertEquals("Wrong event details", Event.ORDER_UNSET, event.getInsertOrder());

        verify(consumer, times(1)).accept(event);
    }
}