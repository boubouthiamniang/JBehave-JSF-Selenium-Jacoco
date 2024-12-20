package poc.utils;

import org.openqa.selenium.WebElement;

public class WebDriverUtils {

    /**
     * Clears the input fields before setting new value.
     *
     * @param fields the WebElement representing the username input field
     */
    public static void clearFields(WebElement... fields) {
        for (WebElement field : fields) {
            if (field != null) {
                field.clear();
            }
        }
    }
}
