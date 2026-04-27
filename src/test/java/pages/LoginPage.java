package pages;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;
import testData.errorMessages.ErrorMessage;
import utils.NavigationUiHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final NavigationUiHelper navigationUiHelper = new NavigationUiHelper();

    private final SelenideElement userNameField = $(AppiumBy.accessibilityId("test-Username")),
            passwordField = $(AppiumBy.accessibilityId("test-Password")),
            loginButton = $(AppiumBy.accessibilityId("test-LOGIN")),
            credentialsList = $(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"And the password for all users is:\")"
            ));


    public void login(String name, String password) {
        navigationUiHelper.setDataInField(name, userNameField);
        navigationUiHelper.setDataInField(password, passwordField);
        navigationUiHelper.clickOnElement(loginButton);
    }

    public void pasteCredentialsByPress(String login, String password) {
        navigationUiHelper.scrollDownUntilElementVisible(credentialsList, 4);
        navigationUiHelper.clickOnBtnByText(login);
        navigationUiHelper.scrollUpUntilElementVisible(loginButton,4);
        navigationUiHelper.clickOnElement(loginButton);
    }

    public void showAuthError() {
        navigationUiHelper.checkTextVisible(ErrorMessage.showAuthErrorWithIncorrectData());
    }
}
