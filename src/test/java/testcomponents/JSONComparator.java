package testcomponents;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;

public class JSONComparator {
	
		public static void compareJSONFiles(String actualResponseStg, File filePath) throws IOException {
		//Convert JSON to String
		String expectedResponseStg = FileUtils.readFileToString(filePath, StandardCharsets.UTF_8); 
		ObjectMapper mapper = new ObjectMapper();
		assertJsonEquals(mapper.readTree(actualResponseStg), mapper.readTree(expectedResponseStg));			
		}

}
