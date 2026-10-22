package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.TimedAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class DoubleClick extends TimedAction<DoubleClick> {
    private final By locator;

    public DoubleClick(By locator) {
        this.locator = locator;
    }

    public static DoubleClick on(By locator) {
        return new DoubleClick(locator);
    }

    @Override
    protected void perform(Actor actor) {
        WebElement element = waitForClickable(actor, locator);
        new Actions(driver(actor))
                .moveToElement(element)
                .pause(Duration.ofMillis(150))
                .doubleClick(element)
                .perform();
    }
}
