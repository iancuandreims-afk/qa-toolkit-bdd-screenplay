package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.TimedAction;
import com.qa.automation.qatoolkit.screenplay.Actor;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class Press extends TimedAction<Press> {
    private final Keys key;
    private By locator;
    private boolean jsFallback = false;

    private Press(Keys key) {
        this.key = key;
    }

    public static Press key(Keys key) {
        return new Press(key);
    }

    public Press on(By locator) {
        this.locator = locator;
        return this;
    }

    public Press withJsFallback() {
        this.jsFallback = true;
        return this;
    }

    @Override
    protected void perform(Actor actor) {
        if (locator == null) {
            throw new IllegalArgumentException("A locator is required when pressing a key");
        }

        WebElement element = waitForVisibility(actor, locator);
        safeSendKeys(actor, element, jsFallback, key);
    }
}
