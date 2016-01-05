package io.sytac.resumator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import io.sytac.resumator.employee.EmployeeIdSerializer;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.employee.NewEmployeeCommandDeserializer;
import org.glassfish.hk2.api.Factory;

import javax.ws.rs.ext.Provider;


/**
 * Configures the Jackson [de]serializer to work with the domain classes
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Provider
public class ObjectMapperResolver implements Factory<ObjectMapper> {

    private final ObjectMapper mapper;

    public ObjectMapperResolver() {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.registerModule(initResumatorSerializers());
        mapper.registerModule(new Jdk8Module());
    }

    protected SimpleModule initResumatorSerializers() {
        final SimpleModule resumator = new SimpleModule("Resumator");
        resumator.addSerializer(new EmployeeIdSerializer());
        resumator.addDeserializer(NewEmployeeCommand.class, new NewEmployeeCommandDeserializer());
        return resumator;
    }

    @Override
    public ObjectMapper provide() {
        return mapper;
    }

    @Override
    public void dispose(ObjectMapper instance) {
        // NOP
    }
}
