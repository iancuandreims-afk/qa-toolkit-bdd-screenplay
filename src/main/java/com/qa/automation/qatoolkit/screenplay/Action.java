package com.qa.automation.qatoolkit.screenplay;

import com.qa.automation.qatoolkit.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.*;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base class for Screenplay actions that are executed by an Actor.
 * <p>
 * Use Action for reusable low-level behavior that changes system state, such as clicking,
 * typing, clearing a field, or triggering browser interactions. Action implementations are
 * usually concrete user tasks that can be composed in an Actor's attemptsTo(...) flow.
 * <p>
 * Keep Action focused on one behavior. Prefer composing small actions instead of building
 * large monolithic task classes. The common helpers here provide safe click and sendKeys
 * behavior with optional JS fallback for edge cases.
 */
public abstract class Action implements Performable {

    protected static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(5); // TODO: Extract default timeout/polling into a configurable ScreenplayConfig or BrowseTheWeb settings so projects can override global defaults
    private static final Logger LOGGER = Logger.getLogger(Action.class.getName());

    @Override
    public final void performAs(Actor actor) {
        if (actor == null) {
            throw new IllegalArgumentException("Actor is required");
        }
        perform(actor);
    }

    protected abstract void perform(Actor actor);

    protected final WebDriver driver(Actor actor) {
        return actor.abilityTo(BrowseTheWeb.class).getDriver();
    }

    /**
     * Direct element lookup (no waiting). Use in conjunction with the wait helpers from TimedAction.
     */
    protected final WebElement element(Actor actor, By locator) {
        return driver(actor).findElement(locator);
    }

    /* ------- Small convenience: JS fallbacks (opt-in) ------- */

    /**
     * Try to click; if interaction fails and allowJsFallback==true, fallback to JS click.
     */
    protected final void safeClick(Actor actor, WebElement element, boolean allowJsFallback) {
        try {
            element.click();
        } catch (WebDriverException e) {
            if (allowJsFallback) {
                LOGGER.log(Level.WARNING, "Click via JS fallback for actor " + (actor != null ? actor.getName() : "<unknown>") + " on element " + element, e);
                ((JavascriptExecutor) driver(actor)).executeScript("arguments[0].click();", element);
            } else {
                throw e;
            }
        }
    }

    protected final void safeClick(Actor actor, WebElement element) {
        safeClick(actor, element, false);
    }

    /**
     * Try to send keys; if interaction fails and allowJsFallback==true use JS to set/append value.
     */
    protected final void safeSendKeys(Actor actor, WebElement element, boolean allowJsFallback, CharSequence... keys) {
        try {
            element.sendKeys(keys);
        } catch (WebDriverException e) {
            if (allowJsFallback) {
                StringBuilder sb = new StringBuilder();
                for (CharSequence k : keys) sb.append(k);
                String value = sb.toString().replace("'", "\\'");
                LOGGER.log(Level.WARNING, "SendKeys via JS fallback for actor " + (actor != null ? actor.getName() : "<unknown>") + " on element " + element + " value=" + value, e);
                ((JavascriptExecutor) driver(actor))
                        .executeScript("arguments[0].value = (arguments[0].value || '') + '" + value + "'; arguments[0].dispatchEvent(new Event('input',{bubbles:true}));", element);
            } else {
                throw e;
            }
        }
    }

    protected final void safeSendKeys(Actor actor, WebElement element, CharSequence... keys) {
        safeSendKeys(actor, element, false, keys);
    }
}
