package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.TimedAction;
import com.qa.automation.qatoolkit.screenplay.Actor;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class Type extends TimedAction<Type> {
    private final String text;
    private By locator;
    private boolean pressEnter;
    private boolean jsFallback = false;

    private Type(String text) {
        this.text = text;
    }

    public static Type text(String text) {
        return new Type(text);
    }

    public Type into(By locator) {
        this.locator = locator;
        return this;
    }

    public Type andPressEnter() {
        this.pressEnter = true;
        return this;
    }

    public Type withJsFallback() {
        this.jsFallback = true;
        return this;
    }

    @Override
    protected void perform(Actor actor) {
        WebElement el = waitForVisibility(actor, locator);
        try {
            safeSendKeys(actor, el, jsFallback, text);
            if (pressEnter) safeSendKeys(actor, el, jsFallback, Keys.ENTER);
        } catch (WebDriverException e) {
            if (jsFallback) {
                throw e;
            } else {
                throw e;
            }
        }
    }
}
