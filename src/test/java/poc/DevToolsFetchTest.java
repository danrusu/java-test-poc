package poc;

import com.base.UIBaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v136.fetch.Fetch;
import org.openqa.selenium.devtools.v136.fetch.model.HeaderEntry;
import org.openqa.selenium.devtools.v136.fetch.model.RequestId;
import org.openqa.selenium.devtools.v136.network.model.Headers;
import org.openqa.selenium.devtools.v136.network.model.Request;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.file.FileUtils.getLocalFilePath;
import static utils.logger.SimpleLogger.log;
import static utils.webdriver.DevToolsUtil.enableFetch;
import static utils.webdriver.DevToolsUtil.getDevToolsSession;

class DevToolsFetchTest extends UIBaseTest {
    public static final Path HTML_LOCAL_PATH = Paths.get(
            "src",
            "test",
            "resources",
            "dev-tools-1.html");
    private static final String EXPECTED_RESULT_TEXT = "hello";

    @Test
    void testResultWithDevToolsFetch() {
        Path htmlPath = getLocalFilePath(HTML_LOCAL_PATH);
        driver.get("file://" + htmlPath);
        DevTools devTools = getDevToolsSession(driver);
        enableFetch(devTools);

        try {
            devTools.addListener(Fetch.requestPaused(), requestPaused -> {
                Request request = requestPaused.getRequest();
                Optional<Integer> statusCode = requestPaused.getResponseStatusCode();
                String url = request.getUrl();
                String method = request.getMethod();
                Optional<String> postData = request.getPostData();
                Headers headers = request.getHeaders();
                List<HeaderEntry> headersList = headers.entrySet().stream().map(
                        header -> new HeaderEntry(
                                header.getKey(), header.getValue().toString()
                        )
                ).toList();

                RequestId requestId = requestPaused.getRequestId();
                log(String.format(
                        "%s %s %s",
                        statusCode.orElse(0) + "",
                        method,
                        url)
                );

                devTools.send(Fetch.continueRequest(
                                requestId,
                                Optional.of(url),
                                Optional.of(method),
//                        postData,
                                Optional.empty(),
                                Optional.of(headersList),
                                Optional.of(false))
                );
            });

            driver.findElement(By.id("action")).click();
            wait.until((WebDriver driver) -> {
                WebElement resultElement = driver.findElement(By.id("result"));
                return resultElement.getText().equals(EXPECTED_RESULT_TEXT);
            });
        } finally {
            devTools.send(Fetch.disable());
            devTools.close();
        }

        WebElement resultElement = driver.findElement(By.id("result"));
        assertEquals(resultElement.getText(), EXPECTED_RESULT_TEXT);
    }
}