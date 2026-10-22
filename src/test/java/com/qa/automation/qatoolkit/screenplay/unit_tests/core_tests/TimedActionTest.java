package com.qa.automation.qatoolkit.screenplay.unit_tests.core_tests;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.TimedAction;
import com.qa.automation.qatoolkit.screenplay.abilities.BrowseTheWeb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Exercises TimedAction's fluent configuration and wait helpers against a mocked WebDriver.
 * A stub subclass exposes the protected helpers so they can be called directly.
 */
class TimedActionTest {

    private static final By TARGET = By.id("target");

    private WebDriver driver;
    private Actor actor;
    private StubTimedAction action;

    @BeforeEach
    void setUp() {
        driver = mock(WebDriver.class);
        actor = new Actor("Tester");
        actor.can(new BrowseTheWeb(driver));
        action = new StubTimedAction();
    }

    @Test
    void defaultsAreFiveSecondTimeoutAndTwoHundredMillisecondPolling() {
        assertEquals(Duration.ofSeconds(5), action.timeoutValue());
        assertEquals(Duration.ofMillis(200), action.pollingValue());
    }

    @Test
    void withTimeoutSetsTimeoutAndReturnsSameInstance() {
        Duration timeout = Duration.ofSeconds(9);

        StubTimedAction returned = action.withTimeout(timeout);

        assertSame(action, returned);
        assertEquals(timeout, action.timeoutValue());
    }

    @Test
    void withTimeoutSecondsConvertsToDuration() {
        StubTimedAction returned = action.withTimeoutSeconds(7);

        assertSame(action, returned);
        assertEquals(Duration.ofSeconds(7), action.timeoutValue());
    }

    @Test
    void withPollingSetsPollingAndReturnsSameInstance() {
        Duration polling = Duration.ofMillis(50);

        StubTimedAction returned = action.withPolling(polling);

        assertSame(action, returned);
        assertEquals(polling, action.pollingValue());
    }

    @Test
    void withPollingSecondsConvertsToDuration() {
        StubTimedAction returned = action.withPollingSeconds(2);

        assertSame(action, returned);
        assertEquals(Duration.ofSeconds(2), action.pollingValue());
    }

    @Test
    void fluentSettersChainWithoutLosingType() {
        StubTimedAction configured = new StubTimedAction()
                .withTimeoutSeconds(3)
                .withPollingSeconds(1);

        assertEquals(Duration.ofSeconds(3), configured.timeoutValue());
        assertEquals(Duration.ofSeconds(1), configured.pollingValue());
    }

    @Test
    void performAsRejectsNullActor() {
        assertThrows(IllegalArgumentException.class, () -> action.performAs(null));
    }

    @Test
    void waitForPresenceReturnsElementWhenFound() {
        WebElement element = mock(WebElement.class);
        when(driver.findElement(TARGET)).thenReturn(element);

        assertSame(element, action.presence(actor, TARGET));
    }

    @Test
    void waitForVisibilityReturnsElementWhenDisplayed() {
        WebElement element = mock(WebElement.class);
        when(element.isDisplayed()).thenReturn(true);
        when(driver.findElement(TARGET)).thenReturn(element);

        assertSame(element, action.visibility(actor, TARGET));
    }

    @Test
    void waitForVisibilityTimesOutWhenElementStaysHidden() {
        WebElement element = mock(WebElement.class);
        when(element.isDisplayed()).thenReturn(false);
        when(driver.findElement(TARGET)).thenReturn(element);
        action.withTimeout(Duration.ofMillis(100)).withPolling(Duration.ofMillis(10));

        assertThrows(TimeoutException.class, () -> action.visibility(actor, TARGET));
    }

    @Test
    void waitForClickableReturnsElementWhenDisplayedAndEnabled() {
        WebElement element = mock(WebElement.class);
        when(element.isDisplayed()).thenReturn(true);
        when(element.isEnabled()).thenReturn(true);
        when(driver.findElement(TARGET)).thenReturn(element);

        assertSame(element, action.clickable(actor, TARGET));
    }

    @Test
    void waitForInvisibilityReturnsTrueWhenElementIsAbsent() {
        when(driver.findElement(TARGET)).thenThrow(new NoSuchElementException("gone"));

        assertTrue(action.invisibility(actor, TARGET));
    }

    @Test
    void waitForInvisibilityTimesOutWhenElementStaysVisible() {
        WebElement element = mock(WebElement.class);
        when(element.isDisplayed()).thenReturn(true);
        when(driver.findElement(TARGET)).thenReturn(element);
        action.withTimeout(Duration.ofMillis(100)).withPolling(Duration.ofMillis(10));

        assertThrows(TimeoutException.class, () -> action.invisibility(actor, TARGET));
    }

    @Test
    void staleElementIsIgnoredWhileWaiting() {
        WebElement element = mock(WebElement.class);
        when(driver.findElement(TARGET))
                .thenThrow(new StaleElementReferenceException("stale"))
                .thenReturn(element);

        assertSame(element, action.presence(actor, TARGET));
    }

    @Test
    void waitForConditionKeepsPollingUntilResultIsNonNull() {
        AtomicInteger calls = new AtomicInteger();
        Function<WebDriver, String> condition = d -> calls.incrementAndGet() < 3 ? null : "ready";
        action.withPolling(Duration.ofMillis(10));

        String result = action.condition(actor, condition);

        assertEquals("ready", result);
        assertEquals(3, calls.get());
    }

    @Test
    void waitForConditionTimesOutWhenConditionNeverSatisfied() {
        Function<WebDriver, String> condition = d -> null;
        action.withTimeout(Duration.ofMillis(100)).withPolling(Duration.ofMillis(10));

        assertThrows(TimeoutException.class, () -> action.condition(actor, condition));
    }

    /** Concrete subclass that exposes TimedAction's protected state and wait helpers. */
    static class StubTimedAction extends TimedAction<StubTimedAction> {

        @Override
        protected void perform(Actor actor) {
            // Not exercised: these tests call the helpers directly.
        }

        Duration timeoutValue() {
            return timeout;
        }

        Duration pollingValue() {
            return polling;
        }

        WebElement presence(Actor actor, By locator) {
            return waitForPresence(actor, locator);
        }

        WebElement visibility(Actor actor, By locator) {
            return waitForVisibility(actor, locator);
        }

        WebElement clickable(Actor actor, By locator) {
            return waitForClickable(actor, locator);
        }

        boolean invisibility(Actor actor, By locator) {
            return waitForInvisibility(actor, locator);
        }

        <V> V condition(Actor actor, Function<? super WebDriver, V> condition) {
            return waitForCondition(actor, condition);
        }
    }
}
