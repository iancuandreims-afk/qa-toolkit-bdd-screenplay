package com.qa.automation.qatoolkit.screenplay.unit_tests;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.abilities.BrowseTheWeb;
import com.qa.automation.qatoolkit.screenplay.abilities.ManageCookies;
import org.openqa.selenium.WebDriver;

public final class UnitTestActors {

    private UnitTestActors() {
    }

    public static Actor actorWithBrowseTheWebAbility(WebDriver driver) {
        Actor actor = new Actor("Tester");
        actor.can(new BrowseTheWeb(driver));
        return actor;
    }

    public static Actor actorWithCookieManagementAbility(WebDriver driver) {
        Actor actor = new Actor("Tester");
        actor.can(ManageCookies.using(driver));
        return actor;
    }
}
