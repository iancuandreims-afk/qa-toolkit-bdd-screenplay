package com.qa.automation.qatoolkit.screenplay.questions;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.Question;
import com.qa.automation.qatoolkit.screenplay.abilities.BrowseTheWeb;

/**
 * Answers with the number of open browser windows or tabs. Requires the {@link BrowseTheWeb} ability.
 */
public class BrowserWindows implements Question<Integer> {
    private BrowserWindows() {
    }

    public static BrowserWindows count() {
        return new BrowserWindows();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        return actor.abilityTo(BrowseTheWeb.class)
                .getDriver()
                .getWindowHandles()
                .size();
    }
}
