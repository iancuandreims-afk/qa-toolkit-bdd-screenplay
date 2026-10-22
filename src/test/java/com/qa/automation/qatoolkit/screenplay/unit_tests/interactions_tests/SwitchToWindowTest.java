package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.mockDriver;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.qa.automation.qatoolkit.screenplay.interactions.SwitchToWindow;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;

import java.util.LinkedHashSet;
import java.util.Set;

class SwitchToWindowTest {

    private static Set<String> handles(String... values) {
        return new LinkedHashSet<>(java.util.List.of(values));
    }

    @Test
    void switchesToWindowAtIndex() {
        WebDriver driver = mockDriver();
        TargetLocator targetLocator = mock(TargetLocator.class);
        when(driver.switchTo()).thenReturn(targetLocator);
        when(driver.getWindowHandles()).thenReturn(handles("first", "second", "third"));

        SwitchToWindow.byIndex(1).performAs(actorWithBrowseTheWebAbility(driver));

        verify(targetLocator).window("second");
    }

    @Test
    void switchesToWindowByTitle() {
        WebDriver driver = mockDriver();
        TargetLocator targetLocator = mock(TargetLocator.class);
        when(driver.switchTo()).thenReturn(targetLocator);
        when(driver.getWindowHandles()).thenReturn(handles("first", "second"));
        when(driver.getTitle()).thenReturn("Home", "Checkout");

        SwitchToWindow.byTitle("Checkout").performAs(actorWithBrowseTheWebAbility(driver));

        verify(targetLocator, atLeastOnce()).window("second");
    }

    @Test
    void switchesToWindowByUrl() {
        WebDriver driver = mockDriver();
        TargetLocator targetLocator = mock(TargetLocator.class);
        when(driver.switchTo()).thenReturn(targetLocator);
        when(driver.getWindowHandles()).thenReturn(handles("first", "second"));
        when(driver.getCurrentUrl()).thenReturn("https://example.com/", "https://example.com/cart");

        SwitchToWindow.byUrl("https://example.com/cart").performAs(actorWithBrowseTheWebAbility(driver));

        verify(targetLocator, atLeastOnce()).window("second");
    }

    @Test
    void switchesToWindowByHandleWhenPresent() {
        WebDriver driver = mockDriver();
        TargetLocator targetLocator = mock(TargetLocator.class);
        when(driver.switchTo()).thenReturn(targetLocator);
        when(driver.getWindowHandles()).thenReturn(handles("first", "second"));

        SwitchToWindow.byHandle("second").performAs(actorWithBrowseTheWebAbility(driver));

        verify(targetLocator).window("second");
    }

    @Test
    void failsWhenWindowHandleIsAbsent() {
        WebDriver driver = mockDriver();
        when(driver.getWindowHandles()).thenReturn(handles("first"));

        assertThrows(IllegalArgumentException.class,
                () -> SwitchToWindow.byHandle("missing").performAs(actorWithBrowseTheWebAbility(driver)));
    }
}
