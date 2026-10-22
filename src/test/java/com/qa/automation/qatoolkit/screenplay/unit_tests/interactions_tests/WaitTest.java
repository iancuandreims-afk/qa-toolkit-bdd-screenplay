package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.mockDriverWithJavascriptExecutor;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.stubDisplayedAndEnabledElement;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.qa.automation.qatoolkit.screenplay.interactions.Wait;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

class WaitTest {

    private static final By LOCATOR = By.id("spinner");

    @Test
    void waitsForElementToBeVisibleByDefault() {
        WebDriver driver = mockDriverWithJavascriptExecutor();
        stubDisplayedAndEnabledElement(driver, LOCATOR);

        Wait.until(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver));

        verify(driver).findElement(LOCATOR);
    }

    @Test
    void waitsForPageReadyStateToBeComplete() {
        WebDriver driver = mockDriverWithJavascriptExecutor();
        when(((JavascriptExecutor) driver).executeScript(any(String.class))).thenReturn("complete");

        Wait.forPageLoad().performAs(actorWithBrowseTheWebAbility(driver));

        verify((JavascriptExecutor) driver).executeScript("return document.readyState");
    }

    @Test
    void waitsUntilCustomExpectedConditionIsSatisfied() {
        WebDriver driver = mockDriverWithJavascriptExecutor();
        Wait.until(d -> true).performAs(actorWithBrowseTheWebAbility(driver));
    }

    @Test
    void timesOutWhenCustomExpectedConditionNeverSatisfied() {
        WebDriver driver = mockDriverWithJavascriptExecutor();
        Wait wait = Wait.until(d -> false).forSeconds(1);

        assertThrows(TimeoutException.class,
                () -> wait.performAs(actorWithBrowseTheWebAbility(driver)));
    }

    @Test
    void waitsForElementToBeClickableWhenRequested() {
        WebDriver driver = mockDriverWithJavascriptExecutor();
        stubDisplayedAndEnabledElement(driver, LOCATOR);

        Wait.until(LOCATOR).isClickable().performAs(actorWithBrowseTheWebAbility(driver));

        verify(driver).findElement(LOCATOR);
    }

    @Test
    void waitsForElementToBePresentWhenRequested() {
        WebDriver driver = mockDriverWithJavascriptExecutor();
        stubDisplayedAndEnabledElement(driver, LOCATOR);

        Wait.until(LOCATOR).isPresent().performAs(actorWithBrowseTheWebAbility(driver));

        verify(driver).findElement(LOCATOR);
    }

    @Test
    void timesOutWhenElementNeverBecomesVisibleWithinConfiguredSeconds() {
        WebDriver driver = mockDriverWithJavascriptExecutor();
        when(driver.findElement(LOCATOR)).thenThrow(new NoSuchElementException("not found"));
        Wait wait = Wait.until(LOCATOR).forSeconds(1);

        assertThrows(TimeoutException.class,
                () -> wait.performAs(actorWithBrowseTheWebAbility(driver)));
    }
}
