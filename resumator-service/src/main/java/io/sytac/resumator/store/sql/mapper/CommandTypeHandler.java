package io.sytac.resumator.store.sql.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.ObjectMapperResolver;
import io.sytac.resumator.command.Command;
import io.sytac.resumator.employee.NewEmployeeCommand;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

/**
 * Translates commands into blobs
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@MappedTypes(Command.class)
public class CommandTypeHandler extends BaseTypeHandler<Command> {

    private final ObjectMapper json;

    public CommandTypeHandler() {
        this.json = new ObjectMapperResolver().getContext(null);
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Command command, JdbcType jdbcType) throws SQLException {
        final byte[] bytes = toBytes(command);
        preparedStatement.setBinaryStream(i, new ByteArrayInputStream(bytes), bytes.length);
    }

    @Override
    public Command getNullableResult(ResultSet resultSet, String column) throws SQLException {
        return Optional.ofNullable(resultSet.getBlob(column))
                .map(blob -> {
                    try {
                        return blob.getBytes(1, (int) blob.length());
                    } catch (SQLException e) {
                        throw new IllegalArgumentException(e);
                    }
                })
                .map(this::fromBytes)
                .orElse(null);
    }

    @Override
    public Command getNullableResult(ResultSet resultSet, int index) throws SQLException {
        return Optional.ofNullable(resultSet.getBlob(index))
                .map(blob -> {
                    try {
                        return blob.getBytes(1, (int) blob.length());
                    } catch (SQLException e) {
                        throw new IllegalArgumentException(e);
                    }
                })
                .map(this::fromBytes)
                .orElse(null);
    }

    @Override
    public Command getNullableResult(CallableStatement callableStatement, int index) throws SQLException {
        return Optional.ofNullable(callableStatement.getBlob(index))
                .map(blob -> {
                    try {
                        return blob.getBytes(1, (int) blob.length());
                    } catch (SQLException e) {
                        throw new IllegalArgumentException(e);
                    }
                })
                .map(this::fromBytes)
                .orElse(null);
    }

    private byte[] toBytes(final Command command) {
        try {
            return json.writeValueAsBytes(command);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Command fromBytes(final byte[] bytes) {
        try {
            final Map map = json.readValue(bytes, Map.class);
            return Optional.ofNullable(map.get("type"))
                    .map(type -> {
                        final Command command;
                        switch (type.toString()) {
                            case "newEmployee":
                                try {
                                    command = json.readValue(bytes, NewEmployeeCommand.class);
                                } catch (IOException e) {
                                    throw new IllegalArgumentException(e);
                                }
                                break;
                            default:
                                command = null;
                        }

                        return command;
                    })
                    .orElseThrow(() -> new IllegalArgumentException("Event type not supported"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
