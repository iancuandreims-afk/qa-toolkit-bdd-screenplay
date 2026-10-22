package com.qa.automation.qatoolkit.screenplay.abilities;

import com.qa.automation.qatoolkit.screenplay.Ability;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

/**
 * Ability that gives an actor a WebDriver session.
 * <p>
 * Factories {@link #withChrome()}, {@link #withFirefox()} and {@link #withEdge()} run headless and
 * resolve the driver binary via WebDriverManager. {@link #withSafari()} only works on macOS and
 * throws {@link UnsupportedOperationException} elsewhere.
 */
public class BrowseTheWeb implements Ability {
    private final WebDriver driver;

    public BrowseTheWeb(WebDriver driver) {
        this.driver = driver;
    }

    public static BrowseTheWeb withChrome() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-blink-features=AutomationControlled");
        return withChrome(options);
    }

    public static BrowseTheWeb withChrome(ChromeOptions options) {
        WebDriverManager.chromedriver().setup();
        return new BrowseTheWeb(new ChromeDriver(options));
    }

    public static BrowseTheWeb withFirefox() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-headless");
        options.addArguments("--disable-blink-features=AutomationControlled");
        return withFirefox(options);
    }

    public static BrowseTheWeb withFirefox(FirefoxOptions options) {
        WebDriverManager.firefoxdriver().setup();
        return new BrowseTheWeb(new FirefoxDriver(options));
    }

    public static BrowseTheWeb withEdge() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-blink-features=AutomationControlled");
        return withEdge(options);
    }

    public static BrowseTheWeb withEdge(EdgeOptions options) {
        WebDriverManager.edgedriver().setup();
        return new BrowseTheWeb(new EdgeDriver(options));
    }

    public static BrowseTheWeb withSafari() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (!osName.contains("mac")) {
            throw new UnsupportedOperationException("Safari driver is only available on macOS");
        }
        return new BrowseTheWeb(new SafariDriver());
    }

    public WebDriver getDriver() {
        return driver;
    }
}
