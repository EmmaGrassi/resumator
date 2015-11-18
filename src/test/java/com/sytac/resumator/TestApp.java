package com.sytac.resumator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.api.client.auth.oauth2.Credential;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.util.ServiceException;



public class TestApp {
	
	private static URL SPREADSHEET_FEED_URL;
	
	static{
		try {
			SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	App app;
	
	@Before
	public void before(){
		app = new App();
	}
	
	@After
	public void after(){
		
	}



	
	
	@Test
	public void authorizationOK() throws IOException {
		final Credential credential = app.authorize();
		org.junit.Assert.assertNotNull(credential);
		org.junit.Assert.assertNotNull(credential.getAccessToken());
		org.junit.Assert.assertFalse(credential.getAccessToken().isEmpty());
	}
	
	
	@Test
	public void atLeastOneSpreadSheetInAccount() throws IOException, ServiceException{
		final SpreadsheetService service = app.initializeAndGetSsService();
		final SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
		final List<SpreadsheetEntry> spreadsheetsList = feed.getEntries();
		org.junit.Assert.assertFalse(spreadsheetsList.size() == 0);
	}
	
	@Test
	public void SpreadSheetTestExists() throws IOException, ServiceException {
		app.init();		
		org.junit.Assert.assertEquals("Test", (app.findSpreadSheet("Test")).getTitle().getPlainText());
		
	}
	
	
	
	
	 
}
