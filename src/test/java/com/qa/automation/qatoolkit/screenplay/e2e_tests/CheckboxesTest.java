package com.qa.automation.qatoolkit.screenplay.e2e_tests;

import com.qa.automation.qatoolkit.screenplay.interactions.Check;
import com.qa.automation.qatoolkit.screenplay.interactions.Click;
import com.qa.automation.qatoolkit.screenplay.interactions.Navigate;
import com.qa.automation.qatoolkit.screenplay.interactions.Uncheck;
import com.qa.automation.qatoolkit.screenplay.questions.Checked;
import com.qa.automation.qatoolkit.screenplay.questions.Selected;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.given;
import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.then;
import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.when;

class CheckboxesTest extends AbstractBaseScreenplayTest {
    private static final String CHECKBOXES_PAGE = "http://localhost:8080/checkboxes.html";

    @Test
    void actorCanCheckAndUncheckCheckboxes() {
        given(theUser).wasAbleTo(
                Navigate.to(CHECKBOXES_PAGE)
        );

        then(theUser).shouldSee(
                Checked.of(By.id("default-checked"))
        );

        when(theUser).attemptsTo(
                Uncheck.box(By.id("default-checked"))
        );

        then(theUser).shouldSee(
                Checked.of(By.id("default-checked")), false
        );

        when(theUser).attemptsTo(
                Check.box(By.id("unchecked-box"))
        );

        then(theUser).shouldSee(
                Checked.of(By.id("unchecked-box"))
        );
    }

    @Test
    void actorCanUseRadioSelectionAndToggleClick() {
        given(theUser).wasAbleTo(
                Navigate.to(CHECKBOXES_PAGE)
        );

        then(theUser).shouldSee(
                Selected.of(By.cssSelector("input[name='choice'][value='first']"))
        );

        when(theUser).attemptsTo(
                Click.on(By.cssSelector("input[name='choice'][value='second']"))
        );

        then(theUser).shouldSee(
                Selected.of(By.cssSelector("input[name='choice'][value='second']"))
        );

        when(theUser).attemptsTo(
                Click.on(By.id("toggle-switch"))
        );

        then(theUser).shouldSee(
                Checked.of(By.id("toggle-switch"))
        );
    }
}
