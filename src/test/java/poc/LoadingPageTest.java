package poc;

import com.base.UIBaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.nio.file.Path;
import java.nio.file.Paths;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBe;

class LoadingPageTest extends UIBaseTest {
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

        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(ofSeconds(10))
                .pollingEvery(ofSeconds(1));

        driver.findElement(By.id("load")).click();

        By loadingAlert = By.cssSelector("[role=alert]");

        wait.until(numberOfElementsToBe(loadingAlert, 1));
        System.out.println("loading alert appeared");

        wait.until(numberOfElementsToBe(loadingAlert, 0));
        System.out.println("loading alert disappeared");

        assertEquals(
                "loaded",
                driver.findElement(By.id("status")).getText()
        );
    }

}