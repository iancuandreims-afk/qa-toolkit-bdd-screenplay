package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.TimedAction;
import com.qa.automation.qatoolkit.screenplay.Actor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Clicks a cookie banner's accept or reject button. If no matching button is present,
 * the action returns without failing, so it is safe to use when the banner may not appear.
 * <p>
 * Built-in locators match common English and German labels. Use {@link #on(By)} to target
 * a specific button.
 */
public class HandleCookieConsent extends TimedAction<HandleCookieConsent> {
    private final ConsentAction consentAction;
    private By explicitLocator;
    private boolean jsFallback = false;

    private HandleCookieConsent(ConsentAction action) {
        this.consentAction = action;
    }

    public static HandleCookieConsent with(ConsentAction action) {
        return new HandleCookieConsent(action);
    }

    public HandleCookieConsent on(By locator) {
        this.explicitLocator = locator;
        return this;
    }

    public HandleCookieConsent withJsFallback() {
        this.jsFallback = true;
        return this;
    }

    @Override
    protected void perform(Actor actor) {
        By consentLocator = explicitLocator != null ? explicitLocator : consentLocatorFor(consentAction);
        List<WebElement> buttons = waitForCondition(actor, d -> d.findElements(consentLocator));

        if (buttons == null || buttons.isEmpty()) {
            return;
        }

        WebElement button = waitForClickable(actor, consentLocator);
        safeClick(actor, button, jsFallback);
    }

    private By consentLocatorFor(ConsentAction action) {
        if (action == ConsentAction.ACCEPT) {
            return By.xpath(
                    "//button[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'accept all') or " +
                    "contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'i agree') or " +
                    "contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'alle akzeptieren') or " +
                    "contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'accept')]"
            );
        }

        return By.xpath(
                "//button[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'reject all') or " +
                "contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'no thanks') or " +
                "contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'ablehnen') or " +
                "contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'refuse all')]"
        );
    }
}
