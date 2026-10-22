package com.qa.automation.qatoolkit.screenplay.unit_tests.questions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import com.qa.automation.qatoolkit.screenplay.questions.Value;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValueTest {

    private static final By LOCATOR = By.id("field");

    @Test
    void returnsValueAttributeOfElement() {
        WebDriver driver = mock(WebDriver.class);
        WebElement element = mock(WebElement.class);
        when(driver.findElement(LOCATOR)).thenReturn(element);
        when(element.getAttribute("value")).thenReturn("abc");

        assertEquals("abc", Value.of(LOCATOR).answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

    @Test
    void returnsNullWhenValueAttributeIsMissing() {
        WebDriver driver = mock(WebDriver.class);
        WebElement element = mock(WebElement.class);
        when(driver.findElement(LOCATOR)).thenReturn(element);

        assertNull(Value.of(LOCATOR).answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

}
