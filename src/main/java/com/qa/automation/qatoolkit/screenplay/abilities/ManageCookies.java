package com.qa.automation.qatoolkit.screenplay.abilities;

import com.qa.automation.qatoolkit.screenplay.Ability;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.Set;

/**
 * Ability that lets an actor read, add and delete browser cookies.
 * Create it with {@link #using(WebDriver)}.
 */
public class ManageCookies implements Ability {
    private final WebDriver driver;

    private ManageCookies(WebDriver driver) {
        this.driver = driver;
    }

    public static ManageCookies using(WebDriver driver) {
        return new ManageCookies(driver);
    }

    public void add(Cookie cookie) {
        driver.manage().addCookie(cookie);
    }

    public void delete(String name) {
        driver.manage().deleteCookieNamed(name);
    }

    public void delete(Cookie cookie) {
        driver.manage().deleteCookie(cookie);
    }

    public void deleteAll() {
        driver.manage().deleteAllCookies();
    }

    public Set<Cookie> all() {
        return driver.manage().getCookies();
    }

    public Cookie get(String name) {
        return driver.manage().getCookieNamed(name);
    }
}
