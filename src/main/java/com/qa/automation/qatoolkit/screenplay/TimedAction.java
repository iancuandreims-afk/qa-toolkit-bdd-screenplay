package com.qa.automation.qatoolkit.screenplay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

/**
 * Base class for actions that need deterministic waiting behavior.
 * <p>
 * TimedAction extends Action with timeout and polling configuration for Selenium waits.
 * Use it for interactions that must wait for visibility, clickability, presence, or
 * custom conditions before performing an action.
 * <p>
 * Typical usage includes:
 * - Click.on(locator)
 * - Type.text("...").into(locator)
 * - SelectBy.text("...").in(locator)
 * - HandleCookieConsent.with(ACCEPT)
 *
 * @param <T> concrete action type for fluent API (self-type)
 */
public abstract class TimedAction<T extends TimedAction<T>> extends Action {

    protected Duration timeout = DEFAULT_TIMEOUT;
    protected Duration polling = Duration.ofMillis(200);

    @SuppressWarnings("unchecked")
    public T withTimeout(Duration d) {
        this.timeout = d;
        return (T) this;
    }

    public T withTimeoutSeconds(int s) {
        return withTimeout(Duration.ofSeconds(s));
    }

    @SuppressWarnings("unchecked")
    public T withPolling(Duration p) {
        this.polling = p;
        return (T) this;
    }

    public T withPollingSeconds(int s) {
        return withPolling(Duration.ofSeconds(s));
    }

    protected WebDriverWait configuredWait(Actor actor) {
        return new WebDriverWait(driver(actor), timeout, polling);
    }

    /* Timed helpers that delegate to configuredWait and honor per-action timeout/polling */

    protected WebElement waitForPresence(Actor actor, By locator) {
        return configuredWait(actor).ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected WebElement waitForVisibility(Actor actor, By locator) {
        return configuredWait(actor).ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(Actor actor, By locator) {
        return configuredWait(actor).ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected boolean waitForInvisibility(Actor actor, By locator) {
        return configuredWait(actor).ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected <V> V waitForCondition(Actor actor, Function<? super WebDriver, V> condition) {
        return configuredWait(actor).ignoring(StaleElementReferenceException.class).until(condition);
    }
}
