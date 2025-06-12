package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {
    public static final int DEFAULT_EXPLICIT_WAIT_DURATION = 5;
    public final WebDriver driver;
    private final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                this.driver,
                Duration.ofSeconds(DEFAULT_EXPLICIT_WAIT_DURATION));
    }

    public WebDriverWait explicitWait() {
        return wait;
    }

    public WebDriverWait explicitWait(int durationInSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(durationInSeconds));
    }

    public WebElement findElement(By elementSelector) {
        List<WebElement> elements = findElements(elementSelector);
        if (elements.isEmpty()) {
            throw new NoSuchElementException(String.format(
                    "selector %s",
                    elementSelector));
        }
        if (elements.size() > 1) {
            throw new ElementNotUniqueException(String.format(
                    "Found %d elements for selector %s",
                    elements.size(),
                    elementSelector));
        }
        // only one element was found
        return elements.getFirst();
    }

    public List<WebElement> findElements(By elementSelector) {
        return driver.findElements(elementSelector);
    }
}
