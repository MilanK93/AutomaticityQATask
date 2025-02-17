package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RegisterPage {
    private WebDriver driver;

    private By usernameField = By.id("username");
    private By emailField = By.id("email");
    private By passwordField = By.id("password");
    private By registerButton = By.xpath("//span[contains(text(),'Register')]");
    
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickRegister() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
    }
    
    private By userNameErrorLocator = By.xpath("//p[contains(text(),'The username has already been taken.')]");
    private By emailErrorLocator = By.xpath("//p[contains(text(),'The email has already been taken.')]");
    private By invalidEmailErrorLocator = By.xpath("//p[contains(text(),'The email field format is invalid')]");
    private By requiredFieldErrorLocator = By.xpath("//p[contains(text(),'This field is required')]");
    private By passwordErrorLocator = By.xpath("//p[contains(text(),'The password field is required')]");
    private By usernameErrorLocator = By.xpath("//p[contains(text(),'The username field is required')]");
    private By emailRequiredErrorLocator = By.xpath("//p[contains(text(),'The email field is required')]");

    public String getEmailErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emailErrorLocator)).getText();
    }

    public String getInvalidEmailErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(invalidEmailErrorLocator)).getText();
    }

    public String getUserNameErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(userNameErrorLocator)).getText();
    }

    public String getRequiredFieldErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(requiredFieldErrorLocator)).getText();
    }

    public String getPasswordErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordErrorLocator)).getText();
    }
    
    public String getUsernameErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameErrorLocator)).getText();
    }
    
    public String getEmailRequiredErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emailRequiredErrorLocator)).getText();
    }
}
