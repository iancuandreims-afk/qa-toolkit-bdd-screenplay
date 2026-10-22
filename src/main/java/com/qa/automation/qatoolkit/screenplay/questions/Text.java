package com.qa.automation.qatoolkit.screenplay.questions;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.Question;
import com.qa.automation.qatoolkit.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Text implements Question<String> {
    private final By locator;
    private boolean jsFallback = false;

    private Text(By locator) {
        this.locator = locator;
    }

    public static Text of(By locator) {
        return new Text(locator);
    }

    public Text withJsFallback() {
        this.jsFallback = true;
        return this;
    }

    @Override
    public String answeredBy(Actor actor) {
        WebDriver driver = actor.abilityTo(BrowseTheWeb.class).getDriver();
        WebElement element = driver.findElement(locator);

        Object domValue = element.getDomProperty("value");
        if (domValue != null) {
            String value = domValue.toString();
            if (!value.isBlank()) {
                return value;
            }
        }

        String textValue = element.getText();
        if (!textValue.isBlank()) {
            return textValue;
        }

        if (jsFallback) {
            Object scriptValue = ((JavascriptExecutor) driver)
                    .executeScript("return arguments[0].value || arguments[0].innerText || arguments[0].textContent || ''", element);
            if (scriptValue != null) {
                return scriptValue.toString();
            }
        }

        return textValue;
    }
}
