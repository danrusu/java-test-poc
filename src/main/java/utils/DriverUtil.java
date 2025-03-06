package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static java.lang.String.format;

public class DriverUtil {
    public static final int DEFAULT_PAGE_LOAD_TIMEOUT = 30;
    public static final String CHROME_BROWSER = "chrome";
    public static final String FIREFOX_BROWSER = "firefox";

    private DriverUtil() {
    }

    public static WebDriver newDriver() {
        String browser = System.getProperty("browser", CHROME_BROWSER).toLowerCase();
        return switch (browser) {
            case CHROME_BROWSER -> new ChromeDriver();
            case FIREFOX_BROWSER -> new FirefoxDriver();
            default -> throw new RuntimeException(format("Unsupported browser: %s", browser));
        };
    }

    public static void waitForPageToLoad(WebDriver driver, int seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(webDriver -> ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState===\"complete\"")
                );
    }

    public static void waitForPageToLoad(WebDriver driver) {
        waitForPageToLoad(driver, DEFAULT_PAGE_LOAD_TIMEOUT);
    }
}
