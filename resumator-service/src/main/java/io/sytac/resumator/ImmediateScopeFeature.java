package io.sytac.resumator;

import javax.inject.Inject;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

/**
 * Enables resources to be instantiated eagerly instead of upon need
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class ImmediateScopeFeature implements Feature {

    @Inject
    public ImmediateScopeFeature(final ServiceLocator locator) {
        ServiceLocatorUtilities.enableImmediateScope(locator);
    }

    @Override
    public boolean configure(FeatureContext context) {
        return true;
    }
}