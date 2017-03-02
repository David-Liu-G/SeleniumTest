package AmazonTest;

import cucumber.annotation.en.And;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class AmazonTest {
    private WebDriver driver = null;
    private WebDriverWait wait = null;

    @Given("^I have open the browser$")
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
    }

    @And("^I have open the Amazon website$")
    public void goToAmazon() {
        driver.navigate().to("https://www.amazon.ca/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#twotabsearchtextbox")));
    }

    @And("^I have put the book \"(.*)\" into the shopping cart$")
    public void putBookInShoppingCart(String bookName) {
        buyBook(bookName);
    }

    @When("^I go to the shopping cart page$")
    public void goToShoppingCart() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#nav-cart")));
        driver.findElement(By.cssSelector("#nav-cart")).click();
    }

    @And("^I remove the book \"(.*)\" from the shopping cart$")
    public void removeTheBook(String bookName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sc-active-cart")));
        WebElement cart = driver.findElement(By.cssSelector("#sc-active-cart"));
        List<WebElement> items = cart.findElements(By.cssSelector("#activeCartViewForm > div.sc-list-body.sc-java-remote-feature > div"));
        for (WebElement item : items) {
            if (item.findElement(By.cssSelector("div.sc-list-item-content > div > div.a-column.a-span8 > div > div > div.a-fixed-left-grid-col.a-col-right > ul > li:nth-child(1) > span > a > span")).getText().equals(bookName)) {
                wait.until(ExpectedConditions.elementToBeClickable(item.findElement(By.cssSelector("div.sc-list-item-content > div > div.a-column.a-span8 > div > div > div.a-fixed-left-grid-col.a-col-right > div > span.a-size-small.sc-action-delete > span > input[type=\"submit\"]"))));
                item.findElement(By.cssSelector("div.sc-list-item-content > div > div.a-column.a-span8 > div > div > div.a-fixed-left-grid-col.a-col-right > div > span.a-size-small.sc-action-delete > span > input[type=\"submit\"]")).click();
                wait.until(ExpectedConditions.attributeContains(item, "data-removed", "true"));
            }
        }
    }

    @And("^I remove the book \"(.*)\" from the shopping cart by save for later feature$")
    public void removeTheBookBySaveForLater(String bookName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sc-active-cart")));
        WebElement cart = driver.findElement(By.cssSelector("#sc-active-cart"));
        List<WebElement> items = cart.findElements(By.cssSelector("#activeCartViewForm > div.sc-list-body.sc-java-remote-feature > div"));
        for (WebElement item : items) {
            if (item.findElement(By.cssSelector("div.sc-list-item-content > div > div.a-column.a-span8 > div > div > div.a-fixed-left-grid-col.a-col-right > ul > li:nth-child(1) > span > a > span")).getText().equals(bookName)) {
                wait.until(ExpectedConditions.elementToBeClickable(item.findElement(By.cssSelector("div.sc-list-item-content > div > div.a-column.a-span8 > div > div > div.a-fixed-left-grid-col.a-col-right > div > span.a-size-small.sc-action-delete > span > input[type=\"submit\"]"))));
                item.findElement(By.cssSelector("div.sc-list-item-content > div > div.a-column.a-span8 > div > div > div.a-fixed-left-grid-col.a-col-right > div > span.a-size-small.sc-action-save-for-later > span > input[type=\"submit\"]")).click();
                wait.until(ExpectedConditions.attributeContains(item, "data-removed", "true"));
            }
        }
    }

    @Then("^The book \"(.*)\" should be removed from the shopping cart$")
    public void checkRemovedBook(String bookName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#activeCartViewForm > div.sc-list-body.sc-java-remote-feature")));
        WebElement cart = driver.findElement(By.cssSelector("#activeCartViewForm > div.sc-list-body.sc-java-remote-feature"));
        List<WebElement> list = cart.findElements(By.className("sc-product-title"));
        assertTrue(list.size() == 1);
        assertTrue(!list.get(0).getText().equals(bookName));
    }

    @And("^There should be one item \"(.*)\" left in the shopping cart$")
    public void checkExistBook(String bookName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#activeCartViewForm > div.sc-list-body.sc-java-remote-feature")));
        WebElement cart = driver.findElement(By.cssSelector("#activeCartViewForm > div.sc-list-body.sc-java-remote-feature"));
        List<WebElement> list = cart.findElements(By.className("sc-product-title"));
        assertTrue(list.size() == 1);
        assertTrue(list.get(0).getText().equals(bookName));
        driver.close();
    }

    @Then("^Nothing can be removed from the shopping cart$")
    public void checkEmptyCart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sc-active-cart > div > h1")));
        WebElement cartTitle = driver.findElement(By.cssSelector("#sc-active-cart > div > h1"));
        assertTrue(cartTitle.getText().equals("Your Shopping Cart is empty."));
        driver.close();
    }

    private void buyBook(String bookName) {
        driver.findElement(By.cssSelector("#twotabsearchtextbox")).sendKeys(bookName);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#nav-search > form > div.nav-right > div > input")));
        driver.findElement(By.cssSelector("#nav-search > form > div.nav-right > div > input")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#result_0 > div > div > div > div.a-fixed-left-grid-col.a-col-right > div.a-row.a-spacing-small > div:nth-child(1) > a")));
        driver.findElement(By.cssSelector("#result_0 > div > div > div > div.a-fixed-left-grid-col.a-col-right > div.a-row.a-spacing-small > div:nth-child(1) > a")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#add-to-cart-button")));
        driver.findElement(By.cssSelector("#add-to-cart-button")).click();
    }
}
