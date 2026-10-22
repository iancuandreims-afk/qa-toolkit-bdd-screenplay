package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.abilities.BrowseTheWeb;
import com.qa.automation.qatoolkit.screenplay.interactions.ConsentAction;
import com.qa.automation.qatoolkit.screenplay.interactions.HandleCookieConsent;
import com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors;
import com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HandleCookieConsentTest {

    private static final By BANNER_BUTTON = By.id("consent");

    @Test
    void clicksAcceptButtonWhenBannerIsPresent() {
        WebDriver driver = UnitTestMocks.mockDriver();
        WebElement button = mock(WebElement.class);
        when(button.isDisplayed()).thenReturn(true);
        when(button.isEnabled()).thenReturn(true);
        when(driver.findElements(any(By.class))).thenReturn(List.of(button));
        when(driver.findElement(any(By.class))).thenReturn(button);
        Actor actor = UnitTestActors.actorWithCookieManagementAbility(driver);
        actor.can(new BrowseTheWeb(driver));

        actor.attemptsTo(HandleCookieConsent.with(ConsentAction.ACCEPT).on(BANNER_BUTTON));

        verify(button).click();
    }

    @Test
    void clicksDeclineButtonWhenBannerIsPresent() {
        WebDriver driver = UnitTestMocks.mockDriver();
        WebElement button = mock(WebElement.class);
        when(button.isDisplayed()).thenReturn(true);
        when(button.isEnabled()).thenReturn(true);
        when(driver.findElements(any(By.class))).thenReturn(List.of(button));
        when(driver.findElement(any(By.class))).thenReturn(button);
        Actor actor = UnitTestActors.actorWithCookieManagementAbility(driver);
        actor.can(new BrowseTheWeb(driver));

        actor.attemptsTo(HandleCookieConsent.with(ConsentAction.DECLINE).on(BANNER_BUTTON));

        verify(button).click();
    }

    @Test
    void doesNothingWhenBannerIsAbsent() {
        WebDriver driver = UnitTestMocks.mockDriver();
        when(driver.findElements(any(By.class))).thenReturn(List.of());
        Actor actor = UnitTestActors.actorWithCookieManagementAbility(driver);
        actor.can(new BrowseTheWeb(driver));

        actor.attemptsTo(HandleCookieConsent.with(ConsentAction.ACCEPT).on(BANNER_BUTTON));

        verify(driver, never()).findElement(any(By.class));
    }
}
