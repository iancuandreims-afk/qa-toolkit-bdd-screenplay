package com.qa.automation.qatoolkit.screenplay.e2e_tests;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.abilities.BrowseTheWeb;
import com.qa.automation.qatoolkit.support.LocalStaticServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

abstract class AbstractBaseScreenplayTest {
    protected Actor theUser;
    protected WebDriver driver;

    @BeforeAll
    static void startServer() throws Exception {
        LocalStaticServer.start();
    }

    @AfterAll
    static void stopServer() {
        LocalStaticServer.stop();
    }

    @BeforeEach
    void setUpActor() {
        theUser = new Actor("Tester");
        theUser.can(BrowseTheWeb.withChrome());
        driver = theUser.abilityTo(BrowseTheWeb.class).getDriver();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
