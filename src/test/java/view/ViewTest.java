

package view;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Assert;
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

public class ViewTest {
    private static final String APP_CONTEXT = "";
    private WebDriver driver;
    private WebDriverWait wait;

    public ViewTest() {
    }

    @BeforeClass
    public static void setUpBeforeClass() {
        ChromeDriverManager.getInstance().setup();
    }

    @Before
    public void setUp() {
        this.driver = new ChromeDriver();
        this.driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
        this.wait = new WebDriverWait(this.driver, 10L);
    }

    @After
    public void tearDown() {
        if (this.driver != null) {
            this.driver.quit();
        }

    }

    @Test
    public void viewHomePage() {
        this.driver.get("http://localhost:8080/");
        List<WebElement> categoryItems = (List)this.wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("categoryDropdownItem")));
        Assert.assertTrue("At least four categories items in dropdown", 4 <= categoryItems.size());
        WebElement categoryDropdown = this.driver.findElement(By.id("categoryDropdown"));
        categoryDropdown.click();
        Actions actions = new Actions(this.driver);
        actions.moveToElement(categoryDropdown).build().perform();
        ((WebElement)categoryItems.get(2)).click();

        try {
            URL url = new URL(this.driver.getCurrentUrl());
            Assert.assertEquals("/category_business.jsp", url.getPath());
        } catch (MalformedURLException var5) {
            Assert.fail("Malformed URL");
        }

    }

    @Test
    public void logoLinksToHomepage() {
        this.driver.get("http://localhost:8080/category_bio.jsp");
        List<WebElement> logoL = (List)this.wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("logo")));
        Assert.assertTrue("Just one logo", 1 <= logoL.size());
        ((WebElement)logoL.get(0)).click();

        try {
            URL url = new URL(this.driver.getCurrentUrl());
            Assert.assertEquals("/index.jsp", url.getPath());
        } catch (MalformedURLException var3) {
            Assert.fail("Malformed URL");
        }

    }

    @Test
    public void selectedCategoryButton() {
        this.driver.get("http://localhost:8080/category_bio.jsp");
        List<WebElement> categoryButtonL = (List)this.wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("categoryButton")));
        Assert.assertTrue("Just one logo", 10 <= categoryButtonL.size());
        String className = ((WebElement)categoryButtonL.get(0)).getAttribute("class");
        Assert.assertEquals(className, "categoryButton selectedCategoryButton");
    }

    @Test
    public void cartCountTextHasNumberFrom0To9() {
        this.driver.get("http://localhost:8080/category_bio.jsp");
        WebElement cartCountWE = this.driver.findElement(By.id("cartCount"));
        String cartCountText = cartCountWE.getAttribute("innerText");
        Assert.assertEquals(cartCountText, "3");
    }

    @Test
    public void categoryPageHasAtLeast4CategoryButtons() {
        this.driver.get("http://localhost:8080/category_bio.jsp");
        List<WebElement> categoryButtonL = (List)this.wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("categoryButton")));
        Assert.assertTrue("At least 4 category buttons", 10 == categoryButtonL.size());
    }
}
