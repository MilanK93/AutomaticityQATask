package testcases;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.LoginPage;

public class FlowOfShopping extends BaseTest {

	private WebDriverWait wait;

	@BeforeMethod
	public void setUp() {
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		navigateToLoginPage();
	}

	private void clickElement(By locator) {
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		element.click();
	}

	private void enterText(By locator, String text) {
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		element.clear();
		element.sendKeys(text);
	}

	@Test
	public void loginAndAddToCart() throws InterruptedException {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.enterEmail("test1@test.com");
		loginPage.enterPassword("Test1234");
		loginPage.clickLogin();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		});

		addProductToCart();

		verifyCartCount();
		clickElement(By.xpath("//*[@id=\"app\"]/div/div[3]/div/div/div/div/button"));
		wait.until(ExpectedConditions.urlContains("checkout"));

		fillShippingDetails();

		fillPaymentDetails();

		clickElement(By.xpath("//*[@id=\"app\"]/div[2]/div[2]/div[1]/button[1]"));

		wait.until(ExpectedConditions.urlContains("/dashboard"));
		verifyCartIsEmpty();

		logout();
	}

	private void addProductToCart() {
		WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//*[@id=\"app\"]/div/div[2]/div[1]/div[2]/div/div[3]/div/div[9]/div/div[2]/button")));
		addToCartButton.click();
		WebElement cartCountElement = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div/div[1]/div/div[2]/div/div/div/span/button")));
		wait.until(driver -> {
			String cartCountText = cartCountElement.getText();
			return !cartCountText.equals("0") && !cartCountText.isEmpty();
		});
		Assert.assertTrue(Integer.parseInt(cartCountElement.getText()) > 0, "Cart is empty or item was not added.");
		WebElement cartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id=\"app\"]/div/div[1]/div/div[2]/div/div/div/span/button")));
		cartButton.click();
	}

	private void verifyCartCount() {
		WebElement cartCountElement = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div/div[1]/div/div[2]/div/div/div/span/button")));
		String cartCount = cartCountElement.getText();
		Assert.assertTrue(Integer.parseInt(cartCount) > 0, "Cart is empty or item was not added.");
	}

	private void verifyCartIsEmpty() {
		WebElement cartItemCount = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id=\"app\"]/div/div[1]/div/div[2]/div/div/div/span/button")));
		Assert.assertEquals(cartItemCount.getText(), "0", "Cart is not empty after order!");
	}

	private void fillShippingDetails() {

		clickElement(By.xpath("//*[@id=\"app\"]/div[2]/div[1]/div/div/div/div[2]/button"));
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//*[@id=\"app\"]/div[2]/div[1]/div/div/div[1]/div/form/div[2]/button[1]/span")));
		clickElement(By.xpath("//*[@id=\"app\"]/div[2]/div[1]/div/div/div[1]/div/form/div[2]/button[1]/span"));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"first_name\"]")));

		enterText(By.xpath("//*[@id=\"first_name\"]"), "Test");
		enterText(By.xpath("//*[@id=\"last_name\"]"), "User");
		enterText(By.xpath("//*[@id=\"email\"]"), "user@test.com");
		enterText(By.xpath("//*[@id=\"phone_number\"]"), "1234567890");
		enterText(By.xpath("//*[@id=\"street_and_number\"]"), "New Street 10");
		enterText(By.xpath("//*[@id=\"city\"]"), "New York");
		enterText(By.xpath("//*[@id=\"postal_code\"]"), "10001");
		enterText(By.xpath("//*[@id=\"country\"]"), "Usa");

		clickElement(By.xpath("//*[@id=\"app\"]/div[2]/div[1]/div/div/div[1]/div/form/div[2]/button[1]/span"));

		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//*[@id=\"app\"]/div[2]/div[1]/div/div/div[1]/div/form/div[2]/button[2]/span")));
		clickElement(By.xpath("//*[@id=\"app\"]/div[2]/div[1]/div/div/div[1]/div/form/div[2]/button[2]/span"));
	}

	private void fillPaymentDetails() {
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//*[@id=\"app\"]/div[2]/div[1]/div/div/div[1]/div/form/div[2]/button[1]")));
		clickElement(By.xpath("//*[@id=\"app\"]/div[2]/div[1]/div/div/div[1]/div/form/div[2]/button[1]"));

		enterText(By.id("cardholder"), "Test User");
		WebElement cardTypeDropdown = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"card_type\"]/span")));
		cardTypeDropdown.click();
		WebElement visaOption = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div/ul/li[2]")));
		visaOption.click();
		enterText(By.id("card_number"), "1234567891011");
		enterText(By.id("cvv"), "123");
		WebElement monthDropdown = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"card_expiration_month\"]/span")));
		monthDropdown.click();
		WebElement decemberOption = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div/ul/li[10]")));
		decemberOption.click();
		WebElement yearDropdown = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"card_expiration_year\"]/span")));
		yearDropdown.click();
		WebElement yearOption = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div/ul/li[4]")));
		yearOption.click();

		clickElement(By.xpath("//*[@id=\"app\"]/div[2]/div[1]/div/div/div[1]/div/form/div[2]/button[1]"));

		clickElement(By.xpath("//*[@id=\"app\"]/div[2]/div[1]/div/div/div[1]/div/form/div[2]/button[2]"));
	}

	private void logout() {

		clickElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div/div[2]/div/div/div/div/div/span/button"));
		clickElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div/div[2]/div/div/div/div/div[3]/div/button"));

		wait.until(ExpectedConditions.urlToBe("https://automaticityacademy.ngrok.app/"));
		System.out.println("User logged out successfully.");
	}

}


