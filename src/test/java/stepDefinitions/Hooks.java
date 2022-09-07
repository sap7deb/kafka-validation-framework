package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.BaseTest;

public class Hooks {
    private BaseTest baseTest;

    public Hooks(BaseTest baseTest) {
        this.baseTest = baseTest;
    }

    @Before
    public void bf(Scenario scenario) {
        baseTest.setScenario(scenario);
    }

    @After
    public void af(Scenario scenario) {
        if (scenario.isFailed()) {
            scenario.log("Scenario Failed");
        }

    }

}
