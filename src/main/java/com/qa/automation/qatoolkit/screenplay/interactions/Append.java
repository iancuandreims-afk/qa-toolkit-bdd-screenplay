package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.TimedAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Append extends TimedAction<Append> {
    private final String text;
    private By locator;
    private boolean jsFallback = false;

    private Append(String text) {
        this.text = text;
    }

    public static Append value(String text) {
        return new Append(text);
    }

    public Append in(By locator) {
        this.locator = locator;
        return this;
    }

    /**
     * Enable JS fallback for this action (opt-in). When enabled, failed sendKeys will fall back to JS.
     */
    public Append withJsFallback() {
        this.jsFallback = true;
        return this;
    }

    @Override
    protected void perform(Actor actor) {
        WebElement element = waitForVisibility(actor, locator);
        safeSendKeys(actor, element, jsFallback, text);
    }
}
