package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.TimedAction;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

/**
 * Simulates dropping a text file onto an element by dispatching a {@code drop} event with a
 * {@code DataTransfer} containing the file. {@link #onto(By)} is required; the action fails without it.
 */
public class DropFile extends TimedAction<DropFile> {
    private final String fileName;
    private String fileContent;
    private By dropZoneLocator;

    private DropFile(String fileName) {
        this.fileName = fileName;
    }

    public static DropFile withName(String fileName) {
        return new DropFile(fileName);
    }

    public DropFile andContent(String fileContent) {
        this.fileContent = fileContent;
        return this;
    }

    public DropFile onto(By dropZoneLocator) {
        this.dropZoneLocator = dropZoneLocator;
        return this;
    }

    @Override
    protected void perform(Actor actor) {
        if (dropZoneLocator == null) {
            throw new IllegalArgumentException("Drop zone locator is required for DropFile");
        }

        JavascriptExecutor js = (JavascriptExecutor) driver(actor);
        js.executeScript(
                "const zone = arguments[0]; " +
                        "const fileName = arguments[1]; " +
                        "const fileContent = arguments[2]; " +
                        "const dt = new DataTransfer(); " +
                        "const file = new File([fileContent], fileName, { type: 'text/plain' }); " +
                        "dt.items.add(file); " +
                        "const event = new DragEvent('drop', { bubbles: true, cancelable: true, dataTransfer: dt }); " +
                        "zone.dispatchEvent(event);",
                waitForVisibility(actor, dropZoneLocator),
                fileName,
                fileContent
        );
    }
}
