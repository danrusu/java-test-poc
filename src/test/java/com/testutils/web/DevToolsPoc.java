package com.testutils.web;

import com.base.UIBaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v131.network.Network;
import org.openqa.selenium.devtools.v131.network.model.Request;
import org.openqa.selenium.devtools.v131.network.model.Response;

import static utils.logger.SimpleLogger.log;
import static utils.webdriver.DevToolsUtil.getDevToolsSession;
import static utils.webdriver.DriverUtil.waitForPageToLoad;

public class DevToolsPoc extends UIBaseTest {
    @Test
    void testDevToolsRequestsListener() {
        DevTools devTools = getDevToolsSession(driver);
        try {

            devTools.addListener(Network.requestWillBeSent(), requestEvent -> {
                Request request = requestEvent.getRequest();
                log(String.format("%s %s", request.getMethod(), request.getUrl()));
            });

            driver.get("https://testutils.com");
            waitForPageToLoad(driver);
            driver.findElement(By.cssSelector("[href=\"/feedback\"]")).click();

        } finally {
            devTools.send(Network.disable());
            devTools.close();
        }
    }

    @Test
    void testDevToolsResponseListener() {
        DevTools devTools = getDevToolsSession(driver);
        try {

            devTools.addListener(Network.responseReceived(), responseReceived -> {
                Response response = responseReceived.getResponse();
                log(String.format("%s %s", response.getStatus(), response.getUrl()));
            });

            driver.get("https://testutils.com");
            waitForPageToLoad(driver);
            driver.findElement(By.cssSelector("[href=\"/feedback\"]")).click();

        } finally {
            devTools.send(Network.disable());
            devTools.close();
        }
    }
}