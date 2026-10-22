package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.interactions.DropFile;
import com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors;
import com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class DropFileTest {

    private static final By DROP_ZONE = By.id("dropzone");

    @Test
    void dispatchesDropEventWithFileNameAndContentOnZone() {
        WebDriver driver = UnitTestMocks.mockDriverWithJavascriptExecutor();
        WebElement zone = UnitTestMocks.stubDisplayedAndEnabledElement(driver, DROP_ZONE);
        Actor actor = UnitTestActors.actorWithBrowseTheWebAbility(driver);

        actor.attemptsTo(DropFile.withName("notes.txt").andContent("hello").onto(DROP_ZONE));

        verify((JavascriptExecutor) driver).executeScript(
                anyString(), eq(zone), eq("notes.txt"), eq("hello"));
    }

    @Test
    void throwsWhenDropZoneIsMissing() {
        WebDriver driver = UnitTestMocks.mockDriverWithJavascriptExecutor();
        Actor actor = UnitTestActors.actorWithBrowseTheWebAbility(driver);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> actor.attemptsTo(DropFile.withName("notes.txt").andContent("hello")));

        verify((JavascriptExecutor) driver, never()).executeScript(anyString(), any(Object[].class));
    }
}
