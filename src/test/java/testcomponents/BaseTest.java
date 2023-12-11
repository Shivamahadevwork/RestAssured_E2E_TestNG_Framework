package testcomponents;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

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
	
	
		public static TestDataBuilder testdata = new TestDataBuilder();	
		public SoftAssert softAssert = new SoftAssert();
		
		public static RequestSpecification getTokenRequestSpec() {
		return new RequestSpecBuilder().
				   setBaseUri(Routes.TokenBaseURI).
				   setContentType(ContentType.URLENC).
				   addFilter(new AllureRestAssured()).				
				   build();			
		}
		
		public static RequestSpecification getRequestSpec() throws FileNotFoundException {
		String log_FileName = "Complete_Logs.txt";	
		File logFile = new File(log_FileName);
		if(logFile.exists()) {
			logFile.delete();
			System.out.println("Existing log file deleted successfully!");
		}			
		
		
		PrintStream	log = new PrintStream(new FileOutputStream("Complete_Logs.txt", true));
		return new RequestSpecBuilder().
				   setBaseUri(Routes.BaseURI).
				   setBasePath(Routes.BasePath).
				   setContentType(ContentType.JSON).
				   addHeader("Authorization", "Bearer "+TokenManager.supplyToken()).
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
		
		public List<HashMap<String, String>> supplyJSONtestdataAsMap(File filePath) throws IOException {
		//	Convert JSON to String
		String testdataAsString = FileUtils.readFileToString(filePath, StandardCharsets.UTF_8);
		// Convert String to Map
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> testdataAsMap = mapper.readValue(testdataAsString, new TypeReference<List<HashMap<String, String>>>(){});
		return testdataAsMap;
		}
		
		public static String loadGlobalData(String key) throws FileNotFoundException {
		Properties prop = new Properties();
		BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"//src//main//resources//properties//GlobalData.properties"));	
		try {
			prop.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop.getProperty(key);
		}
	
	/*	@BeforeMethod(alwaysRun=true)
		public void setUpFunctions() {	
				
		}
		
		@AfterMethod(alwaysRun=true)
		public void tearDownFunctions() {
				
		} */
		
		

}
