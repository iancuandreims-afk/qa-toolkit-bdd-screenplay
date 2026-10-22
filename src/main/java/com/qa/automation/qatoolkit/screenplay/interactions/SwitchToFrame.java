package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.TimedAction;
import com.qa.automation.qatoolkit.screenplay.Actor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;

/**
 * Switches the driver into an iframe, or back to the top-level document.
 * Use {@link #byLocator(By)} to wait for the frame to be present before switching.
 */
public class SwitchToFrame extends TimedAction<SwitchToFrame> {
    private final Type type;
    private final Integer index;
    private final By locator;

    private SwitchToFrame(Type type, Integer index, By locator) {
        this.type = type;
        this.index = index;
        this.locator = locator;
    }

    public static SwitchToFrame byIndex(int index) {
        return new SwitchToFrame(Type.INDEX, index, null);
    }

    public static SwitchToFrame byLocator(By locator) {
        return new SwitchToFrame(Type.LOCATOR, null, locator);
    }

    public static SwitchToFrame defaultContent() {
        return new SwitchToFrame(Type.DEFAULT_CONTENT, null, null);
    }

    @Override
    protected void perform(Actor actor) {
        WebDriver driver = driver(actor);

        switch (type) {
            case INDEX -> driver.switchTo().frame(index);
            case LOCATOR -> {
                WebElement frameElement = waitForPresence(actor, locator);
                driver.switchTo().frame(frameElement);
            }
            case DEFAULT_CONTENT -> driver.switchTo().defaultContent();
            default -> throw new IllegalStateException("Unsupported frame switch type: " + type);
        }
    }

    private enum Type {
        INDEX,
        LOCATOR,
        DEFAULT_CONTENT
    }
}
