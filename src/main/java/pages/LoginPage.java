package pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By emailField = By.id("email");
    private By passwordField = By.id("password");
    private By loginButton = By.xpath("//span[contains(text(),'Sign in')]");

    private By emailErrorLocator = By.xpath("//p[contains(text(),'email')]");
    private By passwordErrorLocator = By.xpath("//p[contains(text(),'password')]");
    private By invalidCredentialsErrorLocator = By.xpath("//p[contains(text(),'The email address or password you entered is invalid')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public boolean isLoginSuccessful() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div/div[1]/div/div[1]/div/a")));  
            return true; 
        } catch (TimeoutException e) {
            return false; 
        }
    }

    private String getTextFromElement(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
        } catch (TimeoutException e) {
            System.out.println("Warning: Element not found: " + locator);
            return null;
        }
    }

    public String getEmailErrorMessage() {
        return getTextFromElement(emailErrorLocator);
    }

    public String getPasswordErrorMessage() {
        return getTextFromElement(passwordErrorLocator);
    }

    public String getInvalidCredentialsErrorMessage() {
        return getTextFromElement(invalidCredentialsErrorLocator);
    }

    public By getEmailErrorLocator() {
        return emailErrorLocator;
    }

    public By getPasswordErrorLocator() {
        return passwordErrorLocator;
    }

    public By getInvalidCredentialsErrorLocator() {
        return invalidCredentialsErrorLocator;
    }
}

