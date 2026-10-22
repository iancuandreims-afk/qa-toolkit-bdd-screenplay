package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.abilities.BrowseTheWeb;
import com.qa.automation.qatoolkit.screenplay.interactions.PressEnter;
import com.qa.automation.qatoolkit.screenplay.interactions.Type;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SearchForTaskTest {

    @Test
    void searchForTypesValueAndPressesEnter() {
        WebDriver driver = mock(WebDriver.class);
        WebElement element = mock(WebElement.class);
        Actor actor = new Actor("Tester");
        actor.can(new BrowseTheWeb(driver));

        when(driver.findElement(By.name("q"))).thenReturn(element);
        when(element.isDisplayed()).thenReturn(true);

        actor.attemptsTo(
                Type.text("test").into(By.name("q")),
                PressEnter.on(By.name("q"))
        );

        verify(element).sendKeys("test");
        verify(element).sendKeys(Keys.ENTER);
    }
}
