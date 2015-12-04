package io.sytac.resumator.http;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;
import io.sytac.resumator.http.dto.ServiceInfoDTO;

import java.io.IOException;

/**
 * Created by skuro on 04/12/15.
 */
public class ServiceInfoDTOSerializer extends JsonSerializer<ServiceInfoDTO> {

    protected final RepresentationFactory rest = new JsonRepresentationFactory();

    @Override
    public void serialize(ServiceInfoDTO value, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        final Representation hal = rest.newRepresentation()
                .withProperty("app-name", value.getAppName())
                .withProperty("version", value.getVersion());
        value.getLinks().stream()
                .forEach(link -> hal.withLink(link.getRel(),
                        link.getHref(),
                        link.getName(),
                        link.getTitle(),
                        link.getHreflang(),
                        link.getProfile()));

        jgen.writeObject(hal);
    }

    @Override
    public Class<ServiceInfoDTO> handledType() {
        return ServiceInfoDTO.class;
    }
}
