package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features/web",
        glue = "steps.web",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/api/cucumber.html",
                "json:target/cucumber-reports/api/cucumber.json"
        }
)
public class RunWebTest extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

