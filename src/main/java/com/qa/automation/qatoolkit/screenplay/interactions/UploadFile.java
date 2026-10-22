package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.Action;
import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.TimedAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Uploads a file through a file input element.
 * Configure it with {@link #path(String)} and {@link #in(By)} before performing it.
 */
public class UploadFile extends TimedAction<UploadFile> {
    private final String filePath;
    private By locator;
    private boolean jsFallback = false;

    private UploadFile(String filePath) {
        this.filePath = filePath;
    }

    public static UploadFile path(String filePath) {
        return new UploadFile(filePath);
    }

    public UploadFile in(By locator) {
        this.locator = locator;
        return this;
    }

    public UploadFile withJsFallback() {
        this.jsFallback = true;
        return this;
    }

    @Override
    protected void perform(Actor actor) {
        WebElement element = waitForVisibility(actor, locator);
        safeSendKeys(actor, element, jsFallback, filePath);
    }
}
