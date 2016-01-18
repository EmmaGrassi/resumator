package io.sytac.resumator.employee;

import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.model.exceptions.InvalidOrganizationException;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Retrieve information about one employee
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public abstract class BaseEmployee extends BaseResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseEmployee.class);

    private final OrganizationRepository organizations;


    public BaseEmployee(final OrganizationRepository organizations) {
        this.organizations = organizations;
    }

    protected OrganizationRepository getOrganizations() {
        return organizations;
    }

    protected String getOrgId(SecurityContext securityContext) {
        final Principal user = securityContext.getUserPrincipal();
        if (user instanceof User) {
            return ((User) user).getOrganizationId();
        }

        LOGGER.warn("Tried to execute a command on a non-existing organization");
        throw new InvalidOrganizationException();
    }

    protected String getOrgDomain(SecurityContext securityContext) {
        return organizations.get(getOrgId(securityContext)).orElseThrow(InvalidOrganizationException::new).getDomain();
    }
}
