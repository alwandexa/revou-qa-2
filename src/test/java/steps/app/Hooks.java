package steps.app;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class Hooks {

    static AndroidDriver driver;
    private static final String ENV = System.getProperty("ENV", "local");
    private static final String LOCAL_GRID_URL = "http://localhost:4723";
    private static final String SELENIUM_GRID_URL = "http://host.docker.internal:4723";

    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:deviceName", "emulator-5554");
        capabilities.setCapability("appium:platformVersion", "15");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:appPackage", "com.saucelabs.mydemoapp.rn");
        capabilities.setCapability("appium:appActivity", "com.saucelabs.mydemoapp.rn.MainActivity");

        driver = new AndroidDriver(new URL(ENV.equalsIgnoreCase("local") ? LOCAL_GRID_URL : SELENIUM_GRID_URL), capabilities);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static AndroidDriver getDriver() {
        return driver;
    }
}
