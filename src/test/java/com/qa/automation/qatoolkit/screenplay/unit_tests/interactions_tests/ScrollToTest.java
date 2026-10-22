package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.mockDriverWithInputDevices;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.mockDriverWithInputDevicesAndJavascriptExecutor;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.stubDisplayedAndEnabledElement;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.qa.automation.qatoolkit.screenplay.interactions.ScrollTo;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Interactive;

class ScrollToTest {

    private static final By LOCATOR = By.id("footer");

    @Test
    void movesPointerToVisibleElement() {
        WebDriver driver = mockDriverWithInputDevices();
        stubDisplayedAndEnabledElement(driver, LOCATOR);

        ScrollTo.element(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver));

        verify((Interactive) driver).perform(any());
    }

    @Test
    void fallsBackToScrollIntoViewWhenEnabledAndActionsFail() {
        WebDriver driver = mockDriverWithInputDevicesAndJavascriptExecutor();
        stubDisplayedAndEnabledElement(driver, LOCATOR);
        doThrow(new ElementClickInterceptedException("blocked"))
                .when((Interactive) driver).perform(any());

        ScrollTo.element(LOCATOR).withJsFallback()
                .performAs(actorWithBrowseTheWebAbility(driver));

        verify((JavascriptExecutor) driver).executeScript(anyString(), any(WebElement.class));
    }

    @Test
    void doesNotUseJavascriptFallbackByDefault() {
        WebDriver driver = mockDriverWithInputDevicesAndJavascriptExecutor();
        stubDisplayedAndEnabledElement(driver, LOCATOR);
        doThrow(new ElementClickInterceptedException("blocked"))
                .when((Interactive) driver).perform(any());

        assertThrows(ElementClickInterceptedException.class,
                () -> ScrollTo.element(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver)));

        verify((JavascriptExecutor) driver, never()).executeScript(anyString(), any(WebElement.class));
    }
}
