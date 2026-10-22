package com.qa.automation.qatoolkit.screenplay.questions;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.Question;
import com.qa.automation.qatoolkit.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Selected implements Question<Boolean> {
    private final By locator;

    private Selected(By locator) {
        this.locator = locator;
    }

    public static Selected of(By locator) {
        return new Selected(locator);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        WebElement element = actor.abilityTo(BrowseTheWeb.class)
                .getDriver()
                .findElement(locator);

        if ("select".equalsIgnoreCase(element.getTagName())) {
            return !new Select(element).getAllSelectedOptions().isEmpty();
        }

        return element.isSelected();
    }
}
