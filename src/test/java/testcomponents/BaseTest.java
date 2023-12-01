package testcomponents;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseTest {
	
		static String access_token = "BQBrzx8L0YDHytTxUwunWUmrSJV2fVqrAUfuBNiNS4Zi6wILeTps3vVrRjBUdvGJuUycZSQrKOICAdpJxvUrudk1RH2cfXEM01aSOZ6Wo2JPGf1XwYNokmNPozZrDxW1sMIW8RDUs12pCMoyQ4VPyUNY_wOAu0PCeShcgpgjqBVTBYbaoO9US-FIsUBB8qXHEg_CplwgSJ7hPzlWg6PJTWUocmpX2W1HgioDsBVCpFioMYHXHit0OmpVIThvKaHpRkHEnXLWCy5XfeWw";
		public TestDataBuilder testdata = new TestDataBuilder();	
		public SoftAssert softAssert = new SoftAssert();
		
		public static RequestSpecification getRequestSpec() throws FileNotFoundException {
		PrintStream	log = new PrintStream(new FileOutputStream("Complete_Logs.txt", true));
		
		return new RequestSpecBuilder().
				   setBaseUri("https://api.spotify.com").
				   setBasePath("/v1").
				   setContentType(ContentType.JSON).
				   addHeader("Authorization", "Bearer "+access_token).
				   log(LogDetail.ALL).
				   addFilter(RequestLoggingFilter.logRequestTo(log)).
				   addFilter(ResponseLoggingFilter.logResponseTo(log)).
				   addFilter(new AllureRestAssured()).
				   build();			
		} 
		
		public static ResponseSpecification getResponseSpec() {
		return new ResponseSpecBuilder().				   
				   log(LogDetail.ALL).
				   build();			
		}	
	
		@BeforeMethod(alwaysRun=true)
		public void setUpFunctions() {	
				
		}
		
		@AfterMethod(alwaysRun=true)
		public void tearDownFunctions() {
				
		}
		
		

}
