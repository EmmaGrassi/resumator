package io.sytac.resumator.employee;

import io.sytac.resumator.command.CommandHeader;

/**
 * Deserializes {@link NewEmployeeCommand} from JSon
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class NewEmployeeCommandDeserializer extends EmployeeCommandDeserializer<NewEmployeeCommand> {

    @Override
    public NewEmployeeCommand getEmployeeCommandInstance(final CommandHeader header, final EmployeeCommandPayload payload) {
        return new NewEmployeeCommand(header, payload);
    }
}
