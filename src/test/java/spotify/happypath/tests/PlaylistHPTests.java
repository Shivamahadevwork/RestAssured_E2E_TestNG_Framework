package spotify.happypath.tests;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;

import spotify.createplaylist_response_hp.pojo.CreatePlaylist;
import spotify.getplaylist_response_hp.pojo.GetPlaylist;
import spotify.playlist_requests.pojo.CreatePlaylist_Request;
import spotify.playlist_requests.pojo.UpdatePlaylist_Request;
import testcomponents.BaseTest;
import testcomponents.JSONComparator;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.*;

public class PlaylistHPTests extends BaseTest{
	
		@Description("Valid details are passed as API input in this test case and a valid response code and body is expected in return")
		@Test(priority=1, description = "Validate the Happy Path flow of Create Playlist API")
		public void validateCreatePlaylist(ITestContext context) throws IOException {
		Response rawActualResponse = given().
								  spec(getRequestSpec()).
								  body(testdata.testdataHPCreatePlaylist()).		
								  when().
								  post("/users/31gzoo4n36wlnqcsqi5pm3dywcbi/playlists").		
								  then().
								  spec(getResponseSpec()).
								  extract().
								  response();	
		
		double responseTime = rawActualResponse.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println(responseTime);
		
		// Assert status code and length>0 of the response from the rawActualResponse
		
		softAssert.assertEquals(rawActualResponse.getStatusCode(), 201);
		softAssert.assertTrue(rawActualResponse.asString().length()>0);
				
		CreatePlaylist deserializedActualResponse = rawActualResponse.as(CreatePlaylist.class);		

		// Assert if the response field values match with the request values from the deserialized response		
		
		softAssert.assertEquals(deserializedActualResponse.getName(), testdata.testdataHPCreatePlaylist().getName());
		softAssert.assertEquals(deserializedActualResponse.getDescription(), testdata.testdataHPCreatePlaylist().getDescription());
		softAssert.assertEquals(deserializedActualResponse.getPublic(), testdata.testdataHPCreatePlaylist().get_public());
		
		// Convert the rawActualResponse to String
		String rawActualResponseStg = rawActualResponse.asString();
						
		// Assert the keys in the response from the response converted as String		
		List<String> itemCount = JsonPath.read(rawActualResponseStg, "$..href");		
		softAssert.assertEquals(itemCount.size(), 4);
		
		// Assert all fields in the actual response vs expected response		
		try {
			File expectedResponseJSON = new File(System.getProperty("user.dir")+"//src//main//resources//expected_responses//Create_Playlist_Response.json");			
			JSONComparator.compareJSONFiles(rawActualResponseStg, expectedResponseJSON);
		} catch (AssertionError e) {
			System.out.println("Alert - mismatches noted! Validate them carefully.");
		}
		
		context.setAttribute("playlistId", deserializedActualResponse.getId());
		softAssert.assertAll();	
		}
		
		@Description("Valid details are passed as API input in this test case and a valid response code and body is expected in return")
		@Test(priority=2, description = "Validate the Happy Path flow of Update Playlist API")
		public void validateUpdatePlaylist(ITestContext context) throws FileNotFoundException {
		String playlistId =  (String) context.getAttribute("playlistId");
		
		Response rawActualResponse	= given().
								  spec(getRequestSpec()).
								  body(testdata.testdataHPUpdatePlaylist()).								  
								  when().put("/playlists/"+playlistId).								  
								  then().spec(getResponseSpec()).
								  extract().
								  response();	
		
		double responseTime = rawActualResponse.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println(responseTime);
		
		softAssert.assertEquals(rawActualResponse.getStatusCode(), 200);
		softAssert.assertTrue(rawActualResponse.asString().length()==0, "Alert - The Update Playlist response is NOT empty!");
		softAssert.assertAll();	
		}
		
		@Description("Valid details are passed as API input in this test case and a valid response code and body is expected in return")
		@Test(priority=3, description = "Validate the Happy Path flow of Get Playlist API")
		public void validatGetPlaylist(ITestContext context) throws IOException {
		String playlistId = (String) context.getAttribute("playlistId");
		Response rawActualResponse = given().
								  spec(getRequestSpec()).
								  when().
								  get("/playlists/"+playlistId).
								  then().
								  spec(getResponseSpec()).
								  extract().
								  response();	
		
		double responseTime = rawActualResponse.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println(responseTime);	
		softAssert.assertEquals(rawActualResponse.getStatusCode(), 200);
		softAssert.assertTrue(rawActualResponse.asString().length()>0);
		softAssert.assertEquals(rawActualResponse.path("tracks.limit"), 100);
		softAssert.assertEquals(rawActualResponse.path("tracks.next"), null);
		
		GetPlaylist deserializedActualResponse = rawActualResponse.as(GetPlaylist.class);
		softAssert.assertEquals(deserializedActualResponse.getId(), playlistId);
		softAssert.assertEquals(deserializedActualResponse.getOwner().getDisplayName(), "Shiva");				
		
		String rawActualResponseStg = rawActualResponse.asString();
		
		List<String> hrefList = JsonPath.read(rawActualResponseStg, "$..href");		
		Assert.assertTrue(hrefList.size()==4, "Alert- the size is not equal to 4!");		
			
		try {
			File expectedResponseJSON = new File(System.getProperty("user.dir")+"//src//main//resources//expected_responses//Get_Playlist_Response.json");
			JSONComparator.compareJSONFiles(rawActualResponseStg, expectedResponseJSON);		
		} catch (AssertionError e) {
			System.out.println("Alert - mismatches noted! Validate them carefully.");
		}		
		softAssert.assertAll();
		}
		
		

}
