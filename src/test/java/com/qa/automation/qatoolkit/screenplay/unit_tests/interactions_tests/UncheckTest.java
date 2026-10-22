package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.qa.automation.qatoolkit.screenplay.interactions.Uncheck;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// Uncheck waits up to 1 second for clickability, then clicks only if selected.
class UncheckTest {

    private static final By LOCATOR = By.id("newsletter");

    @Test
    void clicksCheckboxWhenSelected() {
        WebDriver driver = mock(WebDriver.class);
        WebElement element = mock(WebElement.class);
        when(driver.findElement(LOCATOR)).thenReturn(element);
        when(element.isDisplayed()).thenReturn(true);
        when(element.isEnabled()).thenReturn(true);
        when(element.isSelected()).thenReturn(true);

        Uncheck.box(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver));

        verify(element).click();
    }

    @Test
    void doesNotClickCheckboxWhenAlreadyUnselected() {
        WebDriver driver = mock(WebDriver.class);
        WebElement element = mock(WebElement.class);
        when(driver.findElement(LOCATOR)).thenReturn(element);
        when(element.isDisplayed()).thenReturn(true);
        when(element.isEnabled()).thenReturn(true);
        when(element.isSelected()).thenReturn(false);

        Uncheck.box(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver));

        verify(element, never()).click();
    }
}
