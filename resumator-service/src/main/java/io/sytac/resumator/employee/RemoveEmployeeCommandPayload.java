package io.sytac.resumator.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sytac.resumator.command.CommandPayload;
import io.sytac.resumator.model.Course;
import io.sytac.resumator.model.Education;
import io.sytac.resumator.model.Experience;
import io.sytac.resumator.model.Language;
import lombok.Getter;

import java.util.List;

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
