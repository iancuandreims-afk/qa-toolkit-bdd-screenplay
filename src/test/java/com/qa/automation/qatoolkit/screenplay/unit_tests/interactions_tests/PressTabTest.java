package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.mockDriver;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.mockDriverWithJavascriptExecutor;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.stubDisplayedAndEnabledElement;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.qa.automation.qatoolkit.screenplay.interactions.PressTab;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// PressTab waits up to 1 second for visibility before sending the key.
class PressTabTest {

    private static final By LOCATOR = By.id("username");

    @Test
    void sendsTabKeyToVisibleElement() {
        WebDriver driver = mockDriver();
        WebElement element = stubDisplayedAndEnabledElement(driver, LOCATOR);

        PressTab.on(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver));

        verify(element).sendKeys(Keys.TAB);
    }

    @Test
    void fallsBackToJavascriptKeyEventWhenNativeKeyPressFails() {
        WebDriver driver = mockDriverWithJavascriptExecutor();
        WebElement element = stubDisplayedAndEnabledElement(driver, LOCATOR);
        doThrow(new ElementClickInterceptedException("intercepted")).when(element).sendKeys(Keys.TAB);

        PressTab.on(LOCATOR).withJsFallback().performAs(actorWithBrowseTheWebAbility(driver));

        verify((JavascriptExecutor) driver, times(1)).executeScript(anyString(), any(WebElement.class));
    }

    @Test
    void doesNotUseJavascriptFallbackByDefault() {
        WebDriver driver = mockDriverWithJavascriptExecutor();
        WebElement element = stubDisplayedAndEnabledElement(driver, LOCATOR);
        doThrow(new ElementClickInterceptedException("intercepted")).when(element).sendKeys(Keys.TAB);

        try {
            PressTab.on(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver));
        } catch (ElementClickInterceptedException ignored) {
            // expected: no fallback is attempted
        }

        verify((JavascriptExecutor) driver, never()).executeScript(anyString(), any(WebElement.class));
    }
}
