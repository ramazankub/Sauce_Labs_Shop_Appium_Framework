package tests;

import com.codeborne.selenide.WebDriverRunner;
import drivers.DriverFactory;
import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    private AppiumDriver driver;

    public void openApp() {
        driver = DriverFactory.createDriver();

        if (driver != null) {
            WebDriverRunner.setWebDriver(driver);
        }
    }

    @BeforeEach
    public void setup() {
        openApp();
    }

    @AfterEach
    public void quit() {

        if (driver != null) {
            driver.quit();
        }
    }
}
