package kendo;

import com.base.UIBaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.KendoUtil.getSelectedDropdownListOption;
import static utils.KendoUtil.selectDropdownListOption;


public class KendoDropdownTest extends UIBaseTest {
    public static String KENDO_DROPDOWN_LIST_URL =
            "https://demos.telerik.com/kendo-angular-ui/demos/dropdowns/dropdownlist/basic_usage" +
                    "?theme=default-ocean-blue-a11y";

    @Test
    void testOptionSelect() {
        final String optionToSelect = "Boston";
        driver.get(KENDO_DROPDOWN_LIST_URL);
        selectDropdownListOption(driver, optionToSelect);
        assertEquals(
                optionToSelect,
                getSelectedDropdownListOption(driver)
        );
    }
}
