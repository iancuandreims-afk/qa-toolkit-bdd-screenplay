package com.qa.automation.qatoolkit.screenplay.unit_tests.questions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import com.qa.automation.qatoolkit.screenplay.questions.Count;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CountTest {

    private static final By LOCATOR = By.className("item");

    @Test
    void returnsNumberOfMatchingElements() {
        WebDriver driver = mock(WebDriver.class);
        when(driver.findElements(LOCATOR)).thenReturn(List.of(mock(WebElement.class), mock(WebElement.class), mock(WebElement.class)));

        assertEquals(3, Count.of(LOCATOR).answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

    @Test
    void returnsZeroWhenNoElementsMatch() {
        WebDriver driver = mock(WebDriver.class);
        when(driver.findElements(LOCATOR)).thenReturn(List.of());

        assertEquals(0, Count.of(LOCATOR).answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

}
