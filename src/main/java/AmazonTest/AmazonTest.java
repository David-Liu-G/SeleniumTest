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
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe"); //find chrome driver
        driver = new ChromeDriver();//open web browser
        wait = new WebDriverWait(driver, 5);//create a web driver wait
    }

    @And("^I have open the Amazon website$")
    public void goToAmazon() {
        driver.navigate().to("https://www.amazon.ca/");//go to the amazon website
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#twotabsearchtextbox")));//wait until the visibility of the search bar
    }

    @And("^I have put the book \"(.*)\" into the shopping cart$")
    public void putBookInShoppingCart(String bookName) {
        buyBook(bookName);
    }

    @When("^I go to the shopping cart page$")
    public void goToShoppingCart() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#nav-cart")));//wait until the go to shopping cart button is clickable
        driver.findElement(By.cssSelector("#nav-cart")).click();//go to the shopping cart page
    }

    @And("^I remove the book \"(.*)\" from the shopping cart$")
    public void removeTheBook(String bookName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sc-active-cart")));//wait until the shopping cart page is fully loaded
        WebElement cart = driver.findElement(By.cssSelector("#sc-active-cart"));//find the container of all the items in the shopping cart
        List<WebElement> items = cart.findElements(By.cssSelector("#activeCartViewForm > div.sc-list-body.sc-java-remote-feature > div"));//find all the items inside the container
        for (WebElement item : items) {//for each item in the shopping cart
            if (item.findElement(By.cssSelector("div.sc-list-item-content > div > div.a-column.a-span8 > div > div > div.a-fixed-left-grid-col.a-col-right > ul > li:nth-child(1) > span > a > span")).getText().equals(bookName)) {//if the item's product title match the function argument
                wait.until(ExpectedConditions.elementToBeClickable(item.findElement(By.cssSelector("div.sc-list-item-content > div > div.a-column.a-span8 > div > div > div.a-fixed-left-grid-col.a-col-right > div > span.a-size-small.sc-action-delete > span > input[type=\"submit\"]"))));//wait until the delete button is clickable
                item.findElement(By.cssSelector("div.sc-list-item-content > div > div.a-column.a-span8 > div > div > div.a-fixed-left-grid-col.a-col-right > div > span.a-size-small.sc-action-delete > span > input[type=\"submit\"]")).click();//click the delete button
                wait.until(ExpectedConditions.attributeContains(item, "data-removed", "true"));//wait until the item is truly deleted
            }
        }
    }

    @And("^I remove the book \"(.*)\" from the shopping cart by save for later feature$")
    public void removeTheBookBySaveForLater(String bookName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sc-active-cart")));//wait until the shopping cart page is fully loaded
        WebElement cart = driver.findElement(By.cssSelector("#sc-active-cart"));//find the container of all the items in the shopping cart
        List<WebElement> items = cart.findElements(By.cssSelector("#activeCartViewForm > div.sc-list-body.sc-java-remote-feature > div"));//find all the items inside the container
        for (WebElement item : items) {//for each item in the shopping cart
            if (item.findElement(By.cssSelector("div.sc-list-item-content > div > div.a-column.a-span8 > div > div > div.a-fixed-left-grid-col.a-col-right > ul > li:nth-child(1) > span > a > span")).getText().equals(bookName)) {//if the item's product title match the function argument
                wait.until(ExpectedConditions.elementToBeClickable(item.findElement(By.cssSelector("div.sc-list-item-content > div > div.a-column.a-span8 > div > div > div.a-fixed-left-grid-col.a-col-right > div > span.a-size-small.sc-action-delete > span > input[type=\"submit\"]"))));//wait until the save for later button is clickable
                item.findElement(By.cssSelector("div.sc-list-item-content > div > div.a-column.a-span8 > div > div > div.a-fixed-left-grid-col.a-col-right > div > span.a-size-small.sc-action-save-for-later > span > input[type=\"submit\"]")).click();//click the save for later button
                wait.until(ExpectedConditions.attributeContains(item, "data-removed", "true"));//wait until the item is truly deleted
            }
        }
    }

    @Then("^The book \"(.*)\" should be removed from the shopping cart$")
    public void checkRemovedBook(String bookName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#activeCartViewForm > div.sc-list-body.sc-java-remote-feature")));//wait until the shopping cart element is visible
        WebElement cart = driver.findElement(By.cssSelector("#activeCartViewForm > div.sc-list-body.sc-java-remote-feature"));//get the shopping cart web element
        List<WebElement> list = cart.findElements(By.className("sc-product-title"));//get all the items in the shopping cart
        assertTrue(list.size() == 1);//check if the number of the items in the shopping cart is one
        assertTrue(!list.get(0).getText().equals(bookName));//check the remaining item's product title is not the removed item
    }

    @And("^There should be one item \"(.*)\" left in the shopping cart$")
    public void checkExistBook(String bookName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#activeCartViewForm > div.sc-list-body.sc-java-remote-feature")));//wait until the shopping cart element is visible
        WebElement cart = driver.findElement(By.cssSelector("#activeCartViewForm > div.sc-list-body.sc-java-remote-feature"));//get the shopping cart web element
        List<WebElement> list = cart.findElements(By.className("sc-product-title"));//get all the items in the shopping cart
        assertTrue(list.size() == 1);//check if the number of the items in the shopping cart is one
        assertTrue(list.get(0).getText().equals(bookName));//check the remaining item's product title is correct
        driver.close();//close the web browser
    }

    @Then("^Nothing can be removed from the shopping cart$")
    public void checkEmptyCart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sc-active-cart > div > h1")));//wait until the shopping cart is visible
        WebElement cartTitle = driver.findElement(By.cssSelector("#sc-active-cart > div > h1"));//get the title of the shopping cart page
        assertTrue(cartTitle.getText().equals("Your Shopping Cart is empty."));//check if the shopping cart is empty
        driver.close();//close the web browser
    }

    private void buyBook(String bookName) {
        driver.findElement(By.cssSelector("#twotabsearchtextbox")).sendKeys(bookName); //put book name in the search bar
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#nav-search > form > div.nav-right > div > input"))); //make sure the search button is clickable
        driver.findElement(By.cssSelector("#nav-search > form > div.nav-right > div > input")).click();//click the search button
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#result_0 > div > div > div > div.a-fixed-left-grid-col.a-col-right > div.a-row.a-spacing-small > div:nth-child(1) > a")));//wait until the search result is clickable
        driver.findElement(By.cssSelector("#result_0 > div > div > div > div.a-fixed-left-grid-col.a-col-right > div.a-row.a-spacing-small > div:nth-child(1) > a")).click();//click the first item
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#add-to-cart-button")));//wait until the add to shopping cart button is clickable
        driver.findElement(By.cssSelector("#add-to-cart-button")).click();//click the add to shopping cart button
    }
}
