package com.qa.automation.qatoolkit.screenplay.unit_tests.questions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import com.qa.automation.qatoolkit.screenplay.Question;
import com.qa.automation.qatoolkit.screenplay.questions.Title;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TitleTest {

    @Test
    void valueReturnsPageTitle() {
        WebDriver driver = mock(WebDriver.class);
        when(driver.getTitle()).thenReturn("Home Page");

        assertEquals("Home Page", Title.value().answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

    @Test
    void containsReturnsTrueWhenFragmentMatches() {
        WebDriver driver = mock(WebDriver.class);
        when(driver.getTitle()).thenReturn("Home Page");

        Question<Boolean> question = Title.contains("Home");

        assertTrue(question.answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

    @Test
    void containsReturnsFalseWhenFragmentDoesNotMatch() {
        WebDriver driver = mock(WebDriver.class);
        when(driver.getTitle()).thenReturn("Home Page");

        Question<Boolean> question = Title.contains("Checkout");

        assertFalse(question.answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

}
