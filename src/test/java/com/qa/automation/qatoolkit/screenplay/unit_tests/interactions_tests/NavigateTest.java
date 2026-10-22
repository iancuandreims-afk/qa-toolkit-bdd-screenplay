package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;

import com.qa.automation.qatoolkit.screenplay.interactions.Navigate;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NavigateTest {

    @Test
    void toLoadsUrl() {
        WebDriver driver = mock(WebDriver.class);

        Navigate.to("https://example.com").performAs(actorWithBrowseTheWebAbility(driver));

        verify(driver).get("https://example.com");
    }

    @Test
    void backUsesBrowserHistory() {
        WebDriver driver = mock(WebDriver.class);
        WebDriver.Navigation navigation = mock(WebDriver.Navigation.class);
        when(driver.navigate()).thenReturn(navigation);

        Navigate.back().performAs(actorWithBrowseTheWebAbility(driver));

        verify(navigation).back();
    }

    @Test
    void forwardUsesBrowserHistory() {
        WebDriver driver = mock(WebDriver.class);
        WebDriver.Navigation navigation = mock(WebDriver.Navigation.class);
        when(driver.navigate()).thenReturn(navigation);

        Navigate.forward().performAs(actorWithBrowseTheWebAbility(driver));

        verify(navigation).forward();
    }
}
