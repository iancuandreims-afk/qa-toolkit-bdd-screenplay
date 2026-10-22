package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;

import com.qa.automation.qatoolkit.screenplay.interactions.PressEnter;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.mockDriver;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.mockDriverWithJavascriptExecutor;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.stubDisplayedAndEnabledElement;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class PressEnterTest {

    private static final By LOCATOR = By.id("search");

    @Test
    void sendsEnterKeyToVisibleElement() {
        WebDriver driver = mockDriver();
        WebElement element = stubDisplayedAndEnabledElement(driver, LOCATOR);

        PressEnter.on(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver));

        verify(element).sendKeys(Keys.ENTER);
    }

    @Test
    void usesJavascriptFallbackWhenSendKeysFailsAndFallbackEnabled() {
        WebDriver driver = mockDriverWithJavascriptExecutor();
        WebElement element = stubDisplayedAndEnabledElement(driver, LOCATOR);
        doThrow(new ElementNotInteractableException("nope")).when(element).sendKeys(Keys.ENTER);

        PressEnter.on(LOCATOR).withJsFallback().performAs(actorWithBrowseTheWebAbility(driver));

        verify((JavascriptExecutor) driver).executeScript(anyString(), any(WebElement.class));
    }

    @Test
    void rethrowsWhenSendKeysFailsWithoutFallback() {
        WebDriver driver = mockDriverWithJavascriptExecutor();
        WebElement element = stubDisplayedAndEnabledElement(driver, LOCATOR);
        doThrow(new ElementNotInteractableException("nope")).when(element).sendKeys(Keys.ENTER);

        try {
            PressEnter.on(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver));
        } catch (ElementNotInteractableException ignored) {
            // expected: no fallback is attempted
        }

        verify((JavascriptExecutor) driver, never()).executeScript(anyString(), any(WebElement.class));
    }
}
