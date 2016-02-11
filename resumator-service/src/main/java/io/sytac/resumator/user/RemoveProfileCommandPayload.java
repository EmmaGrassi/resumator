package io.sytac.resumator.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sytac.resumator.command.CommandPayload;
import lombok.Getter;

/**
 * Defines the payload of a remove Profile command
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
@Getter
public class RemoveProfileCommandPayload implements CommandPayload {

    private final String profileId;

    @JsonCreator
    public RemoveProfileCommandPayload(@JsonProperty("profileId") final String profileId) {
        this.profileId = profileId;
    }
}
