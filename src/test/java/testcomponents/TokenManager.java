package testcomponents;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class TokenManager{
	
		private static String access_token;
		private static Instant expiry_time;
	
		public synchronized static String supplyToken() {
		if(access_token==null || Instant.now().isAfter(expiry_time)) {
		System.out.println("Generating a new token!");
		Response rawActualResponse = createToken();
		access_token = rawActualResponse.path("access_token");
		expiry_time = Instant.now().plusSeconds(3300);			
		} else {
			System.out.println("Existing Token is alive and valid");
		}		
		return access_token;	
		}
		
		public static Response createToken() {
		HashMap<String, String> tokenHeaders = new HashMap<String, String>();
		tokenHeaders.put("client_id", "a06ff974a6364864a5cd4bbda8b9ab22");
		tokenHeaders.put("client_secret", "4f3df5f02cfa44cf9edae16b3c0ac019");
		tokenHeaders.put("grant_type", "refresh_token");
		tokenHeaders.put("refresh_token", "AQD1J6cxKVYxLP-JMy7hdmiUJPCFmzQBKzVaJdNVUbRXxR5VIqsvKwqlxcAa--xNRQQF3nZXlVo5rOEC0_yLLRdUvmcp8x3XkO1-52WJX5BVCoyscwG2rVWQXipbm6A6-GY");
			
		Response rawActualResponse = given().
									 spec(BaseTest.getTokenRequestSpec()).
									 formParams(tokenHeaders).
									 when().
									 post(Routes.TokenResourcePath).
									 then().
									 spec(BaseTest.getResponseSpec()).
									 extract().
									 response();
		
		int responseCode = rawActualResponse.getStatusCode();
		if(responseCode!=200) {
			throw new RuntimeException("Token Generation Failed");
			
		} else {
		return rawActualResponse;
		}
		}

}
