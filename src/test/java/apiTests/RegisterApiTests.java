package apiTests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import org.testng.asserts.SoftAssert;
import base.BaseTest;

public class RegisterApiTests extends BaseTest{

	private static String authToken;

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://automaticityacademy.ngrok.app/api/v1/auth";
	}

	private Response sendRegisterRequest(String username, String email, String password) {
		return given()
				.contentType(ContentType.JSON).accept(ContentType.JSON).body("{\"username\": \"" + username
						+ "\", \"email\": \"" + email + "\", \"password\": \"" + password + "\"}")
				.when().post("/register").then().extract().response();
	}

	@Test
	public void registerWithValidDetails() {
		Response response = sendRegisterRequest(dynamicUsername, dynamicEmail, "ValidPass123");

		Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for successful registration.");

		String message = response.jsonPath().getString("message");
		String email = response.jsonPath().getString("user.email");
		String username = response.jsonPath().getString("user.username");
		authToken = response.jsonPath().getString("auth.token");

		Assert.assertEquals(message, "User created successfully");
		Assert.assertEquals(email, dynamicEmail);
		Assert.assertEquals(username, dynamicUsername);
		Assert.assertNotNull(authToken, "Token should be returned upon successful registration.");
	}

	@Test
	public void registerWithEmptyFields() {
		Response response = sendRegisterRequest("", "", "");

		Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 422 for missing fields.");

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(response.jsonPath().getString("errors.username[0]"), "The username field is required.");
		softAssert.assertEquals(response.jsonPath().getString("errors.email[0]"), "The email field is required.");
		softAssert.assertEquals(response.jsonPath().getString("errors.password[0]"), "The password field is required.");
		softAssert.assertAll();
	}

	@Test
	public void registerWithExistingEmail() {
		Response response = sendRegisterRequest(dynamicUsername, "Test@007.com", "Test007");

		Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 422 for duplicate email.");
		Assert.assertTrue(response.getBody().asString().contains("email"), "Expected 'email' error in response.");
	}

	@Test
	public void registerWithExistingUsername() {
		Response response = sendRegisterRequest("Mr007", dynamicEmail, "ValidPass123");

		Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 422 for duplicate username.");
		Assert.assertTrue(response.getBody().asString().contains("username"), "Expected 'username' error in response.");
	}

	@Test
	public void registerWithInvalidEmailFormat() {
		Response response = sendRegisterRequest(dynamicUsername, "test007.com", "ValidPass123");

		Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 422 for invalid email.");
		Assert.assertTrue(response.getBody().asString().contains("email"), "Expected 'email' error in response.");
	}

	@Test
	public void resgisterWithEmptyRequestBody() {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body("{}").when()
				.post("/login").then().extract().response();

		System.out.println("Response Body: " + response.getBody().asString());

		Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 422 for empty request body.");
		Assert.assertTrue(response.getBody().asString().contains("email"), "Expected 'email' validation error.");
		Assert.assertTrue(response.getBody().asString().contains("password"), "Expected 'password' validation error.");
	}

}
