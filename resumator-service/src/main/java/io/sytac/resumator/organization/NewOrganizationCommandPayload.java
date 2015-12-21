package io.sytac.resumator.organization;

import io.sytac.resumator.command.CommandPayload;

/**
 * Contains the details of a new organization to be created
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class NewOrganizationCommandPayload implements CommandPayload {

    final String name;
    final String domain;

    public NewOrganizationCommandPayload(final String name, final String domain) {
        this.name = name;
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public String getDomain() {
        return domain;
    }
}
