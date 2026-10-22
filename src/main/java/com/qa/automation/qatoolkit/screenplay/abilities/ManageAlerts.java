package com.qa.automation.qatoolkit.screenplay.abilities;

import com.qa.automation.qatoolkit.screenplay.Ability;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

/**
 * Ability that lets an actor interact with browser alert, confirm and prompt dialogs.
 * Create it with {@link #using(WebDriver)}.
 */
public class ManageAlerts implements Ability {
    private final WebDriver driver;

    private ManageAlerts(WebDriver driver) {
        this.driver = driver;
    }

    public static ManageAlerts using(WebDriver driver) {
        return new ManageAlerts(driver);
    }

    public Alert alert() {
        return driver.switchTo().alert();
    }

    public void accept() {
        alert().accept();
    }

    public void dismiss() {
        alert().dismiss();
    }

    public String text() {
        return alert().getText();
    }

    public void type(String text) {
        alert().sendKeys(text);
    }
}
