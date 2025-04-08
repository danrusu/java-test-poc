package poc;

import com.base.UIBaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.webdriver.DriverUtil.waitForPageToLoad;

class LoadingPageTest2 extends UIBaseTest {
    public static Path HTML_LOCAL_PATH = Paths.get(
            "src",
            "test",
            "resources",
            "loading.html");

    @Test
    void testLoading() {
        String userDir = System.getProperty("user.dir");
        Path htmlPath = Path.of(userDir).resolve(HTML_LOCAL_PATH);

        driver.get("file://" + htmlPath);

        waitForPageToLoad(driver);

        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .pollingEvery(Duration.ofSeconds(1))
                .withTimeout(Duration.ofSeconds(10));

        driver.findElement(By.id("load")).click();

        By loadingAlert = By.cssSelector("[role=alert]");
        wait.until(driver -> {
            System.out.println("waiting for loading alert to appear");
            List<WebElement> alerts = driver.findElements(loadingAlert);
            return alerts.size() == 1;
        });

        System.out.println("loading alert appeared");

        wait.until(driver -> {
            System.out.println("waiting for loasing alert to disappear");
            List<WebElement> alerts = driver.findElements(loadingAlert);
            return alerts.isEmpty();
        });

        System.out.println("loading alert disappeared");

        assertEquals("loaded", driver.findElement(By.id("status")).getText());
    }

}