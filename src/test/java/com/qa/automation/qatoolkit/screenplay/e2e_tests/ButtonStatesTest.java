package com.qa.automation.qatoolkit.screenplay.e2e_tests;

import com.qa.automation.qatoolkit.screenplay.interactions.Click;
import com.qa.automation.qatoolkit.screenplay.interactions.DoubleClick;
import com.qa.automation.qatoolkit.screenplay.interactions.Hover;
import com.qa.automation.qatoolkit.screenplay.interactions.Navigate;
import com.qa.automation.qatoolkit.screenplay.questions.*;
import org.junit.jupiter.api.Test;

import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.*;
import static org.openqa.selenium.By.id;

class ButtonStatesTest extends AbstractBaseScreenplayTest {
    private static final String BUTTONS_PAGE = "http://localhost:8080/button-states.html";

    @Test
    void actorCanCheckPresenceAndClickabilityState() {
        given(theUser).wasAbleTo(
                Navigate.to(BUTTONS_PAGE)
        );

        then(theUser).shouldSee(
                Clickable.of(id("enabled-link"))
        );

        then(theUser).shouldSee(
                Clickable.of(id("disabled-button")), false
        );

        then(theUser).shouldSee(
                Presence.of(id("hidden-button"))
        );

        then(theUser).shouldSee(
                Visible.of(id("hidden-button")), false
        );

        then(theUser).shouldSee(
                Clickable.of(id("covered-button")), false
        );
    }

    @Test
    void actorCanClickEnabledLinkAndButton() {
        given(theUser).wasAbleTo(
                Navigate.to(BUTTONS_PAGE)
        );

        when(theUser).attemptsTo(
                Click.on(id("enabled-button"))
        );

        then(theUser).shouldSee(
                Value.of(id("click-status")), "clicked"
        );

        when(theUser).attemptsTo(
                Click.on(id("enabled-link"))
        );

        then(theUser).shouldSee(
                Url.contains("about.html")
        );
    }

    @Test
    void actorCanHoverAndDoubleClickElements() {
        given(theUser).wasAbleTo(
                Navigate.to(BUTTONS_PAGE)
        );

        when(theUser).attemptsTo(
                Hover.on(id("hover-target"))
        );

        then(theUser).shouldSee(
                Visible.of(id("hover-message"))
        );

        when(theUser).attemptsTo(
                DoubleClick.on(id("double-click-target"))
        );

        then(theUser).shouldSee(
                Value.of(id("double-click-status")), "double-clicked"
        );
    }
}
