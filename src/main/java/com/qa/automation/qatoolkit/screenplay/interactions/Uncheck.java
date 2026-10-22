package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.TimedAction;
import com.qa.automation.qatoolkit.screenplay.Actor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Uncheck extends TimedAction<Uncheck> {
    private final By locator;
    private boolean jsFallback = false;

    public Uncheck(By locator) {
        this.locator = locator;
    }

    public static Uncheck box(By locator) {
        return new Uncheck(locator);
    }

    public Uncheck withJsFallback() {
        this.jsFallback = true;
        return this;
    }

    @Override
    protected void perform(Actor actor) {
        WebElement element = waitForClickable(actor, locator);
        if (element.isSelected()) {
            safeClick(actor, element, jsFallback);
        }
    }
}
