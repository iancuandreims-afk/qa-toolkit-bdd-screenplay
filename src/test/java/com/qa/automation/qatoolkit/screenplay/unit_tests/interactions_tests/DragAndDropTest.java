package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.interactions.DragAndDrop;
import com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors;
import com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Interactive;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class DragAndDropTest {

    private static final By SOURCE = By.id("card");
    private static final By TARGET = By.id("column");

    @Test
    void performsDragFromSourceToTargetViaInputDevices() {
        WebDriver driver = UnitTestMocks.mockDriverWithInputDevices();
        WebElement source = UnitTestMocks.stubDisplayedAndEnabledElement(driver, SOURCE);
        WebElement target = UnitTestMocks.stubDisplayedAndEnabledElement(driver, TARGET);
        Actor actor = UnitTestActors.actorWithBrowseTheWebAbility(driver);

        actor.attemptsTo(DragAndDrop.from(SOURCE).to(TARGET));

        verify((Interactive) driver).perform(any(java.util.Collection.class));
        verify(source, never()).click();
        verify(target, never()).click();
    }

    @Test
    void throwsWhenTargetIsMissing() {
        WebDriver driver = UnitTestMocks.mockDriverWithInputDevices();
        UnitTestMocks.stubDisplayedAndEnabledElement(driver, SOURCE);
        Actor actor = UnitTestActors.actorWithBrowseTheWebAbility(driver);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> actor.attemptsTo(DragAndDrop.from(SOURCE)));

        verify((Interactive) driver, never()).perform(any(java.util.Collection.class));
    }
}
