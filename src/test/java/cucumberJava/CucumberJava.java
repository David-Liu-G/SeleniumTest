package cucumberJava;

import cucumber.annotation.en.And;
import cucumber.annotation.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CucumberJava {
    private WebDriver driver = null;
    private WebDriverWait wait = null;

    public CucumberJava() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
    }

    @Given("I have open the browser")
    public void openBrowser() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @And("I have open the Amazon website")
    public void goToAmazon() {
        driver.navigate().to("https://www.amazon.ca/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#twotabsearchtextbox")));
    }
}
