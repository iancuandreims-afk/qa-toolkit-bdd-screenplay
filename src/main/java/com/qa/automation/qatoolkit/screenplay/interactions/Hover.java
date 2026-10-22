package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.TimedAction;
import com.qa.automation.qatoolkit.screenplay.Actor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class Hover extends TimedAction<Hover> {
    private final By locator;

    public Hover(By locator) {
        this.locator = locator;
    }

    public static Hover on(By locator) {
        return new Hover(locator);
    }

    @Override
    protected void perform(Actor actor) {
        WebElement element = waitForVisibility(actor, locator);
        new Actions(driver(actor))
                .moveToElement(element)
                .pause(Duration.ofMillis(200))
                .perform();
    }
}
