package pom;

import com.base.UIBaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.file.FileUtils.getLocalFilePath;

public class BasePageTest extends UIBaseTest {
    public static final Path HTML_LOCAL_PATH = Paths.get(
            "src",
            "test",
            "resources",
            "find-elements-implicit-wait.html");
    BasePage basePage;

    @BeforeEach
    void setUp() {
        basePage = new BasePage(driver);
        driver.get("file://" + getLocalFilePath(HTML_LOCAL_PATH));
    }

    @AfterEach
    void tearDown() {
        basePage.implicitlyWait(0);
    }

    @Test
    void testFindElementsFromBasePage() {
        basePage.implicitlyWait(10);
        var newCars = basePage.findElements(By.className("new-car"));
        assertEquals(0, newCars.size());
    }

}
