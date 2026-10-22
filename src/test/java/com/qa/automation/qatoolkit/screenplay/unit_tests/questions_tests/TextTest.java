package com.qa.automation.qatoolkit.screenplay.unit_tests.questions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import com.qa.automation.qatoolkit.screenplay.questions.Text;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

class TextTest {

    private static final By LOCATOR = By.id("field");

    @Test
    void returnsDomValueWhenPresent() {
        WebDriver driver = mock(WebDriver.class);
        WebElement element = mock(WebElement.class);
        when(driver.findElement(LOCATOR)).thenReturn(element);
        when(element.getDomProperty("value")).thenReturn("typed");

        assertEquals("typed", Text.of(LOCATOR).answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

    @Test
    void fallsBackToTextWhenDomValueIsBlank() {
        WebDriver driver = mock(WebDriver.class);
        WebElement element = mock(WebElement.class);
        when(driver.findElement(LOCATOR)).thenReturn(element);
        when(element.getDomProperty("value")).thenReturn("   ");
        when(element.getText()).thenReturn("Visible text");

        assertEquals("Visible text", Text.of(LOCATOR).answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

    @Test
    void returnsTextWhenNoDomValueExists() {
        WebDriver driver = mock(WebDriver.class);
        WebElement element = mock(WebElement.class);
        when(driver.findElement(LOCATOR)).thenReturn(element);
        when(element.getText()).thenReturn("Visible text");

        assertEquals("Visible text", Text.of(LOCATOR).answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

    @Test
    void usesJavascriptFallbackWhenDomValueAndTextAreBlank() {
        WebDriver driver = mock(WebDriver.class, withSettings().extraInterfaces(JavascriptExecutor.class));
        WebElement element = mock(WebElement.class);
        when(driver.findElement(LOCATOR)).thenReturn(element);
        when(element.getText()).thenReturn("");
        when(((JavascriptExecutor) driver).executeScript(anyString(), any())).thenReturn("from-js");

        assertEquals("from-js", Text.of(LOCATOR).withJsFallback().answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

}
