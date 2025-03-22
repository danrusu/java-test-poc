package com.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import static utils.webdriver.DriverUtil.newDriver;

public class UIBaseTest {
    protected WebDriver driver;

    @BeforeEach
    void beforeEach() {
        driver = newDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    void afterEach() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
