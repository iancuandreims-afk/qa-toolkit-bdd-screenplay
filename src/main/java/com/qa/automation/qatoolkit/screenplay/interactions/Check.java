package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.TimedAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Check extends TimedAction<Check> {
    private final By locator;
    private boolean jsFallback = false;

    public Check(By locator) {
        this.locator = locator;
    }

    public static Check box(By locator) {
        return new Check(locator);
    }

    public Check withJsFallback() {
        this.jsFallback = true;
        return this;
    }

    @Override
    protected void perform(Actor actor) {
        WebElement element = waitForClickable(actor, locator);
        if (!element.isSelected()) {
            safeClick(actor, element, jsFallback);
        }
    }
}
