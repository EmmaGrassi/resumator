package com.sytac.resumator;

import java.io.IOException;

import com.google.gdata.util.ServiceException;

public class ResumatorApp {

	
	public static void main(String[] args) throws IOException, ServiceException {
		App app = new App();
		app.init();
		app.setWorkingSheet("Test");
		System.out.println(app.findResumeeInSpreadSheet("Joe", 0));
		System.out.println(app.insertGuy(0, "A name", "A Surname", "2011LS", "Sytac ruls"));
	}
}
