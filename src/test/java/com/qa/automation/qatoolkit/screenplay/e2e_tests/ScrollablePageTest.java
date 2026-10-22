package com.qa.automation.qatoolkit.screenplay.e2e_tests;

import com.qa.automation.qatoolkit.screenplay.interactions.Click;
import com.qa.automation.qatoolkit.screenplay.interactions.Navigate;
import com.qa.automation.qatoolkit.screenplay.interactions.ScrollTo;
import com.qa.automation.qatoolkit.screenplay.questions.Visible;
import org.junit.jupiter.api.Test;

import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.*;
import static org.openqa.selenium.By.id;

class ScrollablePageTest extends AbstractBaseScreenplayTest {
    private static final String SCROLLABLE_PAGE = "http://localhost:8080/scrollable-page.html";

    @Test
    void actorCanScrollToLowerSection() {
        given(theUser).wasAbleTo(
                Navigate.to(SCROLLABLE_PAGE)
        );

        then(theUser).shouldSee(
                Visible.of(id("lower-section")), false
        );

        when(theUser).attemptsTo(
                Click.on(id("scroll-to-lower"))
        );

        then(theUser).shouldSee(
                Visible.of(id("lower-section"))
        );

        when(theUser).attemptsTo(
                ScrollTo.element(id("lower-section"))
        );

        then(theUser).shouldSee(
                Visible.of(id("lower-section"))
        );
    }
}
