package io.sytac.resumator.halbuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.theoryinpractise.halbuilder.json.JsonRepresentationWriter;

import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.util.Set;

/**
 * Custom {@link JsonRepresentationWriter} for configuring halbuilder with custom modules and de(serializers).
 */
public class ResumatorJsonRepresentationWriter extends JsonRepresentationWriter {

    private final ObjectMapper objectMapper;

    public ResumatorJsonRepresentationWriter() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());

        /**
         * Needed because halbuilder doesn't filter <code>null</code>s coming from {@link Jdk8Module}.
         */
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    @Override
    protected JsonGenerator getJsonGenerator(Set<URI> flags, Writer writer) throws IOException {
        JsonGenerator jsonGenerator = super.getJsonGenerator(flags, writer);
        jsonGenerator.setCodec(objectMapper);
        return jsonGenerator;
    }
}
