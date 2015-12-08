package io.sytac.resumator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.sytac.resumator.http.EmployeeIdSerializer;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;


/**
 * Configures the Jackson [de]serializer to work with the domain classes
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Provider
public class ObjectMapperResolver implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;

    public ObjectMapperResolver() {
        final SimpleModule resumator = initResumatorSerializers();
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.registerModule(resumator);
    }

    protected SimpleModule initResumatorSerializers() {
        final SimpleModule resumator = new SimpleModule("Resumator");
        resumator.addSerializer(new EmployeeIdSerializer());
        return resumator;
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}
