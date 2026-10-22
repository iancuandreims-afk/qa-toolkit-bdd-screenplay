package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.TimedAction;
import com.qa.automation.qatoolkit.screenplay.Actor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SelectBy extends TimedAction<SelectBy> {
    private final String selectorValue;
    private final SelectionType selectionType;
    private By locator;
    private boolean jsFallback = false;
    private static final Logger LOGGER = Logger.getLogger(SelectBy.class.getName());

    private SelectBy(String selectorValue, SelectionType selectionType) {
        this.selectorValue = selectorValue;
        this.selectionType = selectionType;
    }

    public static SelectBy text(String visibleText) {
        return new SelectBy(visibleText, SelectionType.TEXT);
    }

    public static SelectBy value(String value) {
        return new SelectBy(value, SelectionType.VALUE);
    }

    public static SelectBy index(int index) {
        return new SelectBy(String.valueOf(index), SelectionType.INDEX);
    }

    public SelectBy in(By locator) {
        this.locator = locator;
        return this;
    }

    public SelectBy withJsFallback() {
        this.jsFallback = true;
        return this;
    }

    @Override
    protected void perform(Actor actor) {
        WebElement element = waitForVisibility(actor, locator);
        try {
            Select select = new Select(element);
            switch (selectionType) {
                case TEXT -> select.selectByVisibleText(selectorValue);
                case VALUE -> select.selectByValue(selectorValue);
                case INDEX -> select.selectByIndex(Integer.parseInt(selectorValue));
                default -> throw new IllegalStateException("Unsupported selection type: " + selectionType);
            }
        } catch (Exception e) {
            if (jsFallback) {
                LOGGER.log(Level.WARNING, "SelectBy: using JS fallback for actor " + (actor != null ? actor.getName() : "<unknown>") + " on element " + element, e);
                String js;
                switch (selectionType) {
                    case TEXT -> js = "for(var i=0;i<arguments[0].options.length;i++){ if(arguments[0].options[i].text==\"" + selectorValue + "\"){arguments[0].selectedIndex=i; arguments[0].dispatchEvent(new Event('change',{bubbles:true})); break;} }";
                    case VALUE -> js = "arguments[0].value=\"" + selectorValue + "\"; arguments[0].dispatchEvent(new Event('change',{bubbles:true}));";
                    case INDEX -> js = "arguments[0].selectedIndex=" + Integer.parseInt(selectorValue) + "; arguments[0].dispatchEvent(new Event('change',{bubbles:true}));";
                    default -> js = "";
                }
                ((JavascriptExecutor) driver(actor)).executeScript(js, element);
            } else {
                throw e;
            }
        }
    }

    private enum SelectionType {
        TEXT,
        VALUE,
        INDEX
    }
}
