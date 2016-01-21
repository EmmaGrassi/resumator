package io.sytac.resumator.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.Configuration;
import io.sytac.resumator.command.Command;
import io.sytac.resumator.command.CommandHeader;
import io.sytac.resumator.command.CommandPayload;
import io.sytac.resumator.employee.EmployeeCommandPayload;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.model.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.*;
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
        Event event = events.publish(getEmployeeCommand());
        assertEquals("Wrong event details", Event.ORDER_UNSET, event.getInsertOrder());
    }

    @Test
    public void canReceiveSpecificEvents() {
        AtomicInteger invoked = new AtomicInteger(0);
        final Consumer<Event> consumer = newEmployeeEvent -> invoked.incrementAndGet();
        final NewEmployeeCommand command = getEmployeeCommand();

        events.subscribe(consumer, NewEmployeeCommand.EVENT_TYPE);
        Event event = events.publish(command);
        assertEquals("Wrong event details", Event.ORDER_UNSET, event.getInsertOrder());
        assertEquals("Not invoked just one time!", 1, invoked.get());
        events.publish(command);
        assertEquals("Not invoked just one time!", 2, invoked.get());

        events.publish(new BogusCommand());
        assertEquals("Received undesirable events types!", 2, invoked.get());
    }
    private NewEmployeeCommand getEmployeeCommand() {
        final List<Education> education = Collections.singletonList(new Education(Education.Degree.MASTER_DEGREE, "Field", "SChool", "City", "Country", 2000, 2005));
        final List<Course> courses = Collections.singletonList(new Course("Course1", "Course 1", 1994));
        final List<String> technologies = Arrays.asList("Java", "Turbo Pascal");
        final List<String> methodologies = Arrays.asList("Scrum", "Exreme programming");

        Date startDate = new GregorianCalendar(2010, Calendar.JANUARY, 1).getTime();
        Date endDate = new GregorianCalendar(2014, Calendar.JANUARY, 1).getTime();
        final List<Experience> experience  = Collections.singletonList(new Experience("CompanyName", "Title", "City", "Coutry", "Short Description",
                technologies, methodologies, startDate, endDate));

        final List<Language> languages = Collections.singletonList(new Language("English", Language.Proficiency.FULL_PROFESSIONAL));
        final EmployeeCommandPayload payload = new EmployeeCommandPayload("Title", "Foo", "Bar", "Email", "+31000999000",
                "Github", "Linkedin", "1984-04-22", "ITALY", "N", "About ME", education, courses, experience, languages);

        final CommandHeader commandHeader = new CommandHeader.Builder()
                .setId(UUID.randomUUID().toString())
                .setDomain("acme.biz")
                .setTimestamp(new Date().getTime())
                .build();
        return new NewEmployeeCommand(commandHeader, payload);
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
        public Event asEvent(ObjectMapper json) {
            return new Event("id", Event.ORDER_UNSET, "bogus", new Timestamp(1L), "bogus");
        }
    }
}