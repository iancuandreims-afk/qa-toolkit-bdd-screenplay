package com.qa.automation.qatoolkit.screenplay.unit_tests.abilities_tests;

import com.qa.automation.qatoolkit.screenplay.abilities.BrowseTheWeb;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

// BrowseTheWeb simply stores a driver; the withChrome()/withFirefox()/withEdge()/withSafari()
// factory methods launch a real browser and are left to the e2e suite.
class BrowseTheWebTest {

    @Test
    void getDriverReturnsTheDriverPassedToTheConstructor() {
        WebDriver driver = mock(WebDriver.class);

        BrowseTheWeb ability = new BrowseTheWeb(driver);

        assertSame(driver, ability.getDriver());
    }
}
