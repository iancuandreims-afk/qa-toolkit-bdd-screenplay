package com.qa.automation.qatoolkit.screenplay.e2e_tests;

import com.qa.automation.qatoolkit.screenplay.interactions.Navigate;
import com.qa.automation.qatoolkit.screenplay.interactions.SelectBy;
import com.qa.automation.qatoolkit.screenplay.questions.Selected;
import com.qa.automation.qatoolkit.screenplay.questions.SelectedOptions;
import com.qa.automation.qatoolkit.screenplay.questions.Value;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.util.List;

import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.given;
import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.then;
import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.when;

class SelectTest extends AbstractBaseScreenplayTest {
    private static final String SELECT_PAGE = "http://localhost:8080/select.html";

    @Test
    void actorCanSelectOptionByTextAndCheckSelectedState() {
        given(theUser).wasAbleTo(
                Navigate.to(SELECT_PAGE)
        );

        when(theUser).attemptsTo(
                SelectBy.text("Romania").in(By.id("country"))
        );

        then(theUser).shouldSee(
                Selected.of(By.id("country"))
        );

        then(theUser).shouldSee(
                SelectedOptions.texts(By.id("country")), List.of("Romania")
        );

        then(theUser).shouldSee(
                Value.of(By.id("country")), "ro"
        );
    }

    @Test
    void actorCanSelectOptionByValueAndCheckMultipleSelectState() {
        given(theUser).wasAbleTo(
                Navigate.to(SELECT_PAGE)
        );

        when(theUser).attemptsTo(
                SelectBy.value("de").in(By.id("country")),
                SelectBy.text("JUnit").in(By.id("multi-select"))
        );

        then(theUser).shouldSee(
                SelectedOptions.texts(By.id("country")), List.of("Germany")
        );

        then(theUser).shouldSee(
                SelectedOptions.values(By.id("multi-select")), List.of("junit")
        );
    }

    @Test
    void actorCanMultiSelectItems() {
        given(theUser).wasAbleTo(
                Navigate.to(SELECT_PAGE)
        );

        when(theUser).attemptsTo(
                SelectBy.text("Java").in(By.id("multi-select")),
                SelectBy.text("JUnit").in(By.id("multi-select"))
        );

        then(theUser).shouldSee(
                SelectedOptions.values(By.id("multi-select")), List.of("java", "junit")
        );

        then(theUser).shouldSee(
                SelectedOptions.texts(By.id("multi-select")), List.of("Java", "JUnit")
        );
    }
}
