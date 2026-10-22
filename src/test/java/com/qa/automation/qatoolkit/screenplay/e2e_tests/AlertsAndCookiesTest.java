package com.qa.automation.qatoolkit.screenplay.e2e_tests;

import com.qa.automation.qatoolkit.screenplay.abilities.ManageAlerts;
import com.qa.automation.qatoolkit.screenplay.abilities.ManageCookies;
import com.qa.automation.qatoolkit.screenplay.interactions.Click;
import com.qa.automation.qatoolkit.screenplay.interactions.HandleCookieConsent;
import com.qa.automation.qatoolkit.screenplay.interactions.Navigate;
import com.qa.automation.qatoolkit.screenplay.questions.CookieValue;
import com.qa.automation.qatoolkit.screenplay.questions.Visible;
import org.junit.jupiter.api.Test;

import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.*;
import static com.qa.automation.qatoolkit.screenplay.interactions.ConsentAction.ACCEPT;
import static org.openqa.selenium.By.id;

class AlertsAndCookiesTest extends AbstractBaseScreenplayTest {
    private static final String ALERTS_AND_COOKIES_PAGE = "http://localhost:8080/alerts-cookies.html";

    @Test
    void actorCanAcceptCookieBannerAndManageCookieState() {
        theUser.can(ManageCookies.using(driver));

        given(theUser).wasAbleTo(
                Navigate.to(ALERTS_AND_COOKIES_PAGE)
        );

        then(theUser).shouldSee(
                Visible.of(id("cookie-banner"))
        );

        when(theUser).attemptsTo(
                HandleCookieConsent.with(ACCEPT).on(id("accept-cookie"))
        );

        then(theUser).shouldSee(
                Visible.of(id("cookie-banner")), false
        );

        then(theUser).shouldSee(
                CookieValue.of("cookie_consent"), "accepted"
        );
    }

    @Test
    void actorCanAcceptAlertAndDismissConfirm() {
        theUser.can(ManageAlerts.using(driver));

        given(theUser).wasAbleTo(
                Navigate.to(ALERTS_AND_COOKIES_PAGE)
        );

        when(theUser).attemptsTo(
                Click.on(id("show-alert"))
        );

        theUser.abilityTo(ManageAlerts.class).accept();

        then(theUser).shouldSee(
                user -> driver
                        .findElement(id("alert-status"))
                        .getText(),
                "alert accepted"
        );

        when(theUser).attemptsTo(
                Click.on(id("show-confirm")));

        theUser.abilityTo(ManageAlerts.class).dismiss();

        then(theUser).shouldSee(
                user -> driver
                        .findElement(id("confirm-status"))
                        .getText(),
                "dismissed");
    }

    @Test
    void actorCanHandlePromptDialog() {
        theUser.can(ManageAlerts.using(driver));

        given(theUser).wasAbleTo(
                Navigate.to(ALERTS_AND_COOKIES_PAGE));

        when(theUser).attemptsTo(
                Click.on(id("show-prompt")));

        theUser.abilityTo(ManageAlerts.class).type("hello from prompt");
        theUser.abilityTo(ManageAlerts.class).accept();

        then(theUser).shouldSee(
                user -> driver
                        .findElement(id("prompt-status"))
                        .getText(),
                "entered:hello from prompt");
    }
}
