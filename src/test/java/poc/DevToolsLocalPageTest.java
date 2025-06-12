package poc;

import com.base.UIBaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v136.network.Network;
import org.openqa.selenium.devtools.v136.network.model.Request;
import org.openqa.selenium.devtools.v136.network.model.RequestId;
import org.openqa.selenium.devtools.v136.network.model.Response;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.file.FileUtils.getLocalFilePath;
import static utils.logger.SimpleLogger.log;
import static utils.webdriver.DevToolsUtil.enableNetwork;
import static utils.webdriver.DevToolsUtil.getDevToolsSession;

class DevToolsLocalPageTest extends UIBaseTest {
    public static final Path HTML_LOCAL_PATH = Paths.get(
            "src",
            "test",
            "resources",
            "dev-tools-1.html");
    private final String EXPECTED_RESULT_TEXT = "hello";

    @Test
    void testFetchResult() {
        Path htmlPath = getLocalFilePath(HTML_LOCAL_PATH);
        driver.get("file://" + htmlPath);

        findElement(By.id("action")).click();
        WebElement resultElement = findElement(By.id("result"));

        wait.until((WebDriver driver) ->
                resultElement.getText().equals(EXPECTED_RESULT_TEXT));
    }

    @Test
    void testResultWithDevToolsNetwork() {
        Path htmlPath = getLocalFilePath(HTML_LOCAL_PATH);
        driver.get("file://" + htmlPath);
        DevTools devTools = getDevToolsSession(driver);
        enableNetwork(devTools);

        Map<String, Map<String, String>> requests = new HashMap<>();
        Map<String, RequestId> requestIds = new HashMap<>();

        try {
            devTools.addListener(Network.requestWillBeSent(), requestWillBeSent -> {
                Request request = requestWillBeSent.getRequest();
                String id = requestWillBeSent.getRequestId().toString();
                requestIds.computeIfAbsent(id, key -> requestWillBeSent.getRequestId());
                requests.computeIfAbsent(id, key -> new HashMap<>());
                requests.computeIfPresent(id, (key, value) -> {
                    value.put("url", request.getUrl());
                    value.put("method", request.getMethod());
                    return value;
                });
            });

            devTools.addListener(Network.responseReceived(), responseReceived -> {
                Response response = responseReceived.getResponse();
                String id = responseReceived.getRequestId().toString();
                requests.computeIfAbsent(id, key -> new HashMap<>());
                requests.computeIfPresent(id, (key, value) -> {
                    value.put("url", response.getUrl());
                    value.put("status", response.getStatus().toString());
                    value.put("mimeType", response.getMimeType());
                    return value;
                });
            });

            findElement(By.id("action")).click();

            AtomicReference<String> requestId = new AtomicReference<>();
            wait.until((WebDriver driver) -> {
                var expectedRequest = requests.entrySet().stream()
                        .filter(request -> {
                            Map<String, String> requestInfo = request.getValue();
                            return requestInfo.get("url").equals("https://testutils" + ".com" +
                                    "/api/echo")
                                    && requestInfo.get("method") != null
                                    && requestInfo.get("method").equals("POST")
                                    && requestInfo.get("status") != null
                                    && requestInfo.get("status").equals("200");
                        })
                        .findAny();
                expectedRequest.ifPresent(
                        request -> requestId.set(request.getKey()));

                return expectedRequest.isPresent();
            });

            RequestId expectedRequestId = requestIds.get(requestId.get());
            String requestData = devTools.send(Network.getRequestPostData(expectedRequestId));
            log("[REQUEST BODY] " + requestData);

            Network.GetResponseBodyResponse responseData =
                    devTools.send(Network.getResponseBody(expectedRequestId));
            log("[RESPONSE BODY] " + responseData.getBody());
        } finally {
            devTools.send(Network.disable());
            devTools.close();
        }

        WebElement resultElement = findElement(By.id("result"));
        assertEquals(resultElement.getText(), EXPECTED_RESULT_TEXT);

        log("\n****** ALL REQUESTS *****");
        requests.forEach((k, v) -> {
            log(String.format("[%s] %s", k, v.toString()));
        });
    }

}