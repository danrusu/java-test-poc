package utils.webdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.base.StaticClass;

import java.time.Duration;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
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
            case CHROME_BROWSER -> new ChromeDriver();
            case FIREFOX_BROWSER -> new FirefoxDriver();
            case EDGE_BROWSER -> new EdgeDriver();
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
}
