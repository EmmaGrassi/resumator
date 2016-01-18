package io.sytac.resumator.organization;

import io.sytac.resumator.events.EventPublisher;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Retrieves and stores organizations in memory
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class InMemoryOrganizationRepository implements OrganizationRepository {

    private final Map<String, Organization> organizations = new ConcurrentHashMap<>();

    private final EventPublisher events;

    @Inject
    public InMemoryOrganizationRepository(final EventPublisher events) {
        this.events = events;
    }

    @Override
    public Optional<Organization> get(final String id) {
        return organizations.values()
                            .stream()
                            .filter(org -> id.equals(org.getId()))
                            .findFirst();
    }

    @Override
    public Optional<Organization> fromDomain(final String domain) {
        return organizations.values().stream().filter(org -> org.getDomain().equals(domain)).findFirst();
    }

    @Override
    public Organization register(final NewOrganizationCommand command) {
        final Organization org = new Organization(command.getPayload().getName(), command.getPayload().getDomain());
        Organization organization = this.addOrganization(org);
        events.publish(command);
        return organization;
    }

    @Override
    public Organization addOrganization(final Organization org) {
        if (organizations.containsKey(org.getDomain())) {
            throw new IllegalStateException("Cannot add an organization that already exists");
        }

        organizations.put(org.getDomain(), org);
        return org;
    }
}
