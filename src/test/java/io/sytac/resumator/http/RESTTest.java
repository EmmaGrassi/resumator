package io.sytac.resumator.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theoryinpractise.halbuilder.api.ContentRepresentation;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderReaderSupport;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
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
                .register(JaxRsHalBuilderSupport.class)
                .register(JaxRsHalBuilderReaderSupport.class)
                .register(EmployeeMessageBodyReader.class)
                ;
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
