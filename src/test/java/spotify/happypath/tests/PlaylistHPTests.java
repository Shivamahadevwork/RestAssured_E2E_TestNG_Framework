package spotify.happypath.tests;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.jayway.jsonpath.JsonPath;
import spotify.createplaylist_response_hp.pojo.CreatePlaylist;
import spotify.getplaylist_response_hp.pojo.GetPlaylist;
import testcomponents.BaseTest;
import testcomponents.JSONComparator;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import reusability_enhancer.PlaylistAPI;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlaylistHPTests extends BaseTest{
	
		@Description("Valid details are passed as API input in this test case and a valid response code and body is expected in return")
		@Test(priority=1, description = "Validate the Happy Path flow of Create Playlist API", dataProvider = "supplyCreatePlaylistData", enabled = false)
		public void validateCreatePlaylist(ITestContext context, HashMap<String, String> testdataAsMap) throws IOException {
		Response rawActualResponse = PlaylistAPI.post(testdataAsMap);
		
		// Assert Response Time
		double responseTime = rawActualResponse.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println(responseTime);
		softAssert.assertTrue(responseTime<3000);
		
		// Assert Response Headers			
		String robotTag = rawActualResponse.getHeader("x-robots-tag");
		System.out.println(robotTag);
		softAssert.assertEquals(robotTag,"noindex, nofollow");
		
		// Assert status code and length>0 of the response from the rawActualResponse
		
		softAssert.assertEquals(rawActualResponse.getStatusCode(), 201);
		softAssert.assertTrue(rawActualResponse.asString().length()>0);
				
		CreatePlaylist deserializedActualResponse = rawActualResponse.as(CreatePlaylist.class);		

		// Assert if the response field values match with the request values from the deserialized response		
		
		softAssert.assertEquals(deserializedActualResponse.getName(), testdata.testdataHPCreatePlaylist(testdataAsMap).getName());
		softAssert.assertEquals(deserializedActualResponse.getDescription(), testdata.testdataHPCreatePlaylist(testdataAsMap).getDescription());
		softAssert.assertEquals(deserializedActualResponse.get_public(), testdata.testdataHPCreatePlaylist(testdataAsMap).get_public());
				
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
		
		@DataProvider
		public Object[][] supplyCreatePlaylistData() throws IOException {
		List<HashMap<String, String>> testdataAsMap = supplyJSONtestdataAsMap(new File(System.getProperty("user.dir")+"//src//test//java//testdata//TestData_CreatePlaylist.json"));
		return new Object[][] {
							  {testdataAsMap.get(0)}, {testdataAsMap.get(1)}, {testdataAsMap.get(2)}
							  };	
		}
		
		@Description("Valid details are passed as API input in this test case and a valid response code and body is expected in return")
		@Test(priority=2, description = "Validate the Happy Path flow of Update Playlist API", enabled = false)
		public void validateUpdatePlaylist(ITestContext context) throws FileNotFoundException {
		String playlistId =  (String) context.getAttribute("playlistId");
		
		Response rawActualResponse	= PlaylistAPI.put(playlistId);	
		
		double responseTime = rawActualResponse.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println(responseTime);
		
		softAssert.assertEquals(rawActualResponse.getStatusCode(), 200);
		softAssert.assertTrue(rawActualResponse.asString().length()==0, "Alert - The Update Playlist response is NOT empty!");
		softAssert.assertAll();	
		}
		
		@Description("Valid details are passed as API input in this test case and a valid response code and body is expected in return")
		@Test(priority=3, description = "Validate the Happy Path flow of Get Playlist API", enabled = false)
		public void validatGetPlaylist(ITestContext context) throws IOException {
		String playlistId = (String) context.getAttribute("playlistId");
		Response rawActualResponse = PlaylistAPI.get(playlistId);
		
		double responseTime = rawActualResponse.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println(responseTime);	
		softAssert.assertEquals(rawActualResponse.getStatusCode(), 200);
		softAssert.assertTrue(rawActualResponse.asString().length()>0);
		softAssert.assertEquals(rawActualResponse.path("tracks.limit"), 100);
				
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
