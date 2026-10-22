package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.mockDriver;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.mockDriverWithJavascriptExecutor;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.stubDisplayedAndEnabledElement;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.qa.automation.qatoolkit.screenplay.interactions.Press;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// Press waits up to 5 seconds for visibility before sending the key.
class PressTest {

    private static final By LOCATOR = By.id("search");

    @Test
    void sendsKeyToVisibleElement() {
        WebDriver driver = mockDriver();
        WebElement element = stubDisplayedAndEnabledElement(driver, LOCATOR);

        Press.key(Keys.ARROW_DOWN).on(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver));

        verify(element).sendKeys(Keys.ARROW_DOWN);
    }

    @Test
    void throwsWhenNoLocatorIsSet() {
        WebDriver driver = mockDriver();

        assertThrows(IllegalArgumentException.class,
                () -> Press.key(Keys.ARROW_DOWN).performAs(actorWithBrowseTheWebAbility(driver)));
    }

    @Test
    void fallsBackToJavascriptWhenSendKeysFailsAndFallbackIsEnabled() {
        WebDriver driver = mockDriverWithJavascriptExecutor();
        WebElement element = stubDisplayedAndEnabledElement(driver, LOCATOR);
        doThrow(new ElementNotInteractableException("not interactable")).when(element).sendKeys(any(CharSequence[].class));

        Press.key(Keys.ARROW_DOWN).on(LOCATOR).withJsFallback()
                .performAs(actorWithBrowseTheWebAbility(driver));

        verify((JavascriptExecutor) driver, times(1)).executeScript(anyString(), any(WebElement.class));
    }

    @Test
    void doesNotUseJavascriptFallbackByDefault() {
        WebDriver driver = mockDriverWithJavascriptExecutor();
        WebElement element = stubDisplayedAndEnabledElement(driver, LOCATOR);
        doThrow(new ElementNotInteractableException("not interactable")).when(element).sendKeys(any(CharSequence[].class));

        assertThrows(ElementNotInteractableException.class,
                () -> Press.key(Keys.ARROW_DOWN).on(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver)));

        verify((JavascriptExecutor) driver, never()).executeScript(anyString(), any(), any());
    }
}
