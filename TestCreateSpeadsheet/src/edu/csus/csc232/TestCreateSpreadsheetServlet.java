/*package edu.csus.csc232;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TestCreateSpreadsheetServlet {

   	  private static String CLIENT_ID = "362703806467-bmhrt68shg3omqdek84p9rf8ftgpq4i7.apps.googleusercontent.com";
	  private static String CLIENT_SECRET = "gUEp3lWUhc4NYJcQCeqW2EQP";
	  private static String REDIRECT_URI = "https://arcane-storm-89009.appspot.com/";

	  public static void main(String[] args) throws IOException {
		    HttpTransport httpTransport = new NetHttpTransport();
		    JsonFactory jsonFactory = new JacksonFactory();
		   
		    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
		        httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
		        .setAccessType("online")
		        .setApprovalPrompt("auto").build();
		    
		    String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
		    System.out.println("Please open the following URL in your browser then type the authorization code:");
		    System.out.println("  " + url);
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		    String code = br.readLine();
		    
		    GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
		    GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);
		    
		    //Create a new authorized API client
		    Drive service = new Drive.Builder(httpTransport, jsonFactory, credential).build();

		    //Insert a file  
		    File body = new File();
		    body.setTitle("My sheet");
		    body.setDescription("A test spreadsheet");
		    body.setMimeType("application/vnd.ms-excel");
		    
		    java.io.File fileContent = new java.io.File("sheet.xls");
		    FileContent mediaContent = new FileContent("application/vnd.ms-excel", fileContent);
		    try
		    {
		    File file = service.files().insert(body, mediaContent).execute();
		    System.out.println("File ID: " + file.getId());
		    //return file;
	    } catch (IOException e) {
	      System.out.println("An error occured: " + e);
	      //return null;
		  }
		}
}*/


package edu.csus.csc232;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TestCreateSpreadsheetServlet {

	private static String CLIENT_ID = "362703806467-bmhrt68shg3omqdek84p9rf8ftgpq4i7.apps.googleusercontent.com";
	private static String CLIENT_SECRET = "gUEp3lWUhc4NYJcQCeqW2EQP";
	private static String REDIRECT_URI = "https://arcane-storm-89009.appspot.com/";
	
	public static final String spreadsheet_title = "Test_Spreadsheet1";
	public static final String sp_description = "Test-create-Spreadsheet";
	//public static final String mimetype = "application/vnd.ms-excel"; //worked but excel sheet table look lik text file
	//public static final String mimetype = "application/x-ms-excel"; //worked but excel sheet can not opened by google drive, when downloaded, file can be viewed in ms excel
	public static final String mimetype = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	//public static final String mimetype = "text/plain";
	//public static final String filename = "C:/Users/Pilluda/Eclipse_luna_4.4_workspace/TestCreateSpeadsheet/src/edu/csus/csc232/test.txt";
	public static final String filename = "C:/Users/Pilluda/Eclipse_luna_4.4_workspace/TestCreateSpeadsheet/src/edu/csus/csc232/sheet.xls";
	
	public static void main(String[] args) throws IOException {
	    HttpTransport httpTransport = new NetHttpTransport();
	    JsonFactory jsonFactory = new JacksonFactory();
	   
	    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	        httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
	        .setAccessType("online")
	        .setApprovalPrompt("auto").build();
	    
	    String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
	    System.out.println("Please open the following URL in your browser then type the authorization code:");
	    System.out.println("  " + url);
	    
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    String code = br.readLine();
	    
	    GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
	    GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);
	    
	    //Create a new authorized API client
	    Drive service = new Drive.Builder(httpTransport, jsonFactory, credential).build();
	    System.out.println ("calling insertfile from main ----");
	   insertFile(service, spreadsheet_title,  sp_description,  mimetype,  filename);
	    
	}
	  
	
	 /**
	   * Insert new file.
	   *
	   * @param service Drive API service instance.
	   * @param title Title of the file to insert, including the extension.
	   * @param description Description of the file to insert.
	   * @param parentId Optional parent folder's ID.
	   * @param mimeType MIME type of the file to insert.
	   * @param filename Filename of the file to insert.
	   * @return Inserted file metadata if successful, {@code null} otherwise.
	   */
  private static File insertFile(Drive service, String title, String description,
      String mimeType, String filename) {
    // File's metadata.
    File body = new File();
    body.setTitle(title);
    body.setDescription(description);
    body.setMimeType(mimeType);

    // Set the parent folder.
    /*if (parentId != null && parentId.length() > 0) {
      body.setParents(
          Arrays.asList(new ParentReference().setId(parentId)));
    }*/
    System.out.println ("inside insertfile 1");
    // File's content.
    java.io.File fileContent = new java.io.File(filename);
    FileContent mediaContent = new FileContent(mimeType, fileContent);
    try {
      File file = service.files().insert(body, mediaContent).execute();
      System.out.println ("inside insertfile 2");
      // Uncomment the following line to print the File ID.
      System.out.println("File ID: " + file.getId());

      return file;
      
    } catch (IOException e) {
      System.out.println("An error occured: " + e);
      return null;
    }
  }

 }


