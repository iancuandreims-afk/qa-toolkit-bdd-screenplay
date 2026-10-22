package com.qa.automation.qatoolkit.screenplay.e2e_tests;

import com.qa.automation.qatoolkit.screenplay.interactions.*;
import com.qa.automation.qatoolkit.screenplay.questions.Text;
import com.qa.automation.qatoolkit.screenplay.questions.Value;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.given;
import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.then;
import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.when;

class TextareaTest extends AbstractBaseScreenplayTest {
    private static final String TEXTAREA_PAGE = "http://localhost:8080/textarea.html";

    @Test
    void actorCanTypeIntoTextareaAndReadValue() {
        given(theUser).wasAbleTo(
                Navigate.to(TEXTAREA_PAGE)
        );

        when(theUser).attemptsTo(
                Type.text("hello from textarea").into(By.id("notes"))
        );

        then(theUser).shouldSee(
                Value.of(By.id("notes")), "hello from textarea"
        );

        then(theUser).shouldSee(
                Text.of(By.id("notes")), "hello from textarea"
        );
    }

    @Test
    void actorCanTypeIntoContentEditableAreaAndReadText() {
        given(theUser).wasAbleTo(
                Navigate.to(TEXTAREA_PAGE)
        );

        when(theUser).attemptsTo(
                Type.text("updated editor text").into(By.id("editor"))
        );

        then(theUser).shouldSee(
                Text.of(By.id("editor")), "updated editor text"
        );
    }

    @Test
    void actorCanTypeMultilineTextIntoTextarea() {
        given(theUser).wasAbleTo(
                Navigate.to(TEXTAREA_PAGE)
        );

        when(theUser).attemptsTo(
                Type.text("line one\nline two").into(By.id("multiline-value"))
        );

        then(theUser).shouldSee(
                Value.of(By.id("multiline-value")), "line one\nline two"
        );

        then(theUser).shouldSee(
                Text.of(By.id("multiline-value")), "line one\nline two"
        );

        when(theUser).attemptsTo(
                PressEnter.on(By.id("multiline-value")),
                Type.text("line three").into(By.id("multiline-value")).andPressEnter(),
                Type.text("line four").into(By.id("multiline-value"))
        );

        then(theUser).shouldSee(
                Text.of(By.id("multiline-value")), "line one\nline two\nline three\nline four"
        );
    }
}
