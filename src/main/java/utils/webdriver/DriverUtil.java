package utils.webdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnexpectedAlertBehaviour;
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

import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static utils.file.DownloadUtils.getDownloadsPath;
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
        prefs.put("safebrowsing.enabled", true);
        //prefs.put("profile.managed_insecure_content_allowed_for_urls", "[\"\"]");
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
        return new ChromeDriver(options);
    }

    private static WebDriver newCustomEdgeDriver() {
        // latest driver manually downloaded
        String driverPath = Paths.get(
                System.getProperty("user.dir"),
                "drivers",
                "msedgedriver.exe"
        ).toString();
        log("driver:", driverPath);
        System.setProperty("webdriver.edge.driver", driverPath);

        // Download options ???


        EdgeOptions options = new EdgeOptions();
        if (System.getProperty("headless", "false").equalsIgnoreCase("true")) {
            options.addArguments("headless=true");
        }

        loadCustomUserProfile(options);

        //setLocalDownloadsOptions(options);

        return new EdgeDriver(options);
    }

    private static void setLocalDownloadsOptions(EdgeOptions options) {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", getDownloadsPath().toString());
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.directory_upgrade", true);
        options.setExperimentalOption("prefs", prefs);
    }

    private static void loadCustomUserProfile(EdgeOptions options) {
        // load custom user profile
        // path of the profile (User Data folder)
        String userDataFolder = Paths.get("C:",
                        "Users",
                        "Alexandru Mutescul",
                        "AppData",
                        "Local",
                        "Microsoft",
                        "Edge",
                        "User Data")
                .toAbsolutePath()
                .toString();

        options.addArguments("user-data-dir=" + userDataFolder);
        // user profile folder
        options.addArguments("profile-directory=Default");
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
