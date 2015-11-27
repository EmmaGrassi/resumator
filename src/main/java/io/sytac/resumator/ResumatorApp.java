package io.sytac.resumator;

import java.io.IOException;

import com.google.gdata.util.ServiceException;

public class ResumatorApp {

	public static void main(String[] args) throws IOException, ServiceException {
		App app = new App();
		app.init();
	}
}
