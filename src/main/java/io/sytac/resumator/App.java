package io.sytac.resumator;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gdata.util.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class App {

	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	public void init() throws IOException, ServiceException{
	}


}
