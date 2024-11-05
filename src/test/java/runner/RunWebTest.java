package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/web",
        glue = "steps.web",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/web/index.html",
                "json:target/cucumber-reports/web/cucumber.json"
        }
)
public class RunWebTest extends AbstractTestNGCucumberTests {
}

