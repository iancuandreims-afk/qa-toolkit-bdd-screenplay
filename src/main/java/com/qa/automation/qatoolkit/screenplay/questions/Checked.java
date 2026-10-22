package com.qa.automation.qatoolkit.screenplay.questions;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.Question;
import com.qa.automation.qatoolkit.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;

public class Checked implements Question<Boolean> {
    private final By locator;

    private Checked(By locator) {
        this.locator = locator;
    }

    public static Checked of(By locator) {
        return new Checked(locator);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return actor.abilityTo(BrowseTheWeb.class)
                .getDriver()
                .findElement(locator)
                .isSelected();
    }
}
