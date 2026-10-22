package com.qa.automation.qatoolkit.screenplay.unit_tests.questions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import com.qa.automation.qatoolkit.screenplay.Question;
import com.qa.automation.qatoolkit.screenplay.questions.Url;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UrlTest {

    @Test
    void valueReturnsCurrentUrl() {
        WebDriver driver = mock(WebDriver.class);
        when(driver.getCurrentUrl()).thenReturn("https://example.com/cart");

        assertEquals("https://example.com/cart", Url.value().answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

    @Test
    void containsReturnsTrueWhenFragmentMatches() {
        WebDriver driver = mock(WebDriver.class);
        when(driver.getCurrentUrl()).thenReturn("https://example.com/cart");

        Question<Boolean> question = Url.contains("/cart");

        assertTrue(question.answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

    @Test
    void containsReturnsFalseWhenFragmentDoesNotMatch() {
        WebDriver driver = mock(WebDriver.class);
        when(driver.getCurrentUrl()).thenReturn("https://example.com/cart");

        Question<Boolean> question = Url.contains("/checkout");

        assertFalse(question.answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

}
