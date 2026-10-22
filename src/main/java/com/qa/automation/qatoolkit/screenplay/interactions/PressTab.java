package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.TimedAction;
import com.qa.automation.qatoolkit.screenplay.Actor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class PressTab extends TimedAction<PressTab> {
    private final By locator;
    private boolean jsFallback = false;

    public PressTab(By locator) {
        this.locator = locator;
    }

    public static PressTab on(By locator) {
        return new PressTab(locator);
    }

    public PressTab withJsFallback() {
        this.jsFallback = true;
        return this;
    }

    @Override
    protected void perform(Actor actor) {
        WebElement element = waitForVisibility(actor, locator);
        try {
            element.sendKeys(Keys.TAB);
        } catch (WebDriverException e) {
            if (jsFallback) {
                ((JavascriptExecutor) driver(actor)).executeScript(
                        "arguments[0].dispatchEvent(new KeyboardEvent('keydown', { key: 'Tab', bubbles: true, cancelable: true }));",
                        element
                );
            } else {
                throw e;
            }
        }
    }
}
