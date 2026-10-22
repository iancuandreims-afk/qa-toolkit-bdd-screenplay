package com.qa.automation.qatoolkit.screenplay.abilities;

import com.qa.automation.qatoolkit.screenplay.Ability;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Ability that captures screenshots of the current page.
 * Create it with {@link #using(WebDriver)}; the driver must implement {@link TakesScreenshot}.
 * {@link #saveTo(Path)} uses {@code Files.copy}, so it fails if the destination already exists.
 */
public class TakeScreenshots implements Ability {
    private final WebDriver driver;

    private TakeScreenshots(WebDriver driver) {
        this.driver = driver;
    }

    public static TakeScreenshots using(WebDriver driver) {
        return new TakeScreenshots(driver);
    }

    public byte[] asBytes() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public File asFile() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }

    public File saveTo(String path) {
        File source = asFile();
        File destination = new File(path);
        try {
            Files.copy(source.toPath(), destination.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Unable to save screenshot to: " + path, e);
        }
        return destination;
    }

    public File saveTo(Path path) {
        return saveTo(path.toString());
    }
}
