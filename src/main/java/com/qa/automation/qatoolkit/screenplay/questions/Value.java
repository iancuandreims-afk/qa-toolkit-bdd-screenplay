package com.qa.automation.qatoolkit.screenplay.questions;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.Question;
import com.qa.automation.qatoolkit.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;

public class Value implements Question<String> {
    private final By locator;

    private Value(By locator) {
        this.locator = locator;
    }

    public static Value of(By locator) {
        return new Value(locator);
    }

    @Override
    public String answeredBy(Actor actor) {
        return actor.abilityTo(BrowseTheWeb.class)
                .getDriver()
                .findElement(locator)
                .getAttribute("value");
    }
}
