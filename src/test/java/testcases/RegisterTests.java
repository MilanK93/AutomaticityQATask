package testcases;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.RegisterPage;
import java.time.Duration;

public class RegisterTests extends BaseTest {

    @Test
    public void registerWithValidDetails() {
        navigateToRegisterPage();
        RegisterPage registerPage = new RegisterPage(driver);
       
        registerPage.enterUsername(dynamicUsername);
        registerPage.enterEmail(dynamicEmail);
        registerPage.enterPassword("Password123");
        registerPage.clickRegister();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/dashboard"));

        Assert.assertEquals(driver.getCurrentUrl(), "https://automaticityacademy.ngrok.app/dashboard");
    }
    
    @Test
    public void registerWithAlreadyRegisteredEmail() {
        navigateToRegisterPage();
        
        RegisterPage registerPage = new RegisterPage(driver);

        registerPage.enterUsername(dynamicUsername);
        registerPage.enterEmail("Test@007.com"); 
        registerPage.enterPassword("Password123");
        registerPage.clickRegister();

        Assert.assertEquals(registerPage.getEmailErrorMessage(), "The email has already been taken.");
    }

    @Test
    public void registerWithInvalidEmailFormat() {
        navigateToRegisterPage();
        RegisterPage registerPage = new RegisterPage(driver);

        registerPage.enterUsername(dynamicUsername);
        registerPage.enterEmail("Test007.com");
        registerPage.enterPassword("Password123");
        registerPage.clickRegister();

        Assert.assertEquals(registerPage.getInvalidEmailErrorMessage(), "The email field format is invalid.");
    }

    @Test
    public void registerWithTakenUsername() {
        navigateToRegisterPage();
        RegisterPage registerPage = new RegisterPage(driver);

        registerPage.enterUsername("Mr007");
        registerPage.enterEmail("test@123.com");
        registerPage.enterPassword("Password123");
        registerPage.clickRegister();

        Assert.assertEquals(registerPage.getUserNameErrorMessage(), "The username has already been taken.");
    }

    @Test
    public void registerWithMissingFields() {
        navigateToRegisterPage();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickRegister(); 

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(registerPage.getUsernameErrorMessage(), "The username field is required.");
        softAssert.assertEquals(registerPage.getEmailRequiredErrorMessage(), "The email field is required.");
        softAssert.assertEquals(registerPage.getPasswordErrorMessage(), "The password field is required.");
        softAssert.assertAll(); 
    }

}
