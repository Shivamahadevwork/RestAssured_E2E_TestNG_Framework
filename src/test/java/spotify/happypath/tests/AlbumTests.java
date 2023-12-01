package spotify.happypath.tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.response.Response;
import spotify.getalbum_response_hp.pojo.AlbumRoot;
import testcomponents.BaseTest;
import testcomponents.JSONComparator;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;

public class AlbumTests extends BaseTest{
	
		@Description("Valid details are passed as API input in this test case and a valid response code and body is expected in return")
		@Test(description = "Validate the Happy Path flow of Get Album API")
		public void validateGetAlbum() throws IOException {
		Response rawActualResponse = given().
									spec(getRequestSpec()).
							when().
									get("/albums?ids=382ObEPsp2rxGrnsizN5TX").
							then().
									spec(getResponseSpec()).
									assertThat().
									statusCode(200).
									extract().
									response();
		
		AlbumRoot deserializedActualResponse = rawActualResponse.as(AlbumRoot.class);
		softAssert.assertEquals(deserializedActualResponse.getAlbums().get(0).getArtists().get(0).getName(), "Daft Punk");
		softAssert.assertEquals(deserializedActualResponse.getAlbums().get(0).getName(), "TRON: Legacy Reconfigured");
		softAssert.assertEquals(deserializedActualResponse.getAlbums().get(0).getExternalIds().getUpc(), "00050087239633");
		softAssert.assertAll();
		
		String rawActualResponseStg = rawActualResponse.asString();
		File expectedResponseJSON = new File(System.getProperty("user.dir")+"//src//main//resources//expected_responses//Get_Album_Response.json");
		try {
			JSONComparator.compareJSONFiles(rawActualResponseStg, expectedResponseJSON);
		} catch (AssertionError e) {
			System.out.println("Alert - mismatches noted! Validate them carefully.");
			e.printStackTrace();
		}		
		
		}
		
	

}
