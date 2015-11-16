package com.sytac.resumator;

import java.io.IOException;
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
	
	App app;
	
	@Before
	public void before(){
		app = new App();
	}
	
	@After
	public void after(){
		
	}

	@Test
	public void dataStoreFilePresent() {
		org.junit.Assert.assertNotNull(App.getDataStoreDir());
	}
	
	@Test
	public void scopesNotNull() {
		org.junit.Assert.assertFalse(App.getScopesList().isEmpty());
	}

	
	
	@Test
	public void authorizationOK() throws IOException {
		final Credential credential = App.authorize();
		org.junit.Assert.assertNotNull(credential);
		org.junit.Assert.assertNotNull(credential.getAccessToken());
		org.junit.Assert.assertFalse(credential.getAccessToken().isEmpty());
	}
	
	
	@Test
	public void atLeastOneSpreadSheet() throws IOException, ServiceException{
		final SpreadsheetService service = App.initializeAndGetSsService();
		final SpreadsheetFeed feed = service.getFeed(App.SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
		final List<SpreadsheetEntry> spreadsheetsList = feed.getEntries();
		org.junit.Assert.assertFalse(spreadsheetsList.size() == 0);
	}
	
	@Test
	public void testSpreadSheetExists() throws IOException, ServiceException {
		final SpreadsheetService service = App.initializeAndGetSsService();
		final SpreadsheetFeed feed = service.getFeed(App.SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
		final List<SpreadsheetEntry> spreadsheetsList = feed.getEntries();
		
		org.junit.Assert.assertEquals("Test", (App.findSpreadSheet(spreadsheetsList,"Test")).getTitle().getPlainText());
		
	}
	
	
	
	
	 
}
