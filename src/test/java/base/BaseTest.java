package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.github.javafaker.Faker;

public class BaseTest {
    protected WebDriver driver;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/Milan/eclipse-workspace/SeleniumDemoProject/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public void navigateToLoginPage() {
        driver.get("https://automaticityacademy.ngrok.app/login");
    }

    public void navigateToRegisterPage() {
        driver.get("https://automaticityacademy.ngrok.app/register");
    }
    
    public void navigateToShoppingPage() {
        driver.get("https://automaticityacademy.ngrok.app/dashboard");
    }
    
	Faker faker = new Faker();
	protected String dynamicUsername = faker.name().username();
	protected String dynamicEmail = faker.internet().emailAddress();
   
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
