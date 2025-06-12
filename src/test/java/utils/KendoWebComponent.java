package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class KendoWebComponent {
    public static final By DROPDOWN_LIST_SELECTOR = By.cssSelector("kendo-dropdownlist");
    public static final String DROPDOWN_LIST_POPUP_TAG_SELECTOR = "kendo-popup";

    private final WebDriver driver;
    private final WebDriverWait wait;

    public KendoWebComponent(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void selectDropdownListOption(
            By dropdownListSelector,
            String optionText) {
        wait.until(visibilityOfElementLocated(dropdownListSelector)).click();
        String optionXpath = String.format(
                "//kendo-popup//*[normalize-space(text())='%s']",
                optionText
        );
        By optionSelector = By.xpath(optionXpath);
        wait.until(visibilityOfElementLocated(optionSelector)).click();
    }

    public void selectDropdownListOption(String optionText) {
        selectDropdownListOption(DROPDOWN_LIST_SELECTOR, optionText);
    }

    public String getSelectedDropdownListOption(By dropdownListSelector) {
        return driver.findElement(dropdownListSelector)
                .findElement(By.cssSelector(".k-input-value-text"))
                .getText();
    }

    public String getSelectedDropdownListOption() {
        return getSelectedDropdownListOption(DROPDOWN_LIST_SELECTOR);
    }
}
