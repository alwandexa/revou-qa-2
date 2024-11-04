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
    private static final String ENV = System.getProperty("ENV", "local");
    private static final String LOCAL_GRID_URL = "http://localhost:4444/wd/hub";
    private static final String SELENIUM_GRID_URL = "http://selenium-hub:4444/wd/hub";

    public static void initializeDriver() {
        if (driver == null) {
            try {
                ChromeOptions options = new ChromeOptions();
                
                // Base options for all environments
                options.addArguments("--remote-allow-origins=*");
                
                if (!ENV.equalsIgnoreCase("local")) {
                    // Options for grid environments
                    options.addArguments("--headless=new");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                    options.addArguments("--disable-gpu");
                    options.addArguments("--window-size=1920,1080");
                    options.addArguments("--disable-extensions");
                    options.addArguments("--disable-infobars");
                    options.addArguments("--ignore-certificate-errors");
                } else {
                    // Options for local environment
                    options.addArguments("--start-maximized");
                }
                
                switch (ENV.toLowerCase()) {
                    case "local":
                        WebDriverManager.chromedriver().setup();
                        driver = new ChromeDriver(options);
                        break;
                        
                    case "local-grid":
                        System.out.println("Connecting to local grid at: " + LOCAL_GRID_URL);
                        driver = new RemoteWebDriver(new URL(LOCAL_GRID_URL), options);
                        break;
                        
                    case "grid":
                    case "container":
                        System.out.println("Connecting to Selenium grid at: " + SELENIUM_GRID_URL);
                        driver = new RemoteWebDriver(new URL(SELENIUM_GRID_URL), options);
                        break;
                        
                    default:
                        throw new IllegalArgumentException("Invalid ENV value: " + ENV + 
                            ". Valid values are: local, local-grid, grid, container");
                }
                
                // Initialize wait after driver is created
                wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                
            } catch (Exception e) {
                String errorMsg = String.format(
                    "Failed to initialize WebDriver in %s environment. Error: %s", 
                    ENV, 
                    e.getMessage()
                );
                System.err.println(errorMsg);
                e.printStackTrace();
                throw new RuntimeException(errorMsg, e);
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