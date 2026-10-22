package com.qa.automation.qatoolkit.screenplay.unit_tests.abilities_tests;

import com.qa.automation.qatoolkit.screenplay.abilities.TakeScreenshots;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

// TakeScreenshots delegates to the driver's TakesScreenshot interface; no real browser is involved.
class TakeScreenshotsTest {

    private final List<Path> tempFiles = new ArrayList<>();

    private WebDriver driverWithScreenshots() {
        return mock(WebDriver.class, withSettings().extraInterfaces(TakesScreenshot.class));
    }

    @AfterEach
    void cleanUp() throws IOException {
        for (Path path : tempFiles) {
            Files.deleteIfExists(path);
        }
    }

    @Test
    void asBytesReturnsScreenshotBytesFromDriver() {
        WebDriver driver = driverWithScreenshots();
        byte[] bytes = {1, 2, 3};
        when(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)).thenReturn(bytes);

        assertArrayEquals(bytes, TakeScreenshots.using(driver).asBytes());
    }

    @Test
    void asFileReturnsScreenshotFileFromDriver() throws IOException {
        WebDriver driver = driverWithScreenshots();
        File screenshot = File.createTempFile("screenshot", ".png");
        tempFiles.add(screenshot.toPath());
        when(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)).thenReturn(screenshot);

        assertEquals(screenshot, TakeScreenshots.using(driver).asFile());
    }

    @Test
    void saveToCopiesTheScreenshotToTheGivenPath() throws IOException {
        WebDriver driver = driverWithScreenshots();
        File source = File.createTempFile("screenshot", ".png");
        tempFiles.add(source.toPath());
        Files.write(source.toPath(), new byte[] {4, 5, 6});
        when(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)).thenReturn(source);

        Path destination = Files.createTempFile("saved-screenshot", ".png");
        Files.delete(destination);
        tempFiles.add(destination);

        File result = TakeScreenshots.using(driver).saveTo(destination);

        assertEquals(destination.toFile(), result);
        assertArrayEquals(new byte[] {4, 5, 6}, Files.readAllBytes(destination));
    }
}
