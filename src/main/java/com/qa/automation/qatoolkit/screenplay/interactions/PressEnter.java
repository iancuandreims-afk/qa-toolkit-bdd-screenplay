package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.TimedAction;
import com.qa.automation.qatoolkit.screenplay.Actor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class PressEnter extends TimedAction<PressEnter> {
    private final By locator;
    private boolean jsFallback = false;

    public PressEnter(By locator) {
        this.locator = locator;
    }

    public static PressEnter on(By locator) {
        return new PressEnter(locator);
    }

    public PressEnter withJsFallback() {
        this.jsFallback = true;
        return this;
    }

    @Override
    protected void perform(Actor actor) {
        WebElement element = waitForVisibility(actor, locator);
        try {
            element.sendKeys(Keys.ENTER);
        } catch (WebDriverException e) {
            if (jsFallback) {
                ((JavascriptExecutor) driver(actor)).executeScript(
                        "var form = arguments[0].form; if (form) { if (form.requestSubmit) { form.requestSubmit(); } else { form.submit(); } } else { arguments[0].dispatchEvent(new KeyboardEvent('keydown', { key: 'Enter', bubbles: true, cancelable: true })); }",
                        element
                );
            } else {
                throw e;
            }
        }
    }
}
