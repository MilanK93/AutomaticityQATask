package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {
    WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public void addItemToCart() throws InterruptedException {
    	
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    	wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));

    	List<WebElement> overlays = driver.findElements(By.cssSelector("div.overlay-class-or-selector"));
    	if (!overlays.isEmpty()) {
    	    WebElement overlay = overlays.get(0);
    	    ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none';", overlay);
    	}

    	WebElement addToCartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class, 'p-button')]")));

    	wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));

    	Actions actions = new Actions(driver);
    	actions.moveToElement(addToCartButton).click().perform();

    	System.out.println("Cart Item Count: " + getCartCount());

    }
   
    public int getCartCount() {
        String count = driver.findElement(By.id("cart-count")).getText();
        return Integer.parseInt(count);
    }

    public void navigateToCart() {
        driver.findElement(By.id("cart-icon")).click();
    }
}
