package com.qa.automation.qatoolkit.screenplay.unit_tests.questions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import com.qa.automation.qatoolkit.screenplay.questions.Presence;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PresenceTest {

    private static final By LOCATOR = By.id("banner");

    @Test
    void returnsTrueWhenElementExists() {
        WebDriver driver = mock(WebDriver.class);
        when(driver.findElements(LOCATOR)).thenReturn(List.of(mock(WebElement.class)));

        assertTrue(Presence.of(LOCATOR).answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

    @Test
    void returnsFalseWhenElementDoesNotExist() {
        WebDriver driver = mock(WebDriver.class);
        when(driver.findElements(LOCATOR)).thenReturn(List.of());

        assertFalse(Presence.of(LOCATOR).answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

}
