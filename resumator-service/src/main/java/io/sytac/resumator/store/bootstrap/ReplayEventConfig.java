package io.sytac.resumator.store.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.organization.OrganizationRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Contains injected object of {@link OrganizationRepository} and {@link ObjectMapper}
 * This class is used in the implementations of {@link EventExecutor}
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
@Getter
@AllArgsConstructor
public final class ReplayEventConfig {
    private final OrganizationRepository organizationRepository;
    private final ObjectMapper objectMapper;
}
