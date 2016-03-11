package io.sytac.resumator.halbuilder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theoryinpractise.halbuilder.json.JsonRepresentationWriter;

import io.sytac.resumator.ObjectMapperFactory;

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
	
	ObjectMapperFactory mapperFactory = new ObjectMapperFactory();
        objectMapper = mapperFactory.provide();
    }
    

    @Override
    protected JsonGenerator getJsonGenerator(Set<URI> flags, Writer writer) throws IOException {
        JsonGenerator jsonGenerator = super.getJsonGenerator(flags, writer);
        jsonGenerator.setCodec(objectMapper);
        return jsonGenerator;
    }
}
