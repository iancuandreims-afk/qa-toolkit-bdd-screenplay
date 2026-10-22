package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.mockDriverWithInputDevices;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.stubDisplayedAndEnabledElement;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.qa.automation.qatoolkit.screenplay.interactions.Hover;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

// Hover waits up to 5 seconds for visibility, then moves the pointer over the element.
class HoverTest {

    private static final By LOCATOR = By.id("menu");

    @Test
    void looksUpElementAndHoversWhenVisible() {
        WebDriver driver = mockDriverWithInputDevices();
        stubDisplayedAndEnabledElement(driver, LOCATOR);

        Hover.on(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver));

        verify(driver).findElement(LOCATOR);
    }

    @Test
    void failsWhenElementIsNotVisible() {
        WebDriver driver = mockDriverWithInputDevices();
        when(driver.findElement(LOCATOR)).thenThrow(new NoSuchElementException("missing"));

        assertThrows(TimeoutException.class,
                () -> Hover.on(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver)));
    }
}
