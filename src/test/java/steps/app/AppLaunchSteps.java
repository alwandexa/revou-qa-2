package steps.app;

import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;

import java.time.Duration;

public class AppLaunchSteps {

    WebDriver driver = Hooks.getDriver();  // Assuming Hooks class provides the driver
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    @Given("the application is launched")
    public void the_application_is_launched() {
        // Check for an element that signifies the app has launched successfully
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.rn:id/action_bar_root")));
    }
}
