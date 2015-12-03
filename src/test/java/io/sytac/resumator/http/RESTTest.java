package io.sytac.resumator.http;

import com.google.common.collect.Sets;
import com.theoryinpractise.halbuilder.api.ContentRepresentation;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;
import io.sytac.resumator.Configuration;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Base class that initializes Jersey and HAL [de]serialization to support testing REST endpoints
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class RESTTest extends JerseyTest {

    @Override
    public Application configure() {
        return new ResourceConfig()
                .packages("io.sytac.resumator.http")
                .register(new ConfigurationBinder())
                .register(HALMessageBodyReader.class)
                .register(HALMessageBodyWriter.class);
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
            new JsonRepresentationFactory().lookupRenderer(mediaType.toString()).write(representation, Sets.newHashSet(RepresentationFactory.STRIP_NULLS), new OutputStreamWriter(entityStream));
        }
    }

}
