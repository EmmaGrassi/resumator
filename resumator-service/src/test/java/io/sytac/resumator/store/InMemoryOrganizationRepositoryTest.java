package io.sytac.resumator.store;

import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.organization.NewOrganizationCommand;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.InMemoryOrganizationRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Organizations must be available in memory
 */
public class InMemoryOrganizationRepositoryTest {

    private InMemoryOrganizationRepository repository;
    private CommandFactory commands;

    @Before
    public void setup() {
        repository = new InMemoryOrganizationRepository();
        commands = new CommandFactory(mock(EventPublisher.class));
    }

    @Test
    public void canRegisterNewOrganizations() throws Exception {
        final NewOrganizationCommand command = newOrgCommand("name", "domain");
        final Organization stored = repository.register(command);
        assertEquals("The stored organization doesn't match the input one!", "name", stored.getName());
    }

    @Test
    public void canOverrideOrganizations() {
        final NewOrganizationCommand one = newOrgCommand("one", "domain");
        final NewOrganizationCommand two = newOrgCommand("two", "domain");

        repository.register(one);
        final Organization overridden = repository.register(two);
        assertEquals("When overriding an organization, the older one was not returned", "one", overridden.getName());
    }

    @Test
    public void canGetEmptyResults() throws Exception {
        assertFalse("Empty repositories should not yield any result", repository.get("whatever").isPresent());
    }

    @Test
    public void canRetrieveAnExistingOrganization() {
        final NewOrganizationCommand command = newOrgCommand("name", "domain.io");
        final Organization org = repository.register(command);

        assertEquals("Couldn't retrieve the stored organization", org, repository.get(org.getId()).get());
    }

    @Test
    public void canSearchByDomain() throws Exception {
        final NewOrganizationCommand command = newOrgCommand("name", "domain.io");
        final Organization org = repository.register(command);

        assertEquals("Couldn't search by domain", org, repository.fromDomain(org.getDomain()).get());
    }

    private NewOrganizationCommand newOrgCommand(final String name, final String domain) {
        final Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("domain", domain);

        return commands.newOrganizationCommand(params);
    }
}