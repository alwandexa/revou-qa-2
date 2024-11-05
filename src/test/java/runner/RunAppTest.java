package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/app",
        glue = "steps.app",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/app/cucumber.html",
                "json:target/cucumber-reports/app/cucumber.json"
        }
)
public class RunAppTest extends AbstractTestNGCucumberTests { }

