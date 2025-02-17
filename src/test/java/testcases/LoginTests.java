package testcases;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class LoginTests extends BaseTest {
	private LoginPage loginPage;
	private WebDriverWait wait;

	@BeforeMethod
	public void openLoginPage() {
		navigateToLoginPage();
		loginPage = new LoginPage(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}

	@Test
	public void loginWithValidCredentials() {
		loginPage.enterEmail("Test@007.com");
		loginPage.enterPassword("Test007");
		loginPage.clickLogin();

		wait.until(ExpectedConditions.urlContains("/dashboard"));

		Assert.assertEquals(driver.getCurrentUrl(), "https://automaticityacademy.ngrok.app/dashboard",
				"Login failed: URL mismatch after login.");
	}

	@Test
	public void loginWithInvalidPassword() {
		loginPage.enterEmail("Test@007.com");
		loginPage.enterPassword("abc123");
		loginPage.clickLogin();

		waitUntilVisible(loginPage.getInvalidCredentialsErrorLocator());

		Assert.assertEquals(loginPage.getInvalidCredentialsErrorMessage(),
				"The email address or password you entered is invalid",
				"Unexpected error message for invalid password.");
	}

	@Test
	public void loginWithInvalidEmailFormat() {
		loginPage.enterEmail("Test007.com");
		loginPage.enterPassword("Test007");
		loginPage.clickLogin();

		waitUntilVisible(loginPage.getEmailErrorLocator());

		Assert.assertEquals(loginPage.getEmailErrorMessage(), "The email field must be a valid email address.",
				"Incorrect error message for invalid email format.");
	}

	@Test
	public void loginWithEmptyFields() {
		loginPage.clickLogin();

		Assert.assertEquals(loginPage.getEmailErrorMessage(), "The email field is required.",
				"Email error message is incorrect.");
		Assert.assertEquals(loginPage.getPasswordErrorMessage(), "The password field is required.",
				"Password error message is incorrect.");
	}

	@Test
	public void loginWithOnlyEmail() {
		loginPage.enterEmail("Test@007.com");
		loginPage.clickLogin();

		Assert.assertEquals(loginPage.getPasswordErrorMessage(), "The password field is required.",
				"Password error message is incorrect when missing.");
	}

	@Test
	public void loginWithOnlyPassword() {
		loginPage.enterPassword("Test007");
		loginPage.clickLogin();

		Assert.assertEquals(loginPage.getEmailErrorMessage(), "The email field is required.",
				"Email error message is incorrect when missing.");
	}

	private void waitUntilVisible(By locator) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
}
