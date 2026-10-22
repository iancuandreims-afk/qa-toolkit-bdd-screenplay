package com.qa.automation.qatoolkit.screenplay.unit_tests.questions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import com.qa.automation.qatoolkit.screenplay.questions.Visible;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// Visible waits up to 1 second, so the negative tests take about 1 second each.
class VisibleTest {

    private static final By LOCATOR = By.id("banner");

    @Test
    void returnsTrueWhenElementIsDisplayed() {
        WebDriver driver = mock(WebDriver.class);
        WebElement element = mock(WebElement.class);
        when(driver.findElement(LOCATOR)).thenReturn(element);
        when(element.isDisplayed()).thenReturn(true);

        assertTrue(Visible.of(LOCATOR).answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

    @Test
    void returnsFalseWhenElementIsNotDisplayed() {
        WebDriver driver = mock(WebDriver.class);
        WebElement element = mock(WebElement.class);
        when(driver.findElement(LOCATOR)).thenReturn(element);
        when(element.isDisplayed()).thenReturn(false);

        assertFalse(Visible.of(LOCATOR).answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

    @Test
    void returnsFalseWhenElementIsNotFound() {
        WebDriver driver = mock(WebDriver.class);
        when(driver.findElement(LOCATOR)).thenThrow(new NoSuchElementException("missing"));

        assertFalse(Visible.of(LOCATOR).answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

}
