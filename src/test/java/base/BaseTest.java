package base;

import com.solvd.advancedautomation.util.DataLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected static final Properties properties = DataLoader.getProperties();
    @BeforeClass
    public void setUp(){
        System.setProperty("webdriver.chrome.driver","driver/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }
    @AfterClass
    public void teardown(){
        driver.quit();
    }
    protected WebDriver getDriver() {
        return driver;
    }
}
