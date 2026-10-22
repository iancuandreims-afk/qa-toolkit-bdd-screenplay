package com.qa.automation.qatoolkit.screenplay.e2e_tests;

import com.qa.automation.qatoolkit.screenplay.interactions.Navigate;
import com.qa.automation.qatoolkit.screenplay.questions.Title;
import com.qa.automation.qatoolkit.screenplay.questions.Url;
import org.junit.jupiter.api.Test;

import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.given;
import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.then;
import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.when;

class LandingPageTest extends AbstractBaseScreenplayTest {
    private static final String HOME_PAGE = "http://localhost:8080/";
    private static final String ABOUT_PAGE = "http://localhost:8080/about.html";

    @Test
    void actorCanOpenHomePage() {
        given(theUser).wasAbleTo(
                Navigate.to(HOME_PAGE));

        then(theUser).shouldSee(
                Title.contains("QA Toolkit Demo Home"));

        then(theUser).shouldSee(
                Url.contains("localhost:8080/"));
    }

    @Test
    void actorCanNavigateToAboutPage() {
        given(theUser).wasAbleTo(
                Navigate.to(HOME_PAGE));

        when(theUser).attemptsTo(
                Navigate.to(ABOUT_PAGE));

        then(theUser).shouldSee(
                Title.contains("About QA Toolkit"));

        then(theUser).shouldSee(
                Url.contains("about.html"));
    }
}
