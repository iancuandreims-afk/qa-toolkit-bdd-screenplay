package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.TimedAction;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Clear extends TimedAction<Clear> {
    private final By locator;
    private boolean jsFallback = false;
    private static final Logger LOGGER = Logger.getLogger(Clear.class.getName());

    public Clear(By locator) {
        this.locator = locator;
    }

    public static Clear field(By locator) {
        return new Clear(locator);
    }

    public Clear withJsFallback() {
        this.jsFallback = true;
        return this;
    }

    @Override
    protected void perform(Actor actor) {
        WebElement element = waitForVisibility(actor, locator);
        try {
            element.clear();
        } catch (WebDriverException e) {
            if (jsFallback) {
                LOGGER.log(Level.FINE, "Clear via JS fallback for actor " + (actor != null ? actor.getName() : "<unknown>") + " on element " + element, e);
                ((JavascriptExecutor) driver(actor)).executeScript("arguments[0].value = ''; arguments[0].dispatchEvent(new Event('input',{bubbles:true}));", element);
            } else {
                throw e;
            }
        }
    }
}
