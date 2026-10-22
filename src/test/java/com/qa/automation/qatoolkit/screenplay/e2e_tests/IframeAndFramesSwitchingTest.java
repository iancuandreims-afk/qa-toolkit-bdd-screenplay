package com.qa.automation.qatoolkit.screenplay.e2e_tests;

import com.qa.automation.qatoolkit.screenplay.interactions.Click;
import com.qa.automation.qatoolkit.screenplay.interactions.Navigate;
import com.qa.automation.qatoolkit.screenplay.interactions.SwitchToFrame;
import com.qa.automation.qatoolkit.screenplay.interactions.Type;
import com.qa.automation.qatoolkit.screenplay.questions.Text;
import org.junit.jupiter.api.Test;

import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.given;
import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.then;
import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.when;
import static org.openqa.selenium.By.id;

public class IframeAndFramesSwitchingTest extends AbstractBaseScreenplayTest {
    private static final String FRAMES_PAGE = "http://localhost:8080/frames-page.html";
    private static final String IFRAME_PAGE = "http://localhost:8080/iframe-page.html";

    @Test
    void actorCanTypeAndClickInsideIframe() {
        given(theUser).wasAbleTo(
                Navigate.to(IFRAME_PAGE)
        );

        when(theUser).attemptsTo(
                SwitchToFrame.byLocator(id("demo-frame"))
        );

        when(theUser).attemptsTo(
                Type.text("hello frame").into(id("frame-input")),
                Click.on(id("frame-button"))
        );

        then(theUser).shouldSee(
                Text.of(id("frame-status")), "submitted:hello frame"
        );

        when(theUser).attemptsTo(
                SwitchToFrame.defaultContent()
        );

        then(theUser).shouldSee(
                Text.of(id("outside-text")), "This text is outside the iframe."
        );
    }

    @Test
    void actorCanSwitchToFramesByLocatorAndByIndex() {
        given(theUser).wasAbleTo(
                Navigate.to(FRAMES_PAGE)
        );

        when(theUser).attemptsTo(
                SwitchToFrame.byLocator(id("first-frame"))
        );

        when(theUser).attemptsTo(
                Type.text("first value").into(id("first-input")),
                Click.on(id("first-button"))
        );

        then(theUser).shouldSee(
                Text.of(id("first-status")), "first:first value"
        );

        when(theUser).attemptsTo(
                SwitchToFrame.defaultContent(),
                SwitchToFrame.byIndex(1)
        );

        when(theUser).attemptsTo(
                Type.text("second value").into(id("second-input")),
                Click.on(id("second-button"))
        );

        then(theUser).shouldSee(
                Text.of(id("second-status")), "second:second value"
        );
    }
}
