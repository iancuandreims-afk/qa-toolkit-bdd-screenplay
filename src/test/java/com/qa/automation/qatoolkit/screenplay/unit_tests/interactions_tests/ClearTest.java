package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import com.qa.automation.qatoolkit.screenplay.interactions.Clear;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// Clear waits up to 1 second for visibility before clearing.
class ClearTest {

    private static final By LOCATOR = By.id("name");

    @Test
    void clearsVisibleElement() {
        WebDriver driver = mock(WebDriver.class);
        WebElement element = mock(WebElement.class);
        when(driver.findElement(LOCATOR)).thenReturn(element);
        when(element.isDisplayed()).thenReturn(true);

        Clear.field(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver));

        verify(element).clear();
    }

    @Test
    void fallsBackToJavascriptClearWhenNativeClearFailsAndFallbackEnabled() {
        WebDriver driver = mock(WebDriver.class, withSettings().extraInterfaces(JavascriptExecutor.class));
        WebElement element = mock(WebElement.class);
        when(driver.findElement(LOCATOR)).thenReturn(element);
        when(element.isDisplayed()).thenReturn(true);
        doThrow(new ElementNotInteractableException("not interactable")).when(element).clear();

        Clear.field(LOCATOR).withJsFallback().performAs(actorWithBrowseTheWebAbility(driver));

        verify((JavascriptExecutor) driver).executeScript(anyString(), any(WebElement.class));
    }

    @Test
    void doesNotUseJavascriptFallbackByDefault() {
        WebDriver driver = mock(WebDriver.class, withSettings().extraInterfaces(JavascriptExecutor.class));
        WebElement element = mock(WebElement.class);
        when(driver.findElement(LOCATOR)).thenReturn(element);
        when(element.isDisplayed()).thenReturn(true);
        doThrow(new ElementNotInteractableException("not interactable")).when(element).clear();

        try {
            Clear.field(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver));
        } catch (ElementNotInteractableException ignored) {
            // expected: no fallback is attempted
        }

        verify((JavascriptExecutor) driver, never()).executeScript(anyString(), any(WebElement.class));
    }
}
