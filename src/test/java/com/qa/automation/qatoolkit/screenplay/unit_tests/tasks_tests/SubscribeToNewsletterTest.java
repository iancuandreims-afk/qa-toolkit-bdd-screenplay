package com.qa.automation.qatoolkit.screenplay.unit_tests.tasks_tests;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.tasks.SubscribeToNewsletter;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.mockDriver;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.stubDisplayedAndEnabledElement;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

// SubscribeToNewsletter is a fixed sequence of Type/Check/Click interactions against known locators.
class SubscribeToNewsletterTest {

    @Test
    void fillsAndSubmitsTheNewsletterFormInOrder() {
        WebDriver driver = mockDriver();
        WebElement firstName = stubDisplayedAndEnabledElement(driver, By.id("first-name"));
        WebElement lastName = stubDisplayedAndEnabledElement(driver, By.id("last-name"));
        WebElement email = stubDisplayedAndEnabledElement(driver, By.id("email"));
        WebElement terms = stubDisplayedAndEnabledElement(driver, By.id("terms"));
        WebElement subscribeButton = stubDisplayedAndEnabledElement(driver, By.id("subscribe-button"));
        when(terms.isSelected()).thenReturn(false);

        Actor actor = actorWithBrowseTheWebAbility(driver);
        SubscribeToNewsletter.withData("Jane", "Doe", "jane.doe@example.com").performAs(actor);

        var inOrder = inOrder(firstName, lastName, email, terms, subscribeButton);
        inOrder.verify(firstName).sendKeys("Jane");
        inOrder.verify(lastName).sendKeys("Doe");
        inOrder.verify(email).sendKeys("jane.doe@example.com");
        inOrder.verify(terms).click();
        inOrder.verify(subscribeButton).click();
    }
}
