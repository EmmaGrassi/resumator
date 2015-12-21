package io.sytac.resumator.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import io.sytac.resumator.employee.Employee;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.model.Event;
import io.sytac.resumator.organization.InMemoryOrganizationRepository;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class BootstrapTest {

    private Bootstrap bootstrap;
    private EventStore store;
    private ObjectMapper json;
    private OrganizationRepository orgs;

    @Before
    public void setUp() throws Exception {
        json = new ObjectMapper();
        json.registerModule(new Jdk8Module());

        store = mock(EventStore.class);
        when(store.getAll()).thenReturn(Collections.emptyList());

        orgs = new InMemoryOrganizationRepository();
        final Organization acme = new Organization("acme", "ACME inc.", "acme.biz");
        orgs.register(acme);

        bootstrap = new Bootstrap(store, orgs, json);
    }

    @Test
    public void canReplayZeroEvents() {
        bootstrap.start(result -> assertTrue("The replay of 0 events failed", result.failures.isEmpty()));
        verify(store, times(1)).setReadOnly(false);
        verify(store, times(1)).setReadOnly(true);
    }

    @Test
    public void canReplayNewEmployeeEvent() {
        when(store.getAll()).thenReturn(Collections.singletonList(newEmployeeEvent()));
        bootstrap.start(result -> assertTrue("The replay of creating one employee failed", result.successfullyReplayed.get() == 1));
        verify(store, times(1)).setReadOnly(false);
        verify(store, times(1)).setReadOnly(true);
        Optional<Employee> employee = orgs.get("acme").get().findEmployeeByName("name", "surname");
        assertTrue("The employee was not stored in the organization after replaying the creation event", employee.isPresent());
    }

    private Event newEmployeeEvent() {
        final NewEmployeeCommand command = new NewEmployeeCommand("acme", "name", "surname", "1984", "ITALIAN", "Amsterdam", Long.toString(new Date().getTime()));
        return command.asEvent(json);
    }

}