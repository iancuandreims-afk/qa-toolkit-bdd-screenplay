package com.qa.automation.qatoolkit.screenplay.e2e_tests;

import com.qa.automation.qatoolkit.screenplay.interactions.Click;
import com.qa.automation.qatoolkit.screenplay.interactions.Navigate;
import com.qa.automation.qatoolkit.screenplay.interactions.SwitchToWindow;
import com.qa.automation.qatoolkit.screenplay.questions.BrowserWindows;
import com.qa.automation.qatoolkit.screenplay.questions.Text;
import com.qa.automation.qatoolkit.screenplay.questions.Url;
import org.junit.jupiter.api.Test;

import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.*;
import static org.openqa.selenium.By.id;

class NewWindowTest extends AbstractBaseScreenplayTest {
    private static final String NEW_WINDOW_PAGE = "http://localhost:8080/new-window.html";
    private static final String NEW_WINDOW_TARGET = "http://localhost:8080/new-window-target.html";

    @Test
    void actorCanOpenNewTabAndSwitchToIt() {
        given(theUser).wasAbleTo(
                Navigate.to(NEW_WINDOW_PAGE)
        );

        then(theUser).shouldSee(
                BrowserWindows.count(), 1
        );

        when(theUser).attemptsTo(
                Click.on(id("open-link-tab"))
        );

        then(theUser).shouldSee(
                BrowserWindows.count(), 2
        );

        when(theUser).attemptsTo(
                SwitchToWindow.byUrl(NEW_WINDOW_TARGET)
        );

        then(theUser).shouldSee(
                Url.contains("new-window-target.html")
        );

        then(theUser).shouldSee(
                Text.of(id("target-message")), "This page opened in a separate window/tab."
        );
    }

    @Test
    void actorCanOpenNewWindowFromButtonAndSwitchToIt() {
        given(theUser).wasAbleTo(
                Navigate.to(NEW_WINDOW_PAGE)
        );

        when(theUser).attemptsTo(
                Click.on(id("open-window-button"))
        );

        then(theUser).shouldSee(
                BrowserWindows.count(), 2
        );

        when(theUser).attemptsTo(
                SwitchToWindow.byIndex(1)
        );

        then(theUser).shouldSee(
                Url.contains("new-window-target.html")
        );
    }
}
