package apiTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class LoginApiTests {

    private static String token;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://automaticityacademy.ngrok.app/api/v1/auth";
    }

    private Response sendLoginRequest(String email, String password) {
        return given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}")
        .when()
            .post("/login")
        .then()
            .extract().response();
    }

    @Test
    public void loginWithValidCredentials() {
        Response response = sendLoginRequest("Test@007.com", "Test007");
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for valid login.");
        token = response.jsonPath().getString("auth.token");
        Assert.assertNotNull(token, "Token should be returned.");
    }

    @Test
    public void loginWithInvalidPassword() {
        Response response = sendLoginRequest("Test@007.com", "abc123");
        Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 for invalid password.");
        String errorMessage = response.jsonPath().getString("message");
        if (errorMessage == null) { 
            errorMessage = response.jsonPath().getString("error"); 
        }
        Assert.assertNotNull(errorMessage, "Error message should not be null.");
        Assert.assertEquals(errorMessage, "Unauthorized", "Expected 'Unauthorized' error message.");
    }

}
