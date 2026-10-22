package com.qa.automation.qatoolkit.screenplay.unit_tests.questions_tests;

import com.qa.automation.qatoolkit.screenplay.questions.CookieValue;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithCookieManagementAbility;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CookieValueTest {

    @Test
    void returnsValueOfNamedCookie() {
        WebDriver driver = mock(WebDriver.class, org.mockito.Mockito.RETURNS_DEEP_STUBS);
        when(driver.manage().getCookieNamed("session")).thenReturn(new Cookie("session", "abc123"));

        assertEquals("abc123", CookieValue.of("session").answeredBy(actorWithCookieManagementAbility(driver)));
    }

    @Test
    void returnsNullWhenCookieDoesNotExist() {
        WebDriver driver = mock(WebDriver.class, org.mockito.Mockito.RETURNS_DEEP_STUBS);
        when(driver.manage().getCookieNamed("session")).thenReturn(null);

        assertNull(CookieValue.of("session").answeredBy(actorWithCookieManagementAbility(driver)));
    }
}
