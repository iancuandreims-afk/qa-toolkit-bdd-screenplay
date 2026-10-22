package com.qa.automation.qatoolkit.screenplay.unit_tests.questions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import com.qa.automation.qatoolkit.screenplay.questions.SelectedOptions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SelectedOptionsTest {

    private static final By LOCATOR = By.id("colour");

    @Test
    void textsReturnsTextOfSelectedOptions() {
        WebElement select = selectWithSelectedOption("red", "Red label");
        WebDriver driver = driverReturning(select);

        assertEquals(List.of("Red label"), SelectedOptions.texts(LOCATOR).answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

    @Test
    void valuesReturnsValueAttributeOfSelectedOptions() {
        WebElement select = selectWithSelectedOption("red", "Red label");
        WebDriver driver = driverReturning(select);

        assertEquals(List.of("red"), SelectedOptions.values(LOCATOR).answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

    @Test
    void returnsEmptyListWhenNoOptionIsSelected() {
        WebElement select = mock(WebElement.class);
        WebElement option = mock(WebElement.class);
        when(select.getTagName()).thenReturn("select");
        when(select.findElements(By.tagName("option"))).thenReturn(List.of(option));
        when(option.isSelected()).thenReturn(false);
        WebDriver driver = driverReturning(select);

        assertEquals(List.of(), SelectedOptions.texts(LOCATOR).answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

    private static WebElement selectWithSelectedOption(String value, String text) {
        WebElement select = mock(WebElement.class);
        WebElement option = mock(WebElement.class);
        when(select.getTagName()).thenReturn("select");
        when(select.findElements(By.tagName("option"))).thenReturn(List.of(option));
        when(option.isSelected()).thenReturn(true);
        when(option.getText()).thenReturn(text);
        when(option.getAttribute("value")).thenReturn(value);
        return select;
    }

    private static WebDriver driverReturning(WebElement element) {
        WebDriver driver = mock(WebDriver.class);
        when(driver.findElement(LOCATOR)).thenReturn(element);
        return driver;
    }

}
