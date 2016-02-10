package io.sytac.resumator.store.bootstrap;

import io.sytac.resumator.command.Command;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.user.ProfileRepository;

import java.util.Optional;

/**
 * Abstract event executor to replay events
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public abstract class AbstractEventExecutor implements EventExecutor {

    protected final ReplayEventConfig config;

    public AbstractEventExecutor(final ReplayEventConfig config) {
        this.config = config;
    }

    /**
     * Checks presence of the id and domain in the header of the command
     * @param command the command to check
     */
    protected void checkIdInHeader(final Command command) {
        final Optional<String> id = Optional.ofNullable(command.getHeader().getId());
        if (!id.isPresent()) {
            throw new IllegalStateException("Cannot replay event '" + command.getType() + "' without id in the header");
        }
    }

    protected void checkDomainInHeader(final Command command) {
        final Optional<String> domain = Optional.ofNullable(command.getHeader().getDomain());
        if (!domain.isPresent()) {
            throw new IllegalStateException("Cannot replay event '" + command.getType() + "' without domain in the header");
        }
    }

    protected Organization getOrganization(final Command command) {
        final String domain = command.getHeader().getDomain();
        return this.config.getOrganizationRepository().fromDomain(domain).orElseThrow(() ->
                new IllegalArgumentException(String.format("Cannot replay '%s' for unknown organization", command.getType())));
    }

    protected ProfileRepository getProfileRepository() {
        return this.config.getProfileRepository();
    }
}