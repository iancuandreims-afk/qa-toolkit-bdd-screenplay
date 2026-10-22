package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import com.qa.automation.qatoolkit.screenplay.interactions.Type;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.mockDriver;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.stubDisplayedAndEnabledElement;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

// Type waits up to 1 second for visibility before sending keys. It never clears the field.
class TypeTest {

    private static final By LOCATOR = By.name("q");

    @Test
    void typesTextIntoVisibleElement() {
        WebDriver driver = mockDriver();
        WebElement element = stubDisplayedAndEnabledElement(driver, LOCATOR);

        Type.text("selenium").into(LOCATOR).performAs(actorWithBrowseTheWebAbility(driver));

        verify(element).sendKeys("selenium");
        verify(element, never()).sendKeys(Keys.ENTER);
    }

    @Test
    void pressesEnterAfterTextWhenRequested() {
        WebDriver driver = mockDriver();
        WebElement element = stubDisplayedAndEnabledElement(driver, LOCATOR);

        Type.text("selenium").into(LOCATOR).andPressEnter().performAs(actorWithBrowseTheWebAbility(driver));

        verify(element).sendKeys("selenium");
        verify(element).sendKeys(Keys.ENTER);
    }
}
