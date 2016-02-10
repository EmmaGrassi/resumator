package io.sytac.resumator.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderReaderSupport;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import io.sytac.resumator.Configuration;
import io.sytac.resumator.ObjectMapperFactory;
import io.sytac.resumator.employee.Employee;
import io.sytac.resumator.model.enums.Nationality;
import io.sytac.resumator.security.UserPrincipal;
import io.sytac.resumator.security.UserPrincipalFactoryProvider;
import io.sytac.resumator.security.UserPrincipalParamResolver;
import io.sytac.resumator.user.Profile;
import io.sytac.resumator.utils.DateUtils;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider;
import org.glassfish.jersey.test.JerseyTest;

import javax.inject.Singleton;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
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
                .packages("io.sytac.resumator.employee",
                          "io.sytac.resumator.service")
                .register(new ConfigurationBinder())
                .register(new ObjectMapperFactory())
                .register(JaxRsHalBuilderSupport.class)
                .register(JaxRsHalBuilderReaderSupport.class)
                .register(EmployeeMessageBodyReader.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(UserPrincipalFactoryProvider.class)
                                .to(ValueFactoryProvider.class)
                                .in(Singleton.class);
                        bind(UserPrincipalParamResolver.class)
                                .to(new TypeLiteral<InjectionResolver<UserPrincipal>>() {})
                                .in(Singleton.class);
                    }
                });
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
        public Employee readFrom(Class<Employee> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                                 MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
                throws IOException, WebApplicationException {

            final Map<String, Object> map = mapper.readerFor(Map.class).readValue(entityStream);
            final Profile profile = (Profile)map.get("profile");


            return Employee.builder()
                    .id(map.get("id").toString())
                    .profile(Profile.builder()
                            .title(profile.getTitle())
                            .name(map.get("name").toString())
                            .surname(map.get("surname").toString())
                            .email(map.get("email").toString())
                            .phoneNumber(map.get("phonenumber").toString())
                            .gitHub(map.get("github").toString())
                            .linkedIn(map.get("linkedin").toString())
                            .dateOfBirth(DateUtils.convert(map.get("dateOfBirth").toString()))
                            .nationality(Nationality.valueOf(map.get("nationality").toString()))
                            .cityOfResidence("")
                            .countryOfResidence("")
                            .aboutMe("")
                            .admin(false)
                            .build())
                    .educations(null)
                    .courses(null)
                    .experiences(null)
                    .languages(null)
                    .build();
        }
    }

}
