package io.sytac.resumator.organization;

import java.util.Optional;

/**
 * Represent an organization or community of people (e.g. a company)
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public interface OrganizationRepository {

    /**
     * Retrieve an {@link Organization} by id
     *
     * @param id The id of the organization to retrieve
     * @return The found organization, or an empty value
     */
    Optional<Organization> get(String id);

    /**
     * Retrieve an {@link Organization} by its domain
     *
     * @param domain The domain linked to the organization (e.g. "sytac.io")
     * @return The found organization, or an empty value
     */
    Optional<Organization> fromDomain(String domain);

    /**
     * Registers a new {@link Organization}
     *
     * @param org The organization to register
     * @return The registered organization
     */
    Organization register(Organization org);
}
