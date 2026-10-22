package com.qa.automation.qatoolkit.screenplay.unit_tests.questions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import com.qa.automation.qatoolkit.screenplay.questions.Clickable;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// Clickable waits up to 1 second, so the negative test takes about 1 second.
class ClickableTest {

    private static final By LOCATOR = By.id("submit");

    @Test
    void returnsTrueWhenElementIsDisplayedAndEnabled() {
        WebDriver driver = mock(WebDriver.class);
        WebElement element = mock(WebElement.class);
        when(driver.findElement(LOCATOR)).thenReturn(element);
        when(element.isDisplayed()).thenReturn(true);
        when(element.isEnabled()).thenReturn(true);

        assertTrue(Clickable.of(LOCATOR).answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

    @Test
    void returnsFalseWhenElementIsDisabled() {
        WebDriver driver = mock(WebDriver.class);
        WebElement element = mock(WebElement.class);
        when(driver.findElement(LOCATOR)).thenReturn(element);
        when(element.isDisplayed()).thenReturn(true);
        when(element.isEnabled()).thenReturn(false);

        assertFalse(Clickable.of(LOCATOR).answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

}
