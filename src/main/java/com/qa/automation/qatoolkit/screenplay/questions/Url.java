package com.qa.automation.qatoolkit.screenplay.questions;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.Question;
import com.qa.automation.qatoolkit.screenplay.abilities.BrowseTheWeb;

public class Url implements Question<String> {
    private Url() {
    }

    public static Url value() {
        return new Url();
    }

    public static Question<Boolean> contains(String fragment) {
        return actor -> actor.abilityTo(BrowseTheWeb.class)
                .getDriver()
                .getCurrentUrl()
                .contains(fragment);
    }

    @Override
    public String answeredBy(Actor actor) {
        return actor.abilityTo(BrowseTheWeb.class).getDriver().getCurrentUrl();
    }
}
