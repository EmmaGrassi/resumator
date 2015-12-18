package io.sytac.resumator.employee;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.sytac.resumator.command.CommandHeader;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

/**
 * Deserializes {@link NewEmployeeCommand} from JSon
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class NewEmployeeCommandDeserializer extends JsonDeserializer<NewEmployeeCommand> {

    @Override
    public NewEmployeeCommand deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException {
        final JsonNode root = p.getCodec().readTree(p);
        final CommandHeader header = parseHeader(root);
        final NewEmployeeCommandPayload payload = parsePayload(root);

        return new NewEmployeeCommand(header, payload);
    }

    private NewEmployeeCommandPayload parsePayload(final JsonNode root) {
        final JsonNode payloadJson = root.get("payload");
        final String org = nullable(payloadJson, "organizationId");
        final String name = nullable(payloadJson, "name");
        final String surname = nullable(payloadJson, "surname");
        final String year = nullable(payloadJson, "yearOfBirth");
        final String nation = nullable(payloadJson, "nationality");
        final String residence = nullable(payloadJson, "currentResidence");
        return new NewEmployeeCommandPayload(org, name, surname, year, nation, residence);
    }

    private CommandHeader parseHeader(final JsonNode node) {
        final JsonNode headerJson = node.get("header");
        final Date date = new Date(headerJson.get("timestamp").asLong());
        return new CommandHeader(date);
    }

    private String nullable(final JsonNode node, final String field){
        return Optional.ofNullable(node.get(field))
                .map(JsonNode::asText)
                .orElse(null);
    }
}
