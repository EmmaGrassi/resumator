package com.sytac.resumator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;

public class App {

	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
	
	
	private static final String NAME_COLUMN = "name";
	private static final String SURNAME_COLUMN = "surname";
	private static final String ADDRESS_COLUMN = "address";
	private static final String EXP1_COLUMN = "exp1";
	private static final String EXP2_COLUMN = "exp2";
	private static final String EXP3_COLUMN = "exp3";
	
	private static final String SERVICE_NAME = "resumator-service";
	private static final File DATA_STORE_DIR = new File(System.getProperty("user.home"), ".credentials/resumator");
	private static final List<String> SCOPES = Arrays.asList("https://spreadsheets.google.com/feeds","https://docs.google.com/feeds");
	private static final String CLIENT_TOKENS_PATH = "/client_secret.json";
	private static final String SHEET_FEED_URL = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";
	
	private static URL SPREADSHEET_FEED_URL;
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static FileDataStoreFactory DATA_STORE_FACTORY;
	private static HttpTransport HTTP_TRANSPORT;

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
			SPREADSHEET_FEED_URL = new URL(SHEET_FEED_URL);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}
	
	
	private SpreadsheetService service;
	private SpreadsheetFeed feed;
	private List<SpreadsheetEntry> spreadsheetsList;
	private SpreadsheetEntry sheet;
	private List<WorksheetEntry> worksheets;
	private String workingSheet;
	
	
	public SpreadsheetService getService() {
		return service;
	}

	public void setService(SpreadsheetService service) {
		this.service = service;
	}

	public SpreadsheetFeed getFeed() {
		return feed;
	}

	public void setFeed(SpreadsheetFeed feed) {
		this.feed = feed;
	}

	public List<SpreadsheetEntry> getSpreadsheetsList() {
		return spreadsheetsList;
	}

	public void setSpreadsheetsList(List<SpreadsheetEntry> spreadsheetsList) {
		this.spreadsheetsList = spreadsheetsList;
	}

	public SpreadsheetEntry getSheet() {
		return sheet;
	}

	public void setSheet(SpreadsheetEntry sheet) {
		this.sheet = sheet;
	}

	public List<WorksheetEntry> getWorksheets() {
		return worksheets;
	}

	public void setWorksheets(List<WorksheetEntry> worksheets) {
		this.worksheets = worksheets;
	}

	public String getWorkingSheetName() {
		return workingSheet;
	}

	public void setWorkingSheet(String sheetName) throws IOException, ServiceException {
		sheet = findSpreadSheet(sheetName); //throw exception if sheet not found
		worksheets = sheet.getWorksheets();
		this.workingSheet = sheetName;
	}
	
	

	public void init() throws IOException, ServiceException{
		service = initializeAndGetSsService();
		feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
		spreadsheetsList = feed.getEntries();
	}
	
	public String findResumeeInSpreadSheet(String name, int sheetPageNr) throws IOException, ServiceException{
		WorksheetEntry page = getWorkSheetEntryByPosition(sheetPageNr);
		ListFeed listFeed = service.getFeed(page.getListFeedUrl(), ListFeed.class);
		for (ListEntry row : listFeed.getEntries()) { // O(n^2)
			if(row.getCustomElements().getValue(NAME_COLUMN).equalsIgnoreCase(name)){
				return row.getPlainTextContent();
			}
		}
		return null;
	}
	

	public String insertGuy(int sheetPageNr, String name, String surname, String address, String exp1) throws IOException, ServiceException{
		WorksheetEntry page = getWorkSheetEntryByPosition(sheetPageNr);
		URL listFeedUrl = page.getListFeedUrl();
		ListEntry row = new ListEntry();
		row.getCustomElements().setValueLocal(NAME_COLUMN, name);
		row.getCustomElements().setValueLocal(SURNAME_COLUMN, surname);
		row.getCustomElements().setValueLocal(ADDRESS_COLUMN, address);
		row.getCustomElements().setValueLocal(EXP1_COLUMN, exp1);
		row = service.insert(listFeedUrl, row);
		return row.getPlainTextContent();
		
	}



	Credential authorize() throws IOException {
		InputStream in = App.class.getResourceAsStream(CLIENT_TOKENS_PATH);
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		LOGGER.info("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}

	SpreadsheetService initializeAndGetSsService() throws IOException {
		final SpreadsheetService service = new SpreadsheetService(SERVICE_NAME);
		service.setProtocolVersion(SpreadsheetService.Versions.V3);
		service.setOAuth2Credentials(authorize());
		return service;
	}
	
	SpreadsheetEntry findSpreadSheet(String sheetName){
		SpreadsheetEntry dbSheet = null;
		final Iterator<SpreadsheetEntry> iterator = spreadsheetsList.iterator();
		while (iterator.hasNext()) {
			dbSheet = iterator.next();
			if (dbSheet.getTitle().getPlainText().trim().equals(sheetName))
				return dbSheet;
		}
		throw new NoSuchElementException("SpreadsheetEntry " + sheetName + " not found");
	}
	
	private WorksheetEntry getWorkSheetEntryByPosition(int position){
		return worksheets.get(position);
	}

}
