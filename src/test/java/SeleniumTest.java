import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class SeleniumTest {
    private final String BASE_URL = "https://demo.opencart.com/";
    private final Long IMPLICITLY_WAIT_SECONDS = 10L;
    private final Long ONE_SECOND_DELAY = 1000L;
    private WebDriver driver;

    private void presentationSleep() {
        presentationSleep(1);
    }

    private void presentationSleep(int seconds) {
        try {
            Thread.sleep(seconds * ONE_SECOND_DELAY);
        } catch (InterruptedException e) {
            presentationSleep();
            e.printStackTrace();
        }
    }

    @BeforeSuite
    public void beforeSuite() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_SECONDS, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        presentationSleep();
        driver.quit();
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get(BASE_URL);
        presentationSleep();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        presentationSleep();
        if (!result.isSuccess()) {
            String testName = result.getName();
            System.out.println("***TC error, name = " + testName + " ERROR");
        }
        presentationSleep();
    }

    @Test
    public void checkMak() {
        //
        // Choose Currency
        driver.findElement(By.cssSelector("ul.list-inline")).click();
        presentationSleep();
        driver.findElement(By.xpath("//*[text()='€ Euro']")).click();
        presentationSleep();

        //Choose Desktops and Mac menu
        driver.findElement(By.cssSelector("a.nav-link.dropdown-toggle")).click();
        presentationSleep();
        driver.findElement(By.cssSelector("a.nav-link.dropdown-toggle")).click();
        presentationSleep();
//        driver.findElement(By.cssSelector("a.see-all")).click();
//        presentationSleep();
        driver.findElement(By.xpath("//a[text()='Mac (1)']")).click(); //imac
        presentationSleep();


        //Check whether the product "iMac" at the price of 111.55 euros is present on the page
        WebElement price = driver.findElement(By.xpath("//a[text()='iMac']/../following-sibling::div[@class='price']"));
        Assert.assertTrue(price.getText().contains("111.55€"));
        System.out.println("contains " + price.getText());
        presentationSleep();

        driver.quit();

    }


}
