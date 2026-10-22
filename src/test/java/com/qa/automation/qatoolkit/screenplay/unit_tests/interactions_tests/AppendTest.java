package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import com.qa.automation.qatoolkit.screenplay.interactions.Append;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.mockDriver;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.stubDisplayedAndEnabledElement;
import static org.mockito.Mockito.verify;

// Append waits up to 1 second for visibility before sending keys.
class AppendTest {

    private static final By LOCATOR = By.id("notes");

    @Test
    void sendsTextToVisibleElement() {
        WebDriver driver = mockDriver();
        WebElement element = stubDisplayedAndEnabledElement(driver, LOCATOR);

        Append.value("more").in(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver));

        verify(element).sendKeys("more");
    }
}
