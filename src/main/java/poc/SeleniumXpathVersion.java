package poc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumXpathVersion {
    public static String getXpathVersion(WebDriver context) {
        try {
            By.xpath("/nobody[@attr=('A'||'')]").findElement(context);
            return "3.0";
        } catch (Exception v3Exception) {
            try {
                By.xpath("/nobody[@attr=lower-case('A')]").findElement(context);
                return "2.0";
            } catch (Exception v2Exception) {
                return "1.0";
            }
        }
    }

    public static void main(String[] args) {
        ChromeDriver driver = new ChromeDriver();
        System.out.printf("Selenium XPATH version: %s", getXpathVersion(driver));
        driver.quit();
    }
}
