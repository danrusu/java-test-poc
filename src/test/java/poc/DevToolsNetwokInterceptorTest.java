package poc;

import com.base.UIBaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.http.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.file.FileUtils.getLocalFilePath;

class DevToolsNetwokInterceptorTest extends UIBaseTest {
    public static final Path HTML_LOCAL_PATH = Paths.get(
            "src",
            "test",
            "resources",
            "dev-tools-1.html");
    private static final String EXPECTED_RESULT_TEXT = "hello";

    @Test
    public void interceptNetwork() {

        WebDriver augumentedDriver = new Augmenter().augment(driver);
        DevTools devTools = ((HasDevTools) augumentedDriver).getDevTools();
        devTools.createSession();

        Predicate<HttpRequest> urlMatcher = req -> {
            return req.getMethod().equals(HttpMethod.POST)
                    && req.getUri().contains("https://testutils.com/api/echo");
        };

        HttpHandler mockResponse = req -> new HttpResponse()
                .setStatus(200)
                .addHeader("Content-Type", "application/json")
                .setContent(Contents.asJson(new Object() {
                    String name = "dan";
                }));

        try (NetworkInterceptor interceptor = new NetworkInterceptor(
                augumentedDriver,
                Route.matching(urlMatcher)
                        .to(() -> mockResponse)
        )) {
            Path htmlPath = getLocalFilePath(HTML_LOCAL_PATH);
            driver.get("file://" + htmlPath);

            driver.findElement(By.id("action")).click();


            assertEquals(
                    EXPECTED_RESULT_TEXT,
                    driver.findElement(By.id("result")).getText())
            ;
        }
    }
}