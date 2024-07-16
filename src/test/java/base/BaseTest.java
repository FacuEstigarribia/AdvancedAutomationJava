package base;

import com.solvd.advancedautomation.emailverification.pages.HomePage;
import com.solvd.advancedautomation.util.ConfigLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected HomePage homePage;
    protected static final Properties properties = ConfigLoader.getProperties();

    @BeforeClass
    public void setUp(){
        System.setProperty("webdriver.chrome.driver","driver/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        goHomePage();
    }
    @BeforeMethod
    public void goHomePage(){
        driver.get("https://sendtestemail.com/");
        homePage = new HomePage(driver);
    }

    @AfterClass
    public void teardown(){
        driver.quit();
    }
}
