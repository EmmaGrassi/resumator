package com.sytac.resumator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
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
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class App {

	private static final String SERVICE_NAME = "resumator-service";

	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	private static final File DATA_STORE_DIR = new File(System.getProperty("user.home"), ".credentials/resumator");

	private static final List<String> SCOPES = Arrays.asList("https://spreadsheets.google.com/feeds",
			"https://docs.google.com/feeds");

	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	private static FileDataStoreFactory DATA_STORE_FACTORY;

	private static HttpTransport HTTP_TRANSPORT;

	private static final String CLIENT_TOKENS_PATH = "/client_secret.json";

	private static final String SHEET_FEED_URL = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";

	public static URL SPREADSHEET_FEED_URL;

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

	public static JsonFactory getJsonFactory() {
		return JSON_FACTORY;
	}

	public static List<String> getScopesList() {
		return SCOPES;
	}

	public static File getDataStoreDir() {
		return DATA_STORE_DIR;
	}

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	public static Credential authorize() throws IOException {
		InputStream in = App.class.getResourceAsStream(CLIENT_TOKENS_PATH);
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		LOGGER.info("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}

	public static SpreadsheetService initializeAndGetSsService() throws IOException {
		final SpreadsheetService service = new SpreadsheetService(SERVICE_NAME);
		service.setProtocolVersion(SpreadsheetService.Versions.V3);
		service.setOAuth2Credentials(authorize());
		return service;
	}

	public static SpreadsheetEntry findSpreadSheet(List<SpreadsheetEntry> spreadsheets, String sheetName)
			throws NoSuchElementException {
		SpreadsheetEntry dbSheet = null;
		final Iterator<SpreadsheetEntry> iterator = spreadsheets.iterator();
		while (iterator.hasNext()) {
			dbSheet = iterator.next();
			if (dbSheet.getTitle().getPlainText().trim().equals(sheetName))
				return dbSheet;
		}
		throw new NoSuchElementException("SpreadsheetEntry " + sheetName + " not found");
	}

	public static void main(String[] args)
			throws AuthenticationException, MalformedURLException, IOException, ServiceException {

		final SpreadsheetService service = initializeAndGetSsService();
		final SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
		final List<SpreadsheetEntry> spreadsheetsList = feed.getEntries();
		
		final SpreadsheetEntry sheet = findSpreadSheet(spreadsheetsList, "Test");
		LOGGER.info("Sheet name found: " + sheet.getTitle().getPlainText());
		final List<WorksheetEntry> worksheets = sheet.getWorksheets();

		for (WorksheetEntry worksheet : worksheets) {
			String title = worksheet.getTitle().getPlainText();
			int rowCount = worksheet.getRowCount();
			int colCount = worksheet.getColCount();
			URL listFeedUrl = worksheet.getListFeedUrl();
			ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);
			for (ListEntry row : listFeed.getEntries()) {
				System.out.print(row.getPlainTextContent() + "\n");
				/*
				 * for (String tag : row.getCustomElements().getTags()) {
				 * System.out.print(row.getCustomElements().getValue(tag) +
				 * "\n"); }
				 */
				// LOGGER.info();
			}
			ListEntry row = new ListEntry();
			row.getCustomElements().setValueLocal("name", "Joe");
			row.getCustomElements().setValueLocal("surname", "Smith");
			row.getCustomElements().setValueLocal("address", "2011LS");
			row.getCustomElements().setValueLocal("exp1", "Sytac yep");
			row = service.insert(listFeedUrl, row);
			// LOGGER.info("\t" + title + "- rows:" + rowCount + " cols: " +
			// colCount);
		}
	}

}
