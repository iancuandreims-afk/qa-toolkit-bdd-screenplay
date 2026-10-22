package com.qa.automation.qatoolkit.screenplay.unit_tests.questions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import com.qa.automation.qatoolkit.screenplay.questions.Selected;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SelectedTest {

    private static final By LOCATOR = By.id("field");

    @Test
    void nonSelectElementUsesIsSelected() {
        WebDriver driver = mock(WebDriver.class);
        WebElement element = mock(WebElement.class);
        when(driver.findElement(LOCATOR)).thenReturn(element);
        when(element.getTagName()).thenReturn("input");
        when(element.isSelected()).thenReturn(true);

        assertTrue(Selected.of(LOCATOR).answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

    @Test
    void selectElementIsSelectedWhenAnOptionIsSelected() {
        WebDriver driver = mock(WebDriver.class);
        WebElement select = mock(WebElement.class);
        WebElement option = mock(WebElement.class);
        when(driver.findElement(LOCATOR)).thenReturn(select);
        when(select.getTagName()).thenReturn("select");
        when(select.findElements(By.tagName("option"))).thenReturn(List.of(option));
        when(option.isSelected()).thenReturn(true);

        assertTrue(Selected.of(LOCATOR).answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

    @Test
    void selectElementIsNotSelectedWhenNoOptionIsSelected() {
        WebDriver driver = mock(WebDriver.class);
        WebElement select = mock(WebElement.class);
        WebElement option = mock(WebElement.class);
        when(driver.findElement(LOCATOR)).thenReturn(select);
        when(select.getTagName()).thenReturn("select");
        when(select.findElements(By.tagName("option"))).thenReturn(List.of(option));
        when(option.isSelected()).thenReturn(false);

        assertFalse(Selected.of(LOCATOR).answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

}
