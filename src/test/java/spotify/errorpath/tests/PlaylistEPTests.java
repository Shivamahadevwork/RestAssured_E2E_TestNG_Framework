package spotify.errorpath.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import spotify.playlist_requests.pojo.CreatePlaylist_Request;
import spotify.playlist_responses_ep.pojo.Error_Root;
import testcomponents.BaseTest;
import testcomponents.JSONComparator;

public class PlaylistEPTests extends BaseTest{
	
	@Description("No name is passed as inpuut in the payload in this test case and an error response code and body is expected in return")
	@Test(priority=1, description = "Validate the Error Path flow of Create Playlist API")
	public void validateCreatePlaylistWithoutName(ITestContext context) throws IOException {
	
	Response rawActualresponse = given().
		  spec(getRequestSpec()).
		  body(testdata.testdataEPCreatePlaylist()).		
	when().
		  post("/users/31gzoo4n36wlnqcsqi5pm3dywcbi/playlists").		
	then().
		  spec(getResponseSpec()).
		  extract().
		  response();
	
	double responseTime = rawActualresponse.getTimeIn(TimeUnit.MILLISECONDS);
	System.out.println(responseTime);
	softAssert.assertEquals(rawActualresponse.getStatusCode(), 400);
	softAssert.assertTrue(rawActualresponse.asString().length()>0);
	
	Error_Root deserializedActualResponse = rawActualresponse.as(Error_Root.class);
	softAssert.assertSame(deserializedActualResponse.getError().getStatus(), 400);
	softAssert.assertEquals(deserializedActualResponse.getError().getMessage(), "The access token expired");
	
	String actualResponseStg = rawActualresponse.asString();
	File expectedRespnseJSON = new File(System.getProperty("user.dir")+"//src//main//resources//expected_responses//CreatePlaylistWithoutName.json");
	JSONComparator.compareJSONFiles(actualResponseStg, expectedRespnseJSON);	
	}
							  	

}
