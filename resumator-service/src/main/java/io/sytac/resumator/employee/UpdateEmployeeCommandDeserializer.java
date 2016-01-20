package io.sytac.resumator.employee;

import io.sytac.resumator.command.CommandHeader;

/**
 * Deserializes {@link NewEmployeeCommand} from JSon
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class UpdateEmployeeCommandDeserializer extends EmployeeCommandDeserializer<UpdateEmployeeCommand> {

    @Override
    public UpdateEmployeeCommand getEmployeeCommandInstance(final CommandHeader header, final EmployeeCommandPayload payload) {
        return new UpdateEmployeeCommand(header, payload);
    }
}
