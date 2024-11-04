package steps.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.net.URL;
import java.time.Duration;

public class DriverManager {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String SELENIUM_GRID_URL = "http://selenium-hub:4444/wd/hub";
    private static final String ENV = System.getProperty("ENV", "local"); // Default to local if not specified

    public static void initializeDriver() {
        if (driver == null) {
            try {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                
                // Only add container-specific options when not in local environment
                if (!ENV.equalsIgnoreCase("local")) {
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                    options.addArguments("--headless");
                }
                
                if (ENV.equalsIgnoreCase("grid")) {
                    // Remote WebDriver for Selenium Grid
                    driver = new RemoteWebDriver(new URL(SELENIUM_GRID_URL), options);
                } else {
                    // Local WebDriver
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver(options);
                }
                
                wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize WebDriver: " + e.getMessage(), e);
            }
        }
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            initializeDriver();
        }
        return driver;
    }

    public static WebDriverWait getWait() {
        if (wait == null) {
            initializeDriver();
        }
        return wait;
    }

    public static void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Error while quitting driver: " + e.getMessage());
            } finally {
                driver = null;
                wait = null;
            }
        }
    }
}