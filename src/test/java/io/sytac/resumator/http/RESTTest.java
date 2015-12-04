package io.sytac.resumator.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theoryinpractise.halbuilder.api.ContentRepresentation;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;
import io.sytac.resumator.Configuration;
import io.sytac.resumator.ObjectMapperResolver;
import io.sytac.resumator.model.Employee;
import io.sytac.resumator.model.EmployeeId;
import io.sytac.resumator.model.enums.Nationality;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Base class that initializes Jersey and HAL [de]serialization to support testing REST endpoints
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class RESTTest extends JerseyTest {

    @Override
    public Application configure() {
        final ResourceConfig resourceConfig = new ResourceConfig();
        return resourceConfig
                .packages("io.sytac.resumator.http")
                .register(new ConfigurationBinder())
                .register(new ObjectMapperResolver())
                .register(HALMessageBodyReader.class)
                .register(HALMessageBodyWriter.class)
                .register(EmployeeMessageBodyReader.class);
    }

    /**
     * Makes sure to include a {@link Configuration} instance into the injection context
     */
    protected static class ConfigurationBinder extends AbstractBinder {
        @Override
        protected void configure() {
            bind(new Configuration());
        }
    }

    /**
     * Enables support to read HAL formatted JSON documents
     */
    protected static class HALMessageBodyReader implements MessageBodyReader<ContentRepresentation> {

        @Override
        public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
            return type == ContentRepresentation.class &&
                    mediaType.toString().equals(RepresentationFactory.HAL_JSON);
        }

        @Override
        public ContentRepresentation readFrom(Class<ContentRepresentation> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
            return new JsonRepresentationFactory().readRepresentation(mediaType.toString(), new InputStreamReader(entityStream));
        }
    }

    /**
     * Enables support to write HAL formatted JSON documents
     */
    protected static class HALMessageBodyWriter implements MessageBodyWriter<Representation> {

        @Override
        public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
            return Representation.class.isAssignableFrom(type) &&
                    mediaType.toString().equals(RepresentationFactory.HAL_JSON);
        }

        @Override
        public long getSize(Representation representation, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
            return -1; // see javadocs on the interface
        }

        @Override
        public void writeTo(Representation representation, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
            final String serialized = new JsonRepresentationFactory().newRepresentation().withBean(type).toString();
            entityStream.write(serialized.getBytes(Charset.forName("UTF-8")));
        }
    }

    protected static class EmployeeMessageBodyReader implements MessageBodyReader<Employee> {

        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
            return type.equals(Employee.class)  &&
                    mediaType.toString().equals(RepresentationFactory.HAL_JSON);
        }

        @Override
        public Employee readFrom(Class<Employee> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
            Map<String, Object> map = mapper.readerFor(Map.class).readValue(entityStream);
            return new Employee(new EmployeeId(map.get("id").toString()),
                                                map.get("name").toString(),
                                                map.get("surname").toString(),
                                                Integer.valueOf(map.get("yearOfBirth").toString()),
                                                Nationality.valueOf(map.get("nationality").toString()),
                                                map.get("currentResidence").toString());
        }
    }

}
