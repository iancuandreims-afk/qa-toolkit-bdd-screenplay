package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.TimedAction;
import com.qa.automation.qatoolkit.screenplay.Actor;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ScrollTo extends TimedAction<ScrollTo> {
    private final By locator;
    private boolean jsFallback = false;
    private static final Logger LOGGER = Logger.getLogger(ScrollTo.class.getName());


    public ScrollTo(By locator) {
        this.locator = locator;
    }

    public static ScrollTo element(By locator) {
        return new ScrollTo(locator);
    }

    public ScrollTo withJsFallback() {
        this.jsFallback = true;
        return this;
    }

    @Override
    protected void perform(Actor actor) {
        WebElement element = waitForVisibility(actor, locator);
        try {
            new Actions(driver(actor)).moveToElement(element).perform();
        } catch (WebDriverException e) {
            if (jsFallback) {
                LOGGER.log(
                        Level.WARNING,
                        "ScrollTo: using JS fallback for actor "
                                + (actor != null ? actor.getName() : "<unknown>")
                                + " on element " + element,
                        e
                );
                ((JavascriptExecutor) driver(actor)).executeScript(
                        "arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});",
                        element
                );
            } else {
                throw e;
            }
        }
    }
}
