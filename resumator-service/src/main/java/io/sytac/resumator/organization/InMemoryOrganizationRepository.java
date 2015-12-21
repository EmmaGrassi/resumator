package io.sytac.resumator.organization;

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
        final Optional<Organization> stored = Optional.ofNullable(organizations.putIfAbsent(org.getDomain(), org));
        if (stored.isPresent()) {
            LOGGER.warn("Replacing existing organization: {}", stored);
            return stored.get();
        }

        return org;
    }
}
