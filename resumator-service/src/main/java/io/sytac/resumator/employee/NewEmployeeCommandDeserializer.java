package io.sytac.resumator.employee;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.sytac.resumator.command.CommandHeader;

import java.io.IOException;
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
        final String org = nullable(payloadJson, "organizationDomain");
        final String title = nullable(payloadJson, "title");
        final String name = nullable(payloadJson, "name");
        final String surname = nullable(payloadJson, "surname");
        final String email = nullable(payloadJson, "email");
        final String phonenumber = nullable(payloadJson, "phonenumber");
        final String github = nullable(payloadJson, "github");
        final String linkedin = nullable(payloadJson, "linkedin");
        final String dateOfBirth = nullable(payloadJson, "dateOfBirth");
        final String nation = nullable(payloadJson, "nationality");
        return new NewEmployeeCommandPayload(null, title, name, surname, email, phonenumber, github, linkedin, dateOfBirth, nation, "", "", null, null, null, null);
    }

    private CommandHeader parseHeader(final JsonNode node) {
        final JsonNode headerJson = node.get("header");
        final Long timestamp = headerJson.get("timestamp").asLong();
        final String domain = headerJson.get("domain").asText();
        final String id = headerJson.get("id").asText();
        return new CommandHeader.Builder().setId(id).setDomain(domain).setTimestamp(timestamp).build();
    }

    private String nullable(final JsonNode node, final String field){
        return Optional.ofNullable(node.get(field))
                .map(JsonNode::asText)
                .orElse(null);
    }
}
