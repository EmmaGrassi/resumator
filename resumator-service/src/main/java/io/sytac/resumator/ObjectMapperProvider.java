package io.sytac.resumator;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;
    
    @Inject
    public ObjectMapperProvider(ObjectMapper mapper) {
	super();
	this.mapper = mapper;
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {      
        return mapper;
    }
}