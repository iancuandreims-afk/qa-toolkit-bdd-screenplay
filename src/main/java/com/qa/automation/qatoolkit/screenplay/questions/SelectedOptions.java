package com.qa.automation.qatoolkit.screenplay.questions;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.Question;
import com.qa.automation.qatoolkit.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class SelectedOptions implements Question<List<String>> {
    private final By locator;
    private final SelectionMode mode;

    private SelectedOptions(By locator, SelectionMode mode) {
        this.locator = locator;
        this.mode = mode;
    }

    public static SelectedOptions texts(By locator) {
        return new SelectedOptions(locator, SelectionMode.TEXT);
    }

    public static SelectedOptions values(By locator) {
        return new SelectedOptions(locator, SelectionMode.VALUE);
    }

    @Override
    public List<String> answeredBy(Actor actor) {
        WebElement element = actor.abilityTo(BrowseTheWeb.class)
                .getDriver()
                .findElement(locator);

        Select select = new Select(element);

        return select.getAllSelectedOptions()
                .stream()
                .map(option -> mode == SelectionMode.TEXT
                        ? option.getText()
                        : option.getAttribute("value"))
                .toList();
    }

    private enum SelectionMode {
        TEXT, VALUE
    }
}
