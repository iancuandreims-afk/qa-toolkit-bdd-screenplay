package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;

import com.qa.automation.qatoolkit.screenplay.interactions.Click;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.mockDriver;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.mockDriverWithJavascriptExecutor;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.stubDisplayedAndEnabledElement;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

// Click waits up to 1 second for clickability before clicking.
class ClickTest {

    private static final By LOCATOR = By.id("submit");

    @Test
    void clicksElementWhenItIsClickable() {
        WebDriver driver = mockDriver();
        WebElement element = stubDisplayedAndEnabledElement(driver, LOCATOR);

        Click.on(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver));

        verify(element).click();
    }

    @Test
    void fallsBackToJavascriptClickWhenEnabledAndNativeClickFails() {
        WebDriver driver = mockDriverWithJavascriptExecutor();
        WebElement element = stubDisplayedAndEnabledElement(driver, LOCATOR);
        doThrow(new ElementClickInterceptedException("intercepted")).when(element).click();

        Click.on(LOCATOR).withJsFallback().performAs(actorWithBrowseTheWebAbility(driver));

        verify(element).click();
        verify((JavascriptExecutor) driver, times(1)).executeScript(anyString(), any(WebElement.class));
    }

    @Test
    void doesNotUseJavascriptFallbackByDefault() {
        WebDriver driver = mockDriverWithJavascriptExecutor();
        WebElement element = stubDisplayedAndEnabledElement(driver, LOCATOR);
        doThrow(new ElementClickInterceptedException("intercepted")).when(element).click();

        try {
            Click.on(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver));
        } catch (ElementClickInterceptedException ignored) {
            // expected: no fallback is attempted
        }

        verify((JavascriptExecutor) driver, never()).executeScript(anyString(), any(WebElement.class));
    }
}
