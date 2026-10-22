package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.Action;
import com.qa.automation.qatoolkit.screenplay.Actor;

public class RefreshPage extends Action {
    public static RefreshPage now() {
        return new RefreshPage();
    }

    @Override
    protected void perform(Actor actor) {
        driver(actor).navigate().refresh();
    }
}
