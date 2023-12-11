package api_level_reusability_enhancer;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.util.HashMap;

import io.restassured.response.Response;
import testcomponents.BaseTest;

public class PlaylistAPI extends BaseTest{
	
		public static Response post(HashMap<String, String> testdataAsMap) throws FileNotFoundException {
		return given().
					  spec(getRequestSpec()).
					  body(testdata.testdataHPCreatePlaylist(testdataAsMap)).		
					  when().
					  post("/users/"+loadGlobalData("userId")+"/playlists").		
					  then().
					  spec(getResponseSpec()).
					  extract().
					  response();				
		}
	
		public static Response put(String playlistId) throws FileNotFoundException {
		return given().
				  spec(getRequestSpec()).
				  body(testdata.testdataHPUpdatePlaylist()).								  
				  when().put("/playlists/"+playlistId).								  
				  then().spec(getResponseSpec()).
				  extract().
				  response();			
		}	
		
		public static Response get(String playlistId) throws FileNotFoundException {
		return given().
				  spec(getRequestSpec()).
				  when().
				  get("/playlists/"+playlistId).
				  then().
				  spec(getResponseSpec()).
				  extract().
				  response();			
		}
	

}
