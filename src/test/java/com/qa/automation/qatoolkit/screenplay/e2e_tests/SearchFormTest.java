package com.qa.automation.qatoolkit.screenplay.e2e_tests;

import com.qa.automation.qatoolkit.screenplay.Question;
import com.qa.automation.qatoolkit.screenplay.abilities.BrowseTheWeb;
import com.qa.automation.qatoolkit.screenplay.interactions.*;
import com.qa.automation.qatoolkit.screenplay.questions.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.given;
import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.then;
import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.when;
import static org.openqa.selenium.Keys.ENTER;

class SearchFormTest extends AbstractBaseScreenplayTest {
    private static final String HOME_PAGE = "http://localhost:8080/";

    @Test
    void actorCanTypeIntoSearchInputAndReadValue() {
        given(theUser).wasAbleTo(
                Navigate.to(HOME_PAGE)
        );

        when(theUser).attemptsTo(
                Clear.field(By.id("search-input")),
                Type.text("new value").into(By.id("search-input"))
        );

        then(theUser).shouldSee(
                Value.of(By.id("search-input")), "new value"
        );
    }

    @Test
    void actorCanClearSearchInput() {
        given(theUser).wasAbleTo(
                Navigate.to(HOME_PAGE),
                Type.text("value to be cleared").into(By.id("search-input"))
        );

        when(theUser).attemptsTo(
                Clear.field(By.id("search-input"))
        );

        then(theUser).shouldSee(
                Value.of(By.id("search-input")), ""
        );
    }

    @Test
    void actorCanClickToggleButtonAndCheckVisibility() {
        given(theUser).wasAbleTo(
                Navigate.to(HOME_PAGE)
        );

        when(theUser).attemptsTo(
                Click.on(By.id("toggle-button"))
        );

        then(theUser).shouldSee(
                Visible.of(By.id("status-message"))
        );
    }

    @Test
    void actorCanSubmitSearchByPressingEnterInteraction() {
        given(theUser).wasAbleTo(
                Navigate.to(HOME_PAGE)
        );

        when(theUser).attemptsTo(
                Type.text("test").into(By.id("search-input")),
                PressEnter.on(By.id("search-input")),
                Wait.until(webDriver -> webDriver.getCurrentUrl().contains("search.html"))
        );

        then(theUser).shouldSee(
                Url.contains("search.html")
        );
    }

    @Test
    void actorCanSubmitSearchByPressingEnterChained() {
        given(theUser).wasAbleTo(
                Navigate.to(HOME_PAGE)
        );

        when(theUser).attemptsTo(
                Type.text("test").into(By.id("search-input")).andPressEnter(),
                Wait.until(webDriver -> webDriver.getCurrentUrl().contains("search.html"))
        );

        then(theUser).shouldSee(
                Url.contains("search.html")
        );
    }

    @Test
    void actorCanSubmitSearchByPressingEnterKey() {
        given(theUser).wasAbleTo(
                Navigate.to(HOME_PAGE)
        );

        when(theUser).attemptsTo(
                Type.text("test").into(By.id("search-input")),
                Press.key(ENTER).on(By.id("search-input")),
                Wait.until(webDriver -> webDriver.getCurrentUrl().contains("search.html"))
        );

        then(theUser).shouldSee(
                Url.contains("search.html")
        );
    }

    @Test
    void actorCanPressTabAndFocusNextField() {
        given(theUser).wasAbleTo(
                Navigate.to(HOME_PAGE)
        );

        when(theUser).attemptsTo(
                Type.text("demo").into(By.id("search-input")),
                PressTab.on(By.id("search-input"))
        );

        Question<String> activeElementId = actor -> actor.abilityTo(BrowseTheWeb.class)
                .getDriver()
                .switchTo()
                .activeElement()
                .getAttribute("id");

        then(theUser).shouldSee(
                activeElementId, "search-button"
        );
    }

    @Test
    void actorCanCheckPresenceVisibleAndClickableQuestions() {
        given(theUser).wasAbleTo(
                Navigate.to(HOME_PAGE)
        );

        then(theUser).shouldSee(
                Presence.of(By.id("search-input"))
        );

        then(theUser).shouldSee(
                Visible.of(By.id("search-input"))
        );

        then(theUser).shouldSee(
                Clickable.of(By.id("search-button"))
        );

        then(theUser).shouldSee(
                Clickable.of(By.id("disabled-button")), false
        );
    }
}
