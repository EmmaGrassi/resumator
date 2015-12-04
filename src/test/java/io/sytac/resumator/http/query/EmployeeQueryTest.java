package io.sytac.resumator.http.query;

import com.theoryinpractise.halbuilder.api.ContentRepresentation;
import io.sytac.resumator.AbstractResumatorTest;
import io.sytac.resumator.http.RESTTest;
import io.sytac.resumator.model.Employee;
import io.sytac.resumator.model.EmployeeId;
import io.sytac.resumator.model.enums.Nationality;
import io.sytac.resumator.store.EmployeeRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test retrieval of one employee
 */
public class EmployeeQueryTest extends RESTTest {

    private EmployeeRepository repository;
    private List<EmployeeId> existingEmployees = Arrays.asList(new EmployeeId(), new EmployeeId());

    public EmployeeRepository getRepository() {
        repository = repository == null ? mock(EmployeeRepository.class) : repository;
        return repository;
    }

    protected void storeMockData() {
        existingEmployees.stream()
                .map(id -> {
                    when(repository.find(eq(id)))
                                   .thenReturn(Optional.of(new Employee(id, "Foo", "Bar", 1970, Nationality.AFGHAN, "Wonderland")));
                    return true;
                }).reduce((x, y) -> true);
    }

    @Override
    public Application configure() {
        final ResourceConfig application = (ResourceConfig) super.configure();
        return application.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(getRepository()).to(EmployeeRepository.class);
            }
        });
    }

    @Test
    public void canRetrieveOneEmployee() {
        storeMockData();
        final EmployeeId id = existingEmployees.get(0);
        assertNotNull(repository.find(id));

        final WebTarget target = target("employee/" + id.toString()).register(EmployeeMessageBodyReader.class);
        final Employee response = target.request().buildGet().invoke(Employee.class);
        assertEquals("Existing employee was not found", id.toString(), response.getId().toString());
    }

}