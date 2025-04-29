package utils.webdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.base.StaticClass;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static utils.file.FileUtils.getDownloadsPath;
import static utils.logger.SimpleLogger.log;

public class DriverUtil extends StaticClass {
    public static final int DEFAULT_PAGE_LOAD_SECONDS_TIMEOUT = 30;
    public static final String CHROME_BROWSER = "chrome";
    public static final String FIREFOX_BROWSER = "firefox";
    public static final String EDGE_BROWSER = "edge";

    // You can set the browser from CLI, i.e. pass -Dbrowser=edge as VM argument
    public static WebDriver newDriver() {
        final String browser = System.getProperty("browser", CHROME_BROWSER).toLowerCase();
        log(format("TESTING ON \"%s\"%n", browser));
        return switch (browser) {
            case CHROME_BROWSER -> newCustomChromeDriver();
            case FIREFOX_BROWSER -> newCustomFirefoxDriver();
            case EDGE_BROWSER -> newCustomEdgeDriver();
            default -> throw new InvalidBrowserException(browser);
        };
    }

    public static void waitForPageToLoad(final WebDriver driver, final int seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(webDriver -> requireNonNull(((JavascriptExecutor) driver)
                        .executeScript("return document.readyState===\"complete\""))
                );
    }

    public static void waitForPageToLoad(final WebDriver driver) {
        waitForPageToLoad(driver, DEFAULT_PAGE_LOAD_SECONDS_TIMEOUT);
    }

    private static WebDriver newCustomChromeDriver() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.default_directory", getDownloadsPath().toString());

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        return new ChromeDriver(options);
    }

    private static WebDriver newCustomEdgeDriver() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", getDownloadsPath().toString());

        EdgeOptions options = new EdgeOptions();
        options.setExperimentalOption("prefs", prefs);

        return new EdgeDriver(options);
    }

    private static WebDriver newCustomFirefoxDriver() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.download.dir", getDownloadsPath().toString());
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/csv, " +
                "text/csv, text/plain,application/octet-stream doc xls pdf txt");

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        return new FirefoxDriver(options);
    }
}
