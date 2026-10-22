package com.qa.automation.qatoolkit.screenplay.e2e_tests;

import com.qa.automation.qatoolkit.screenplay.interactions.Navigate;
import com.qa.automation.qatoolkit.screenplay.questions.Count;
import com.qa.automation.qatoolkit.screenplay.questions.Presence;
import com.qa.automation.qatoolkit.screenplay.questions.Text;
import com.qa.automation.qatoolkit.screenplay.questions.Value;
import com.qa.automation.qatoolkit.screenplay.questions.Visible;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.given;
import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.then;

class ResultsAreaTest extends AbstractBaseScreenplayTest {
    private static final String RESULTS_PAGE = "http://localhost:8080/search.html";

    @Test
    void actorCanReadTextAndValueFromResultArea() {
        given(theUser).wasAbleTo(
                Navigate.to(RESULTS_PAGE)
        );

        then(theUser).shouldSee(
                Text.of(By.id("result-message")), "Results for: test"
        );

        then(theUser).shouldSee(
                Value.of(By.id("search-value")), "test"
        );
    }

    @Test
    void actorCanCountVisibleResultsAndCheckPresence() {
        given(theUser).wasAbleTo(
                Navigate.to(RESULTS_PAGE)
        );

        then(theUser).shouldSee(
                Count.of(By.cssSelector(".result-card")), 3
        );

        then(theUser).shouldSee(
                Presence.of(By.id("results-list"))
        );

        then(theUser).shouldSee(
                Visible.of(By.id("results-list"))
        );
    }
}
