package com.qa.automation.qatoolkit.screenplay.unit_tests.questions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import com.qa.automation.qatoolkit.screenplay.questions.BrowserWindows;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BrowserWindowsTest {

    @Test
    void countReturnsNumberOfWindowHandles() {
        WebDriver driver = mock(WebDriver.class);
        when(driver.getWindowHandles()).thenReturn(Set.of("window-1", "window-2"));

        assertEquals(2, BrowserWindows.count().answeredBy(actorWithBrowseTheWebAbility(driver)));
    }

}
