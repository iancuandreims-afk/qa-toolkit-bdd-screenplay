package com.qa.automation.qatoolkit.screenplay.questions;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.Question;
import com.qa.automation.qatoolkit.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;

public class Count implements Question<Integer> {
    private final By locator;

    private Count(By locator) {
        this.locator = locator;
    }

    public static Count of(By locator) {
        return new Count(locator);
    }

    @Override
    public Integer answeredBy(Actor actor) {
        return actor.abilityTo(BrowseTheWeb.class)
                .getDriver()
                .findElements(locator)
                .size();
    }
}
