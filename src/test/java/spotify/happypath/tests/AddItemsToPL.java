package spotify.happypath.tests;
import testcomponents.BaseTest;
import testcomponents.JSONComparator;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import spotify.playlist_requests.pojo.AddItemsPL;
import spotify.additemstoplaylist_response_hp.pojo.AddItemsPLRes;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;



public class AddItemsToPL extends BaseTest{
	
		@Description("Valid details are passed as API input in this test case and a valid response code and body is expected in return")
		@Test(description = "Validate the Happy Path flow of Add Items to Playlist API")
		public void validateAddItemsToPL() throws FileNotFoundException {
		//Prepare pre-serialized request
		AddItemsPL preserializedRequest = new AddItemsPL();	
		List<String> uriList = new ArrayList<>();
		uriList.add("spotify:track:4iV5W9uYEdYUVa79Axb7Rh");
		preserializedRequest.setUris(uriList);
		preserializedRequest.setPosition(0);
		
		//Prepare raw Rest Assured response
		Response rawActualResponse = given().
											spec(getRequestSpec()).body(preserializedRequest).
									 when().
											post("/playlists/4oBuQCcu1GTranlpNpklwh/tracks").
									 then().
									 		spec(getResponseSpec()).
									 		extract().
									 		response();
		
		double responseTime = rawActualResponse.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println(responseTime);
		
		softAssert.assertEquals(rawActualResponse.getStatusCode(), 201);		
		softAssert.assertTrue(rawActualResponse.asString().length()>0);
		softAssert.assertNotNull(rawActualResponse.path("snapshot_id"));
		assertThat(rawActualResponse.path("snapshot_id").toString(), matchesRegex("^[A-Za-z0-9+/=]+$"));
				
		// Prepare deserialized response from the raw response
		AddItemsPLRes deserializedActualResponse = rawActualResponse.as(AddItemsPLRes.class);
		softAssert.assertTrue(deserializedActualResponse.toString().length()>0);
		softAssert.assertNotNull(deserializedActualResponse.getSnapshot_id());
		assertThat(deserializedActualResponse.getSnapshot_id(), matchesRegex("^[A-Za-z0-9+/=]+$"));				
		
		// Convert the raw response to response as String
		String rawActualResponseStg = rawActualResponse.asString();
		
		// Collecting and Reporting all soft assertions
		softAssert.assertAll();			
		}
		

}
