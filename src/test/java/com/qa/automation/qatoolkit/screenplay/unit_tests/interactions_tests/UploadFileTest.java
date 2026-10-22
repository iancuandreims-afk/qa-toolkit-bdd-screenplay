package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.interactions.UploadFile;
import com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors;
import com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UploadFileTest {

    private static final By FILE_INPUT = By.id("upload");
    private static final String PATH = "C:\\files\\report.pdf";

    @Test
    void sendsPathToFileInput() {
        WebDriver driver = UnitTestMocks.mockDriver();
        WebElement input = UnitTestMocks.stubDisplayedAndEnabledElement(driver, FILE_INPUT);
        Actor actor = UnitTestActors.actorWithBrowseTheWebAbility(driver);

        actor.attemptsTo(UploadFile.path(PATH).in(FILE_INPUT));

        verify(input).sendKeys(PATH);
    }

    @Test
    void jsFallbackRunsWhenSendKeysFails() {
        WebDriver driver = UnitTestMocks.mockDriverWithJavascriptExecutor();
        WebElement input = UnitTestMocks.stubDisplayedAndEnabledElement(driver, FILE_INPUT);
        when(input.isDisplayed()).thenReturn(true);
        org.mockito.Mockito.doThrow(new ElementClickInterceptedException("blocked"))
                .when(input).sendKeys(anyString());
        Actor actor = UnitTestActors.actorWithBrowseTheWebAbility(driver);

        actor.attemptsTo(UploadFile.path(PATH).in(FILE_INPUT).withJsFallback());

        verify((JavascriptExecutor) driver, times(1)).executeScript(anyString(), any(Object[].class));
    }

    @Test
    void noJsFallbackWhenNotRequested() {
        WebDriver driver = UnitTestMocks.mockDriverWithJavascriptExecutor();
        WebElement input = UnitTestMocks.stubDisplayedAndEnabledElement(driver, FILE_INPUT);
        org.mockito.Mockito.doThrow(new ElementClickInterceptedException("blocked"))
                .when(input).sendKeys(anyString());
        Actor actor = UnitTestActors.actorWithBrowseTheWebAbility(driver);

        org.junit.jupiter.api.Assertions.assertThrows(ElementClickInterceptedException.class,
                () -> actor.attemptsTo(UploadFile.path(PATH).in(FILE_INPUT)));

        verify((JavascriptExecutor) driver, never()).executeScript(anyString(), any(Object[].class));
    }
}
