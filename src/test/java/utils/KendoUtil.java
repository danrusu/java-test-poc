package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.base.StaticClass;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class KendoUtil extends StaticClass {

    public static final By DROPDOWN_LIST_DEFAULT_SELECTOR = By.cssSelector("kendo-dropdownlist");

    public static void selectDropdownListOption(
            WebDriver driver,
            By dropdownListSelector,
            String optionText) {

        var wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        // open dropdown
        wait.until(visibilityOfElementLocated(dropdownListSelector)).click();

        // click option
        String optionXpath = String.format(
                "//kendo-popup//*[normalize-space(text())='%s']",
                optionText
        );
        By optionSelector = By.xpath(optionXpath);
        wait.until(visibilityOfElementLocated(optionSelector)).click();
    }

    public static void selectDropdownListOption(WebDriver driver, String optionText) {
        selectDropdownListOption(
                driver,
                DROPDOWN_LIST_DEFAULT_SELECTOR,
                optionText);
    }

    public static String getSelectedDropdownListOption(WebDriver driver, By dropdownListSelector) {
        return driver.findElement(dropdownListSelector)
                .findElement(By.cssSelector(".k-input-value-text"))
                .getText();
    }

    public static String getSelectedDropdownListOption(WebDriver driver) {
        return getSelectedDropdownListOption(driver, DROPDOWN_LIST_DEFAULT_SELECTOR);
    }
}
