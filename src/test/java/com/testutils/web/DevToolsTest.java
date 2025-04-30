package com.testutils.web;

import com.base.UIBaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v131.network.Network;
import org.openqa.selenium.devtools.v131.network.model.Request;
import org.openqa.selenium.devtools.v131.network.model.Response;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static utils.logger.SimpleLogger.log;
import static utils.webdriver.DevToolsUtil.getDevToolsSession;
import static utils.webdriver.DriverUtil.waitForPageToLoad;

class DevToolsTest extends UIBaseTest {
    @Test
    void testRequests() {
        DevTools devTools = getDevToolsSession(driver);
        List<String> urls = new ArrayList<>();
        try {

            devTools.addListener(Network.requestWillBeSent(), requestEvent -> {
                Request request = requestEvent.getRequest();
                urls.add(request.getUrl());
                log(String.format("%s %s", request.getMethod(), request.getUrl()));
            });

            driver.get("https://testutils.com");
            waitForPageToLoad(driver);
            driver.findElement(By.cssSelector("[href=\"/feedback\"]")).click();

        } finally {
            devTools.send(Network.disable());
            devTools.close();
        }

        assertFalse(urls.isEmpty());
    }


    @Test
    void testResponses() {
        DevTools devTools = getDevToolsSession(driver);

        List<Integer> statusCodes = new ArrayList<>();
        try {

            devTools.addListener(Network.responseReceived(), responseReceived -> {
                Response response = responseReceived.getResponse();
                statusCodes.add(response.getStatus());
                log(String.format("%s %s", response.getStatus(), response.getUrl()));
            });

            driver.get("https://testutils.com");
            waitForPageToLoad(driver);
            driver.findElement(By.cssSelector("[href=\"/feedback\"]")).click();

        } finally {
            devTools.send(Network.disable());
            devTools.close();
        }

        statusCodes.forEach(
                statusCode -> assertEquals(200, statusCode)
        );
    }
}