package utils;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class NavigationUiHelper {
    private final GesturesHelper gesturesHelper = new GesturesHelper();
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(4);

    public void clickOnElement(SelenideElement selenideElement) {
        selenideElement.shouldBe(visible, DEFAULT_TIMEOUT);
        selenideElement.click();
    }

    public void setDataInField(String data, SelenideElement selenideElement) {
        selenideElement.shouldBe(visible, DEFAULT_TIMEOUT);
        selenideElement.click();
        selenideElement.setValue(data);
    }

    public void checkTextVisible(String text) {
        $x("//*[contains(@text, \"" + text + "\")]")
                .shouldBe(visible, DEFAULT_TIMEOUT);
    }

    public void checkElementVisible(SelenideElement selenideElement) {
        selenideElement.shouldBe(visible, DEFAULT_TIMEOUT);
    }

    public void clickOnBtnByText(String textInBtn) {
        SelenideElement btn = $x("//*[contains(@text, '" + textInBtn + "')]");
        btn.shouldBe(visible, DEFAULT_TIMEOUT);
        btn.click();
    }

    public void scrollDownUntilElementVisible(SelenideElement element, int maxSwipes) {
        for (int i = 0; i < maxSwipes; i++) {
            if (element.exists() && element.isDisplayed()) return;
            gesturesHelper.swipeUp();
        }
        if (!element.exists() || !element.isDisplayed()) {
            throw new AssertionError("Element not found after " + maxSwipes + " swipes down: " + element);
        }
    }

    public void scrollUpUntilElementVisible(SelenideElement element, int maxSwipes) {
        for (int i = 0; i < maxSwipes; i++) {
            if (element.exists() && element.isDisplayed()) return;
            gesturesHelper.swipeDown();
        }
        if (!element.exists() || !element.isDisplayed()) {
            throw new AssertionError("Element not found after " + maxSwipes + " swipes up: " + element);
        }
    }
}
