package io.sytac.resumator.store.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.command.Command;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;

import java.util.Optional;

/**
 * Abstract event executor to replay events
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public abstract class AbstractEventExecutor implements EventExecutor {

    protected final OrganizationRepository repository;
    protected final ObjectMapper objectMapper;

    public AbstractEventExecutor(final ReplayEventConfig config) {
        this.repository = config.getOrganizationRepository();
        this.objectMapper = config.getObjectMapper();
    }

    /**
     * Checks presence of the id and domain in the header of the command
     * @param command the command to check
     */
    protected void checkIdAndDomainInHeader(final Command command) {
        final Optional<String> id = command.getHeader().getId();
        final Optional<String> domain = command.getHeader().getDomain();
        if (!id.isPresent() || !domain.isPresent()) {
            String errorMessage = "Cannot replay '" + command.getType() + "' event without id and/or domain information in the header";
            throw new IllegalStateException(errorMessage);
        }
    }

    protected Organization getOrganization(final Command command) {
        final String domain = command.getHeader().getDomain().get();
        return this.repository.fromDomain(domain).orElseThrow(() ->
                new IllegalArgumentException(String.format("Cannot replay '%s' for unknown organization", command.getType())));
    }
}