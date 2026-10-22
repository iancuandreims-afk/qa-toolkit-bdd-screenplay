package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.Action;
import com.qa.automation.qatoolkit.screenplay.Actor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

/**
 * Waits for an element state or a custom {@link ExpectedCondition} before the scenario continues.
 * The default is a 10-second wait for the element to be visible; override it with
 * {@link #forSeconds(int)} and the state with {@link #isClickable()} or {@link #isPresent()}.
 */
public class Wait extends Action {
    private final ExpectedCondition<Boolean> conditionCallback;
    private final By locator;
    private Condition condition = Condition.VISIBLE;
    private Duration timeout = Duration.ofSeconds(10);

    private Wait(By locator) {
        this.locator = locator;
        this.conditionCallback = null;
    }

    private Wait(ExpectedCondition<Boolean> conditionCallback) {
        this.locator = null;
        this.conditionCallback = conditionCallback;
    }

    public static Wait until(By locator) {
        return new Wait(locator);
    }

    public static Wait until(ExpectedCondition<Boolean> condition) {
        return new Wait(condition);
    }

    public static Wait forPageLoad() {
        return new Wait(webDriver ->
                Objects.requireNonNull(((JavascriptExecutor) webDriver)
                                .executeScript("return document.readyState"))
                        .equals("complete")
        );
    }

    public Wait isVisible() {
        this.condition = Condition.VISIBLE;
        return this;
    }

    public Wait isClickable() {
        this.condition = Condition.CLICKABLE;
        return this;
    }

    public Wait isPresent() {
        this.condition = Condition.PRESENT;
        return this;
    }

    public Wait forSeconds(int seconds) {
        this.timeout = Duration.ofSeconds(seconds);
        return this;
    }

    @Override
    protected void perform(Actor actor) {
        WebDriver driver = driver(actor);
        WebDriverWait wait = new WebDriverWait(driver, timeout);

        if (conditionCallback != null) {
            wait.until(conditionCallback);
            return;
        }

        if (locator == null) {
            throw new IllegalStateException("No wait condition provided.");
        }

        switch (condition) {
            case VISIBLE -> wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            case CLICKABLE -> wait.until(ExpectedConditions.elementToBeClickable(locator));
            case PRESENT -> wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            default -> throw new IllegalStateException("Unsupported wait condition: " + condition);
        }
    }

    private enum Condition {
        VISIBLE,
        CLICKABLE,
        PRESENT
    }
}
