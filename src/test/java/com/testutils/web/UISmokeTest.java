package com.testutils.web;

import com.testutils.UIBaseTest;
import com.testutils.pom.HomePage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static utils.DriverUtil.waitForPageToLoad;

class UISmokeTest extends UIBaseTest {

    @Test
    @Tag("ui-test")
    @Tag("smoke-test")
    void testHomePageLoads() {
        driver.get("https://testutils.com");

        waitForPageToLoad(driver);

        String title = driver.getTitle();

        HomePage homePage = new HomePage(driver);

        assertAll(
                () -> assertEquals("testutils", title),
                () -> assertEquals("Test Utils", homePage.getPageHeader()),
                () -> assertEquals("Tools & Tips for web app testing",
                        homePage.getPageDescription()),
                () -> assertFalse(() -> homePage.getSections().isEmpty(), "sections are missing")
        );
    }
}
