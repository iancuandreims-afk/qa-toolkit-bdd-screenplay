package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.TimedAction;
import com.qa.automation.qatoolkit.screenplay.Actor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class DragAndDrop extends TimedAction<DragAndDrop> {
    private final By sourceLocator;
    private By targetLocator;

    public DragAndDrop(By sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    public static DragAndDrop from(By sourceLocator) {
        return new DragAndDrop(sourceLocator);
    }

    public DragAndDrop to(By targetLocator) {
        this.targetLocator = targetLocator;
        return this;
    }

    @Override
    protected void perform(Actor actor) {
        if (targetLocator == null) {
            throw new IllegalArgumentException("Target locator is required for DragAndDrop");
        }

        WebElement source = waitForVisibility(actor, sourceLocator);
        WebElement target = waitForVisibility(actor, targetLocator);
        new Actions(driver(actor)).dragAndDrop(source, target).perform();
    }
}
