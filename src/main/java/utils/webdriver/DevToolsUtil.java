package utils.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v131.network.Network;
import org.openqa.selenium.edge.EdgeDriver;
import utils.base.StaticClass;

import java.util.Optional;

public class DevToolsUtil extends StaticClass {
    public static DevTools getDevToolsSession(WebDriver driver) {
        DevTools devTools = getDevTools(driver);
        devTools.createSession();
        devTools.send(Network.enable(
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));

        return devTools;
    }

    private static DevTools getDevTools(WebDriver driver) {
        if (driver instanceof ChromeDriver) {
            return ((ChromeDriver) driver).getDevTools();
        }
        if (driver instanceof EdgeDriver) {
            return ((EdgeDriver) driver).getDevTools();
        }
        throw new DevToolsException();
    }
}
