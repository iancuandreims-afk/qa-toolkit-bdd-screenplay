package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.TimedAction;
import com.qa.automation.qatoolkit.screenplay.Actor;
import org.openqa.selenium.WebDriver;

import java.util.Set;

/**
 * Switches the driver to another browser window or tab, found by index, title, URL, or handle.
 * Title and URL lookups poll until a match appears, then switch to it.
 * {@link #byIndex(int)} relies on the iteration order of {@code getWindowHandles()}, which the WebDriver
 * spec does not guarantee; prefer {@link #byTitle(String)}, {@link #byUrl(String)}, or {@link #byHandle(String)}.
 */
public class SwitchToWindow extends TimedAction<SwitchToWindow> {
    private final Type type;
    private final String value;

    private SwitchToWindow(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public static SwitchToWindow byIndex(int index) {
        return new SwitchToWindow(Type.INDEX, String.valueOf(index));
    }

    public static SwitchToWindow byTitle(String title) {
        return new SwitchToWindow(Type.TITLE, title);
    }

    public static SwitchToWindow byUrl(String url) {
        return new SwitchToWindow(Type.URL, url);
    }

    public static SwitchToWindow byHandle(String handle) {
        return new SwitchToWindow(Type.HANDLE, handle);
    }

    @Override
    protected void perform(Actor actor) {
        WebDriver driver = driver(actor);

        switch (type) {
            case INDEX -> {
                String handle = waitForCondition(actor, d -> {
                    Set<String> handles = d.getWindowHandles();
                    int idx = Integer.parseInt(value);
                    int current = 0;
                    for (String h : handles) {
                        if (current == idx) return h;
                        current++;
                    }
                    return null;
                });
                if (handle == null) throw new IllegalArgumentException("Window index " + value + " was not found");
                driver.switchTo().window(handle);
            }
            case TITLE -> {
                String handle = waitForCondition(actor, d -> {
                    for (String h : d.getWindowHandles()) {
                        d.switchTo().window(h);
                        if (d.getTitle().equals(value)) return h;
                    }
                    return null;
                });
                if (handle == null) throw new IllegalArgumentException("Window with title '" + value + "' was not found");
                driver.switchTo().window(handle);
            }
            case URL -> {
                String handle = waitForCondition(actor, d -> {
                    for (String h : d.getWindowHandles()) {
                        d.switchTo().window(h);
                        if (d.getCurrentUrl().equals(value)) return h;
                    }
                    return null;
                });
                if (handle == null) throw new IllegalArgumentException("Window with URL '" + value + "' was not found");
                driver.switchTo().window(handle);
            }
            case HANDLE -> {
                // immediate check
                if (!driver.getWindowHandles().contains(value)) {
                    throw new IllegalArgumentException("Window handle '" + value + "' was not found");
                }
                driver.switchTo().window(value);
            }
            default -> throw new IllegalStateException("Unsupported window switch type: " + type);
        }
    }

    private enum Type {
        INDEX,
        TITLE,
        URL,
        HANDLE
    }
}
