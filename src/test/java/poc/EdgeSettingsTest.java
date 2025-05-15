package poc;

import com.base.UIBaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

class EdgeSettingsTest extends UIBaseTest {
    private static final String EDGE_VERSION_URL = "edge://version";

    private static final List<String> browserSettings = List.of(
            "version",
            "os_type",
            "js_engine",
            "command_line",
            "executable_path",
            "profile_path");

    @Test
    void testResultWithDevToolsFetch() {
        driver.get(EDGE_VERSION_URL);

        Function<WebElement, String> getDomId = infoElement -> infoElement.getDomAttribute("id");
        List<WebElement> browserInfoElements = driver.findElements(By.cssSelector("#inner td[id]"));

        Map<String, String> browserInfo = browserInfoElements.stream()
                .collect(toMap(
                        getDomId,
                        WebElement::getText
                ));

//        browserInfo.forEach((key, value) ->
//                System.out.println(key + " : " + value));

        browserSettings.forEach(browserSetting -> {
            System.out.printf(
                    "%n%s : %s",
                    browserSetting,
                    browserInfo.get(browserSetting)
            );
        });

    }
}