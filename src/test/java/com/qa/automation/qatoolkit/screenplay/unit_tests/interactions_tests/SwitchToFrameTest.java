package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.mockDriver;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.stubDisplayedAndEnabledElement;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.qa.automation.qatoolkit.screenplay.interactions.SwitchToFrame;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;

class SwitchToFrameTest {

    private static final By FRAME_LOCATOR = By.id("editor-frame");

    @Test
    void switchesToFrameByIndex() {
        WebDriver driver = mockDriver();
        TargetLocator targetLocator = mock(TargetLocator.class);
        when(driver.switchTo()).thenReturn(targetLocator);

        SwitchToFrame.byIndex(2).performAs(actorWithBrowseTheWebAbility(driver));

        verify(targetLocator).frame(2);
    }

    @Test
    void switchesToFrameByLocator() {
        WebDriver driver = mockDriver();
        TargetLocator targetLocator = mock(TargetLocator.class);
        WebElement frame = stubDisplayedAndEnabledElement(driver, FRAME_LOCATOR);
        when(driver.switchTo()).thenReturn(targetLocator);

        SwitchToFrame.byLocator(FRAME_LOCATOR).performAs(actorWithBrowseTheWebAbility(driver));

        verify(targetLocator).frame(frame);
    }

    @Test
    void switchesBackToDefaultContent() {
        WebDriver driver = mockDriver();
        TargetLocator targetLocator = mock(TargetLocator.class);
        when(driver.switchTo()).thenReturn(targetLocator);

        SwitchToFrame.defaultContent().performAs(actorWithBrowseTheWebAbility(driver));

        verify(targetLocator).defaultContent();
    }
}
