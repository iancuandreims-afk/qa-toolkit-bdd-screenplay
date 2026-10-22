package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.TimedAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Clicks the element located by {@link #on(By)}.
 * Call {@link #withJsFallback()} to fall back to a JavaScript click when the normal click is intercepted.
 */
public class Click extends TimedAction<Click> {
    private final By locator;
    private boolean jsFallback = false;

    public Click(By locator) {
        this.locator = locator;
    }

    public static Click on(By locator) {
        return new Click(locator);
    }

    public Click withJsFallback() {
        this.jsFallback = true;
        return this;
    }

    @Override
    protected void perform(Actor actor) {
        WebElement element = waitForClickable(actor, locator);
        safeClick(actor, element, jsFallback);
    }
}
