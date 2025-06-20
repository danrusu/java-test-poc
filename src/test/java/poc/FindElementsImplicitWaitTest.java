package poc;

import com.base.UIBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static utils.file.FileUtils.getLocalFilePath;

public class FindElementsImplicitWaitTest extends UIBaseTest {
    public static final Path HTML_LOCAL_PATH = Paths.get(
            "src",
            "test",
            "resources",
            "find-elements-implicit-wait.html");

    @BeforeEach
    void setUp() {
        driver.get("file://" + getLocalFilePath(HTML_LOCAL_PATH));
    }

    @Test
    void testFindElements() {
        implicitlyWait(1);
        var cars = findElements("car");
        assertEquals(2, cars.size());
        var newCars = findElements("new-car"); // is added to DOM after 3 seconds
        assertEquals(0, newCars.size());

        implicitlyWait(2);
        newCars = findElements("new-car"); // is added to DOM after 3 seconds
        assertEquals(1, newCars.size());
    }

    @Test
    void testFindElement() {
        implicitlyWait(1);
        assertThrowsExactly(NoSuchElementException.class, () -> {
            driver.findElement(By.className("new-car"));
        });
    }

    private List<WebElement> findElements(String className) {
        System.out.println("Implicit wait: " +
                driver.manage().timeouts().getImplicitWaitTimeout().getSeconds() +
                " seconds");

        // wait implicitWait and then find elements
        var elements = driver.findElements(By.className(className));

        System.out.println(className + " elements: " + elements.size());
        elements.forEach(elem -> System.out.println(elem.getText()));

        return elements;
    }

    private void implicitlyWait(int seconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
    }
}
