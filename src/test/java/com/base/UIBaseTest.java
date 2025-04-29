package com.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static utils.webdriver.DriverUtil.newDriver;

public class UIBaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    void beforeEach() {
        driver = newDriver();
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void afterEach() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
