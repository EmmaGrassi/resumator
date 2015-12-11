package io.sytac.resumator.store;

import io.sytac.resumator.model.Organization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Retrieves and stores organizations in memory
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class InMemoryOrganizationRepository implements OrganizationRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryOrganizationRepository.class);

    private final ConcurrentHashMap<String, Organization> organizations = new ConcurrentHashMap<>();

    @Override
    public Optional<Organization> get(final String id) {
        return Optional.ofNullable(organizations.get(id));
    }

    @Override
    public Optional<Organization> fromDomain(final String domain) {
        return organizations.values().stream().filter(org -> org.getDomain().equals(domain)).findFirst();
    }

    @Override
    public Organization register(final Organization org) {
        Optional.ofNullable(organizations.putIfAbsent(org.getId(), org))
                .ifPresent(old -> LOGGER.warn("Replacing existing organization: {}", old));
        return org;
    }
}
