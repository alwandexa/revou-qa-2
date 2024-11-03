package steps.web;

import io.cucumber.java.Before;
import io.cucumber.java.After;

public class Hooks {

    @Before
    public void setUp() {
        DriverManager.initializeDriver();
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
