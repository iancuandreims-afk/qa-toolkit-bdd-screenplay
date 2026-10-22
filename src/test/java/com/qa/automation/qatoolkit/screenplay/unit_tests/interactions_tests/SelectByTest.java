package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.interactions.SelectBy;
import com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors;
import com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ElementClickInterceptedException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SelectByTest {

    private static final By SELECT = By.id("country");

    private WebElement stubSelectElement(WebDriver driver) {
        WebElement select = UnitTestMocks.stubDisplayedAndEnabledElement(driver, SELECT);
        when(select.getTagName()).thenReturn("select");
        WebElement option = mock(WebElement.class);
        when(option.getText()).thenReturn("Romania");
        when(option.getDomAttribute("value")).thenReturn("RO");
        when(option.getAttribute("index")).thenReturn("0");
        when(option.isEnabled()).thenReturn(true);
        when(option.isSelected()).thenReturn(false);
        when(select.findElements(By.tagName("option"))).thenReturn(List.of(option));
        when(select.findElements(By.xpath(".//option[@value = \"RO\"]"))).thenReturn(List.of(option));
        when(select.findElements(By.xpath(".//option[normalize-space(.) = \"Romania\"]"))).thenReturn(List.of(option));
        return select;
    }

    @Test
    void selectsOptionByVisibleText() {
        WebDriver driver = UnitTestMocks.mockDriver();
        WebElement select = stubSelectElement(driver);
        Actor actor = UnitTestActors.actorWithBrowseTheWebAbility(driver);

        actor.attemptsTo(SelectBy.text("Romania").in(SELECT));

        verify(select, atLeastOnce()).findElements(By.xpath(".//option[normalize-space(.) = \"Romania\"]"));
        verify(select, never()).sendKeys(anyString());
    }

    @Test
    void selectsOptionByValue() {
        WebDriver driver = UnitTestMocks.mockDriver();
        WebElement select = stubSelectElement(driver);
        Actor actor = UnitTestActors.actorWithBrowseTheWebAbility(driver);

        actor.attemptsTo(SelectBy.value("RO").in(SELECT));

        verify(select, atLeastOnce()).findElements(By.xpath(".//option[@value = \"RO\"]"));
    }

    @Test
    void selectsOptionByIndex() {
        WebDriver driver = UnitTestMocks.mockDriver();
        WebElement select = stubSelectElement(driver);
        Actor actor = UnitTestActors.actorWithBrowseTheWebAbility(driver);

        actor.attemptsTo(SelectBy.index(0).in(SELECT));

        verify(select, atLeastOnce()).findElements(By.tagName("option"));
    }

    @Test
    void jsFallbackRunsWhenSelectFails() {
        WebDriver driver = UnitTestMocks.mockDriverWithJavascriptExecutor();
        WebElement select = UnitTestMocks.stubDisplayedAndEnabledElement(driver, SELECT);
        when(select.getTagName()).thenReturn("select");
        when(select.findElements(By.tagName("option"))).thenThrow(new ElementClickInterceptedException("not a select"));
        Actor actor = UnitTestActors.actorWithBrowseTheWebAbility(driver);

        actor.attemptsTo(SelectBy.text("Romania").in(SELECT).withJsFallback());

        verify((JavascriptExecutor) driver, times(1)).executeScript(anyString(), any(Object[].class));
    }

    @Test
    void failureWithoutFallbackPropagates() {
        WebDriver driver = UnitTestMocks.mockDriver();
        WebElement select = UnitTestMocks.stubDisplayedAndEnabledElement(driver, SELECT);
        when(select.getTagName()).thenReturn("select");
        when(select.findElements(By.xpath(".//option[normalize-space(.) = \"Romania\"]")))
                .thenThrow(new ElementClickInterceptedException("not a select"));
        Actor actor = UnitTestActors.actorWithBrowseTheWebAbility(driver);

        assertThrows(ElementClickInterceptedException.class,
                () -> actor.attemptsTo(SelectBy.text("Romania").in(SELECT)));
    }
}
