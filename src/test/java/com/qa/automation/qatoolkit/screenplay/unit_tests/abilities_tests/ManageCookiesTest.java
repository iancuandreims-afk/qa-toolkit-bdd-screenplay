package com.qa.automation.qatoolkit.screenplay.unit_tests.abilities_tests;

import com.qa.automation.qatoolkit.screenplay.abilities.ManageCookies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// ManageCookies delegates every call to driver.manage().
class ManageCookiesTest {

    private WebDriver.Options options;
    private ManageCookies manageCookies;

    @BeforeEach
    void setUp() {
        WebDriver driver = mock(WebDriver.class);
        options = mock(WebDriver.Options.class);
        when(driver.manage()).thenReturn(options);
        manageCookies = ManageCookies.using(driver);
    }

    @Test
    void addDelegatesToDriverManage() {
        Cookie cookie = new Cookie("session", "abc123");

        manageCookies.add(cookie);

        verify(options).addCookie(cookie);
    }

    @Test
    void deleteByNameDelegatesToDriverManage() {
        manageCookies.delete("session");

        verify(options).deleteCookieNamed("session");
    }

    @Test
    void deleteByCookieDelegatesToDriverManage() {
        Cookie cookie = new Cookie("session", "abc123");

        manageCookies.delete(cookie);

        verify(options).deleteCookie(cookie);
    }

    @Test
    void deleteAllDelegatesToDriverManage() {
        manageCookies.deleteAll();

        verify(options).deleteAllCookies();
    }

    @Test
    void allReturnsCookiesFromDriverManage() {
        Set<Cookie> cookies = Set.of(new Cookie("session", "abc123"));
        when(options.getCookies()).thenReturn(cookies);

        assertSame(cookies, manageCookies.all());
    }

    @Test
    void getReturnsNamedCookieFromDriverManage() {
        Cookie cookie = new Cookie("session", "abc123");
        when(options.getCookieNamed("session")).thenReturn(cookie);

        assertSame(cookie, manageCookies.get("session"));
    }
}
