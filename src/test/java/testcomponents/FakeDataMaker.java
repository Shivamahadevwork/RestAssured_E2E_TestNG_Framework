package testcomponents;

import com.github.javafaker.Faker;

public class FakeDataMaker {
	
		public static String sendFakeName() {
		Faker faker = new Faker();	
		return faker.regexify("[a-zA-Z]{10}");	
		}

}
