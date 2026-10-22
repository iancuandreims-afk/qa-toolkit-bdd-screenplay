package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.mockDriverWithInputDevices;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.stubDisplayedAndEnabledElement;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.qa.automation.qatoolkit.screenplay.interactions.DoubleClick;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

// DoubleClick waits up to 1 second for clickability, then uses Actions to double-click.
class DoubleClickTest {

    private static final By LOCATOR = By.id("item");

    @Test
    void performsDoubleClickWhenElementIsClickable() {
        WebDriver driver = mockDriverWithInputDevices();
        stubDisplayedAndEnabledElement(driver, LOCATOR);

        DoubleClick.on(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver));

        verify(driver).findElement(LOCATOR);
    }

    @Test
    void failsWhenElementIsNotClickable() {
        WebDriver driver = mockDriverWithInputDevices();
        when(driver.findElement(LOCATOR)).thenThrow(new NoSuchElementException("missing"));

        assertThrows(TimeoutException.class,
                () -> DoubleClick.on(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver)));
    }
}
