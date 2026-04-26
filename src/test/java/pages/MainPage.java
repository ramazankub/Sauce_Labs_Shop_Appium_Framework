package pages;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;
import utils.NavigationUiHelper;

import static com.codeborne.selenide.appium.SelenideAppium.$;
public class MainPage {
    private final NavigationUiHelper navigationUiHelper = new NavigationUiHelper();

    private final SelenideElement burgerMenu = $(AppiumBy.accessibilityId("test-Menu"));

    public MainPage checkIsOpened() {
        navigationUiHelper.checkElementVisible(burgerMenu);
        return this;
    }
}
