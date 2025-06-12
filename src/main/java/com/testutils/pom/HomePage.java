package com.testutils.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pom.BasePage;

import java.util.List;

public class HomePage extends BasePage {
    public static final String URL = "https://testutils.com";

    private final By pageHeader = By.cssSelector("h1");
    private final By pageDescription = By.cssSelector("h2");
    private final By sections = By.cssSelector("section");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getPageHeader() {
        return findElement(pageHeader).getText();
    }

    public String getPageDescription() {
        return findElement(pageDescription).getText();
    }

    public List<WebElement> getSections() {
        return findElements(sections);
    }
}
