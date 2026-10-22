package com.qa.automation.qatoolkit.screenplay.questions;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.Question;
import com.qa.automation.qatoolkit.screenplay.abilities.ManageCookies;
import org.openqa.selenium.Cookie;

/**
 * Answers with the value of a browser cookie, or {@code null} if no cookie with that name exists.
 * Requires the {@link ManageCookies} ability.
 */
public class CookieValue implements Question<String> {
    private final String cookieName;

    private CookieValue(String cookieName) {
        this.cookieName = cookieName;
    }

    public static CookieValue of(String cookieName) {
        return new CookieValue(cookieName);
    }

    @Override
    public String answeredBy(Actor actor) {
        Cookie cookie = actor.abilityTo(ManageCookies.class).get(cookieName);
        return cookie == null ? null : cookie.getValue();
    }
}
