package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.qa.automation.qatoolkit.screenplay.interactions.Check;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// Check waits up to 1 second for clickability, then clicks only if unselected.
class CheckTest {

    private static final By LOCATOR = By.id("terms");

    @Test
    void clicksCheckboxWhenUnselected() {
        WebDriver driver = mock(WebDriver.class);
        WebElement element = mock(WebElement.class);
        when(driver.findElement(LOCATOR)).thenReturn(element);
        when(element.isDisplayed()).thenReturn(true);
        when(element.isEnabled()).thenReturn(true);
        when(element.isSelected()).thenReturn(false);

        Check.box(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver));

        verify(element).click();
    }

    @Test
    void doesNotClickCheckboxWhenAlreadySelected() {
        WebDriver driver = mock(WebDriver.class);
        WebElement element = mock(WebElement.class);
        when(driver.findElement(LOCATOR)).thenReturn(element);
        when(element.isDisplayed()).thenReturn(true);
        when(element.isEnabled()).thenReturn(true);
        when(element.isSelected()).thenReturn(true);

        Check.box(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver));

        verify(element, never()).click();
    }
}
