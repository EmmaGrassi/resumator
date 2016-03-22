package io.sytac.resumator.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.command.CommandHeader;
import io.sytac.resumator.employee.Employee;
import io.sytac.resumator.employee.EmployeeCommandPayload;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.model.Event;
import io.sytac.resumator.organization.InMemoryOrganizationRepository;
import io.sytac.resumator.organization.NewOrganizationCommand;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.store.bootstrap.Bootstrap;
import io.sytac.resumator.store.bootstrap.Migrator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class BootstrapTest {

    private static final String JSON_EMPLOYEE_STR_OLD_FORMAT = "{\"aboutMe\":\"ewqewq\",\"education\":[],\"experience\":[{\"shortDescription\":\"ewqewq\",\"methodologies\":\"ewqewq\"," +
                 "\"companyName\":\"dsads\",\"endDate\":\"2011-01-01\",\"technologies\":\"ewqe\",\"city\":\"dsadsa\","+
                 "\"startDate\":\"1999-01-01\",\"country\":\"dsadsa\",\"title\":\"dsadsa\"},{\"shortDescription\":\"ewqewq\","+
                 "\"methodologies\":\"ewqew\",\"companyName\":\"ewqewq\",\"endDate\":\"2011-01-01\",\"technologies\":\"ewqe\","+
                 "\"city\":\"ewqeqw\",\"startDate\":\"1998-01-01\",\"country\":\"ewqewq\",\"title\" :\"ewqewq\"}],\"name\":\"selman tayyar\","+
                 "\"currentResidence\" :\"dsad\",\"admin\":true,\"phonenumber\":\"+31654365653\",\"title\":\"selman dev\",\"courses\":[],"+
                 "\"dateOfBirth\":\"1981-01-01\",\"type\":\"EMPLOYEE\",\"languages\":[],\"surname\":\"ewqewq\",\"nationality\":\"DUTCH\",\"email\":\"selman.tayyar@sytac.io\"}";

    private Migrator migrator;
    private Bootstrap bootstrap;
    private EventStore store;
    private ObjectMapper json;
    private OrganizationRepository orgs;

    @Mock
    private EventPublisher eventPublisherMock;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        json = new ObjectMapper();
        json.registerModule(new Jdk8Module());

        store = mock(EventStore.class);
        when(store.getAll()).thenReturn(Collections.emptyList());

        final Map<String, String> orgDetails = new HashMap<>();
        orgDetails.put("name", "ACME inc.");
        orgDetails.put("domain", "acme.biz");

        when(eventPublisherMock.publish(any(NewOrganizationCommand.class))).thenReturn(mock(Event.class));
        orgs = new InMemoryOrganizationRepository(eventPublisherMock);
        orgs.register(new CommandFactory().newOrganizationCommand(orgDetails));

        bootstrap = new Bootstrap(store, orgs, json);
        migrator=new Migrator(store);
    }

    @Test
    public void canReplayZeroEvents() {
        bootstrap.replay();
        verify(store, times(1)).setReadOnly(false);
        verify(store, times(1)).setReadOnly(true);
    }

    @Test
    public void canReplayNewEmployeeEvent() {
        when(store.getAll()).thenReturn(Collections.singletonList(newEmployeeEvent()));
        bootstrap.replay();
        verify(store, times(1)).setReadOnly(false);
        verify(store, times(1)).setReadOnly(true);
        Optional<Employee> employee = orgs.fromDomain("acme.biz").get().findEmployeeByName("name", "surname");
        assertTrue("The employee was not stored in the organization after replaying the creation event", employee.isPresent());
    }

    private Event newEmployeeEvent() {
        final EmployeeCommandPayload employeeCommandPayload = new EmployeeCommandPayload(null, "title", "name", "surname", "email",
                "phonenumber", "github", "linkedin", "1984-04-22", "ITALIAN","Netherlands","Amsterdam", "", "", null, null, null, null, false);
        final CommandHeader commandHeader = new CommandHeader.Builder()
                .setId(UUID.randomUUID().toString())
                .setDomain("acme.biz")
                .build();
        final NewEmployeeCommand command = new NewEmployeeCommand(commandHeader, employeeCommandPayload);
        return command.asEvent(json);
    }

    @Test
    public void canSkipMigrateEvent() {
        List<Event> events = Collections.singletonList(newEmployeeEvent());
        when(store.getAll()).thenReturn(events);
        migrator.migrate();
        verify(store, times(0)).setReadOnly(true);
        verify(store, times(0)).post(events.get(0));
    }

    @Test
    public void canMigrateEvent() {
        Date now=new Date();
        Timestamp created= new Timestamp(now.getTime());
        Event event=new Event("32132", 1l, JSON_EMPLOYEE_STR_OLD_FORMAT, created, "newEmployee");
        List<Event> events = new ArrayList<>();
        events.add(event);
        when(store.getAll()).thenReturn(events);
        migrator.migrate();
        verify(store, times(0)).setReadOnly(true);
        verify(store, times(1)).post(Mockito.any(Event.class));
    }

}
