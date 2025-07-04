package pom.kendo;

import com.base.UIBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DropdownListTest extends UIBaseTest {
    public static final String KENDO_DROPDOWN_LIST_URL =
            "https://demos.telerik.com/kendo-angular-ui/demos/dropdowns/dropdownlist/basic_usage" +
                    "?theme=default-ocean-blue-a11y";
    public DropDownList kendoWebComponent;

    @BeforeEach
    void beforeEach() {
        kendoWebComponent = new DropDownList(driver);
    }

    @Test
    void testOptionSelect() {
        final String optionToSelect = "Boston";
        driver.get(KENDO_DROPDOWN_LIST_URL);
        kendoWebComponent.selectDropdownListOption(optionToSelect);
        assertEquals(
                optionToSelect,
                kendoWebComponent.getSelectedDropdownListOption()
        );
    }

    //    https://www.telerik.com/kendo-angular-ui/components/inputs/formfield
    @Test
    void testFormFieldRadio() {
    }
}
