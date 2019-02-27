package session;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class SessionTest {

    private static final String APP_CONTEXT = "EvanBookstoreTransact";

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass // Run this code before the class starts
    public static void setUpBeforeClass() {
        ChromeDriverManager.getInstance().setup();
    }

    @Before // Run this code before every test method
    public void setUp() {
        // Initialize the driver
        driver = new ChromeDriver();
        // Wait up to 10 seconds for the driver to load
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Initialize a web-driver wait object
        // This will be used to explicitly wait for specific elements
        wait = new WebDriverWait(driver, 10);
    }

    @After // Run this code after every test method
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void item3FromCategoryDropdownLinksToCategoryPage() {

        // Load the homepage of the bookstore
        driver.get("http://localhost:8080/" + APP_CONTEXT);

        // Wait for all elements with class "categoryDropdownItem" to load
        // Once they load, store them as a list of web-element objects
        List<WebElement> categoryItems =
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("categoryDropdownItem")));

        // Check that there are at least four categories
        assertTrue("At least four categories items in dropdown", 4 <= categoryItems.size());

        // Find the category dropdown element
        WebElement categoryDropdown = driver.findElement(By.id("categoryDropdown"));
        // Click on the category dropdown element
        categoryDropdown.click();

        // Create an actions object that allows you to move the cursor
        Actions actions = new Actions(driver);
        // Hover over the category dropdown with the cursor
        actions.moveToElement(categoryDropdown).build().perform();

        // Click on the 3rd element in the dropdown menu
        categoryItems.get(2).click();

        try {
            // Get the current URL and store it in a URL object
            URL url = new URL(driver.getCurrentUrl());
            // Check if the path of the URL equals the application context + category.jsp
            assertEquals("/" + APP_CONTEXT + "/category", url.getPath());
        } catch (MalformedURLException mue) {
            // If the URL returned is not a proper URL string, fail the test
            // Note: It will always be a proper URL string since that is what the getCurrentUrl returns
            fail("Malformed URL");
        }
    }

    @Test
    public void selectedCategoryButtonIsACategoryButton() {
        driver.get("http://localhost:8080/" + APP_CONTEXT + "/category");

        List<WebElement> categoryButtons =
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("categoryButton")));

        WebElement selectedCategoryButton = driver.findElement(By.id("selectedCategoryButton"));
        assertTrue("The category buttons contain the selected category",
                categoryButtons.contains(selectedCategoryButton));
    }

    @Test
    public void categoryPageHasAtLeast4CategoryButtons() {
        driver.get("http://localhost:8080/" + APP_CONTEXT + "/category");

        List<WebElement> categoryButtons =
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("categoryButton")));

        assertTrue("At least four categories buttons", 4 <= categoryButtons.size());
    }

    @Test
    public void logoLinksToHomepage() {
        driver.get("http://localhost:8080/" + APP_CONTEXT + "/category");

        WebElement logo = driver.findElement(By.id("logo"));
        logo.click();

        try {
            URL url = new URL(driver.getCurrentUrl());
            assertEquals("/" + APP_CONTEXT + "/home", url.getPath());
        } catch (MalformedURLException mue) {
            fail("Malformed URL");
        }
    }

    @Test
    public void cartCountTextHasNumberFrom0To9() {
        driver.get("http://localhost:8080/" + APP_CONTEXT);

        WebElement cartCount = driver.findElement(By.id("cartCount"));

        int n = -1;
        try {
            n = Integer.parseInt(cartCount.getText());
        } catch (NumberFormatException nfe) {
            fail("Text is not an integer");
        }
        assertTrue("Number is between 0 and 9", 0 <= n && n <= 9);

    }

    //My tests:
    /*
    1. Add-to-cart
     */
   @Test
    public void addToCartTest() {
        resetCart();
        int totalBooks = 4;
        int categoryIndex = 3;
        int bookIndex = 2;
        addBooks(categoryIndex, bookIndex, totalBooks);
    }

    /*
    2. Decrement
    */
    @Test
    public void decrementTest() {
        //first, 0 out the cart and start fresh:
        resetCart();

        //Add 4 books from category 3, book 2:
        int totalBooks = 2;
        int categoryIndex = 3;
        int bookIndex = 2;
        addBooks(categoryIndex, bookIndex, totalBooks);

        //make sure we have totalBooks count of books in cart
        assertEquals(totalBooks, getCartCount());

        //decrement:
        for (int i = 0; i < totalBooks - 1; i++) {
            decreaseCartItem();
        }

        assertEquals(1, getCartCount());
    }

    /*
    3. Increment
    */

    @Test
    public void incrementTest() {
        //first, 0 out the cart and start fresh:
        resetCart();

        //Add 4 books from category 3, book 2:
        int totalBooks = 2;
        int categoryIndex = 3;
        int bookIndex = 2;
        addBooks(categoryIndex, bookIndex, totalBooks);

        //make sure we have totalBooks count of books in cart
        assertEquals(totalBooks, getCartCount());

        //decrement:
        for (int i = 0; i < 2; i++) {
            increaseCartItem();
        }

        assertEquals(4, getCartCount());
    }

    /*
    4. Clear cart
    */
    @Test
    public void clearCartTest() {
        //first, 0 out the cart and start fresh:
        resetCart();

        //make sure we have totalBooks count of books in cart
        assertEquals(0, getCartCount());
    }

    /*
    5. Continue shopping
     */
    @Test
    public void continueShoppingButton() {
        driver.get("http://localhost:8080/" + APP_CONTEXT);
        gotoCategory(6);
        String catHeaderText = driver.findElement(By.id("categoryNameHeader")).getText();
        driver.get("http://localhost:8080/" + APP_CONTEXT + "/cart");
        driver.findElement(By.id("continueShoppingBtn")).click();
        String catHeaderText2 = driver.findElement(By.id("categoryNameHeader")).getText();
        assertEquals(catHeaderText, catHeaderText2);
    }


    private void addBooks(int bookCatIndex, int bookIndex, int totalBooks) {

        for (int i = 0; i < totalBooks; i++) {
            List<WebElement> addToCartButtons =
                    addABook(bookCatIndex);
            //add third book from group:
            addToCartButtons.get(bookIndex).click();
        }
        WebElement cartCount = driver.findElement(By.id("cartCount"));
        int n = -1;
        try {
            n = Integer.parseInt(cartCount.getText());
            assertEquals(n, totalBooks);
        } catch (NumberFormatException nfe) {
            fail("Text is not an integer");
        }
    }

    private List<WebElement> addABook(int catIndex) {
        driver.get("http://localhost:8080/" + APP_CONTEXT);
        gotoCategory(catIndex);

        List<WebElement> addToCartButtons = addToCartButtons =
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("addToCartButton")));
        return addToCartButtons;
    }

    private void gotoCategory(int catIndex) {
        List<WebElement> categoryItems =
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("categoryDropdownItem")));
        WebElement categoryDropdown = driver.findElement(By.id("categoryDropdown"));
        categoryDropdown.click();
        Actions actions = new Actions(driver);
        actions.moveToElement(categoryDropdown).build().perform();
        categoryItems.get(catIndex).click();
    }


    private int getCartCount() {
        return Integer.parseInt(driver.findElement(By.id("cartCount")).getText());
    }

    private void resetCart() {
        driver.get("http://localhost:8080/" + APP_CONTEXT + "/cart");

        while (getCartCount() > 0) {
            decreaseCartItem();
        }
    }

    private void decreaseCartItem() {
        driver.get("http://localhost:8080/" + APP_CONTEXT + "/cart");
        List<WebElement> cartAddButtons =
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("cartSubtractButton")));
        cartAddButtons.get(0).click();
    }

    private void increaseCartItem() {
        driver.get("http://localhost:8080/" + APP_CONTEXT + "/cart");
        List<WebElement> cartAddButtons =
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("cartAddButton")));
        cartAddButtons.get(0).click();
    }
}
