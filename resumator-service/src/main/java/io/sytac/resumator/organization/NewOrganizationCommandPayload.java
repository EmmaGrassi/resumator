package io.sytac.resumator.organization;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sytac.resumator.command.CommandPayload;
import lombok.Getter;

/**
 * Contains the details of a new organization to be created
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Getter
public class NewOrganizationCommandPayload implements CommandPayload {

    final String name;
    final String domain;

    @JsonCreator
    public NewOrganizationCommandPayload(@JsonProperty("name") final String name,
                                         @JsonProperty("domain") final String domain) {
        this.name = name;
        this.domain = domain;
    }
}
