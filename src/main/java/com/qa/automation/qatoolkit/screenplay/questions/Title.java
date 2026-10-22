package com.qa.automation.qatoolkit.screenplay.questions;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.Question;
import com.qa.automation.qatoolkit.screenplay.abilities.BrowseTheWeb;

public class Title implements Question<String> {
    private Title() {
    }

    public static Title value() {
        return new Title();
    }

    public static Question<Boolean> contains(String fragment) {
        return actor -> actor.abilityTo(BrowseTheWeb.class)
                .getDriver()
                .getTitle()
                .contains(fragment);
    }

    @Override
    public String answeredBy(Actor actor) {
        return actor.abilityTo(BrowseTheWeb.class).getDriver().getTitle();
    }
}
