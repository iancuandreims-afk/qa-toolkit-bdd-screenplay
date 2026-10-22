package com.qa.automation.qatoolkit.screenplay.unit_tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Interactive;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

public final class UnitTestMocks {

    private UnitTestMocks() {
    }

    public static WebDriver mockDriver() {
        return mock(WebDriver.class);
    }

    public static WebDriver mockDriverWithInputDevices() {
        return mock(WebDriver.class, withSettings().extraInterfaces(Interactive.class));
    }

    public static WebDriver mockDriverWithJavascriptExecutor() {
        return mock(WebDriver.class, withSettings().extraInterfaces(JavascriptExecutor.class));
    }

    public static WebDriver mockDriverWithInputDevicesAndJavascriptExecutor() {
        return mock(WebDriver.class,
                withSettings().extraInterfaces(Interactive.class, JavascriptExecutor.class));
    }

    public static WebElement stubDisplayedAndEnabledElement(WebDriver driver, By locator) {
        WebElement element = mock(WebElement.class);
        when(driver.findElement(locator)).thenReturn(element);
        when(element.isDisplayed()).thenReturn(true);
        when(element.isEnabled()).thenReturn(true);
        return element;
    }
}
