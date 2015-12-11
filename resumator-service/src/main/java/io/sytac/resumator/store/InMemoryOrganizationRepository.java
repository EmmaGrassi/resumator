package io.sytac.resumator.store;

import io.sytac.resumator.model.Organization;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Retrieves and stores organizations in memory
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class InMemoryOrganizationRepository implements OrganizationRepository {

    private final ConcurrentHashMap<String, Organization> organizations = new ConcurrentHashMap<>();

    @Override
    public Optional<Organization> get(String id) {
        return Optional.ofNullable(organizations.get(id));
    }

    @Override
    public Optional<Organization> fromDomain(String domain) {
        return organizations.values().stream().filter(org -> org.getDomain().equals(domain)).findFirst();
    }
}
