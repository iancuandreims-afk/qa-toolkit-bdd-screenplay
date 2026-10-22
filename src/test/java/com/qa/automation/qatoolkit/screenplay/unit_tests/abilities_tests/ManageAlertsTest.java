package com.qa.automation.qatoolkit.screenplay.unit_tests.abilities_tests;

import com.qa.automation.qatoolkit.screenplay.abilities.ManageAlerts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// ManageAlerts delegates every call to driver.switchTo().alert().
class ManageAlertsTest {

    private WebDriver.TargetLocator targetLocator;
    private Alert alert;
    private ManageAlerts manageAlerts;

    @BeforeEach
    void setUp() {
        WebDriver driver = mock(WebDriver.class);
        targetLocator = mock(WebDriver.TargetLocator.class);
        alert = mock(Alert.class);
        when(driver.switchTo()).thenReturn(targetLocator);
        when(targetLocator.alert()).thenReturn(alert);
        manageAlerts = ManageAlerts.using(driver);
    }

    @Test
    void acceptAcceptsTheAlert() {
        manageAlerts.accept();

        verify(alert).accept();
    }

    @Test
    void dismissDismissesTheAlert() {
        manageAlerts.dismiss();

        verify(alert).dismiss();
    }

    @Test
    void textReturnsTheAlertText() {
        when(alert.getText()).thenReturn("Are you sure?");

        assertEquals("Are you sure?", manageAlerts.text());
    }

    @Test
    void typeSendsKeysToTheAlert() {
        manageAlerts.type("hello");

        verify(alert).sendKeys("hello");
    }
}
