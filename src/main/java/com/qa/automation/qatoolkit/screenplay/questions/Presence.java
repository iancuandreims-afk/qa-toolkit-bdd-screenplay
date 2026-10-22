package com.qa.automation.qatoolkit.screenplay.questions;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.Question;
import com.qa.automation.qatoolkit.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;

public class Presence implements Question<Boolean> {
    private final By locator;

    private Presence(By locator) {
        this.locator = locator;
    }

    public static Presence of(By locator) {
        return new Presence(locator);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return !actor.abilityTo(BrowseTheWeb.class)
                .getDriver()
                .findElements(locator)
                .isEmpty();
    }
}
