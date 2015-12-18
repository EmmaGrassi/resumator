package io.sytac.resumator.store;

import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.InMemoryOrganizationRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Organizations must be available in memory
 */
public class InMemoryOrganizationRepositoryTest {

    private InMemoryOrganizationRepository repository;

    @Before
    public void setup() {
        repository = new InMemoryOrganizationRepository();
    }

    @Test
    public void canRegisterNewOrganizations() throws Exception {
        final Organization org = new Organization("id", "name", "domain");
        final Organization stored = repository.register(org);
        assertEquals("The stored organization doesn't match the input one!", org, stored);
    }

    @Test
    public void canOverrideOrganizations() {
        final Organization one = new Organization("id", "one", "one");
        final Organization two = new Organization("id", "two", "two");

        repository.register(one);
        final Organization overridden = repository.register(two);
        assertEquals("When overriding an organization, the older one was not returned", one, overridden);
    }

    @Test
    public void canGetEmptyResults() throws Exception {
        assertFalse("Empty repositories should not yield any result", repository.get("whatever").isPresent());
    }

    @Test
    public void canRetrieveAnExistingOrganization() {
        final Organization org = new Organization("id", "name", "domain.io");
        repository.register(org);

        assertEquals("Couldn't retrieve the stored organization", org, repository.get(org.getId()).get());
    }

    @Test
    public void canSearchByDomain() throws Exception {
        final Organization org = new Organization("id", "name", "domain.io");
        repository.register(org);

        assertEquals("Couldn't search by domain", org, repository.fromDomain(org.getDomain()).get());
    }
}