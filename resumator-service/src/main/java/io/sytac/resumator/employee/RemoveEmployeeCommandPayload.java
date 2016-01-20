package io.sytac.resumator.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sytac.resumator.command.CommandPayload;
import lombok.Getter;

/**
 * Defines the payload of a remove Employee command
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
@Getter
public class RemoveEmployeeCommandPayload implements CommandPayload {

    private final String employeeId;

    @JsonCreator
    public RemoveEmployeeCommandPayload(@JsonProperty("employeeId") final String employeeId) {
        this.employeeId = employeeId;
    }
}
