package utils;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;

public class GesturesHelper {
    public void swipeUp() {
        AppiumDriver driver;

        try {
            driver = (AppiumDriver) WebDriverRunner.getWebDriver();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Dimension size = driver.manage().window().getSize();
        int x = size.width;
        int y = size.height;

        int startX = (x / 2);
        int startY = (int) (y * 0.8);
        int endY = (int) (y * 0.2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(
                Duration.ZERO,
                PointerInput.Origin.viewport(),
                startX, startY
        ));

        swipe.addAction(finger.createPointerDown(
                PointerInput.MouseButton.LEFT.asArg()
        ));

        swipe.addAction(finger.createPointerMove(
                Duration.ofMillis(400),
                PointerInput.Origin.viewport(),
                startX, endY
        ));

        swipe.addAction(finger.createPointerUp(
                PointerInput.MouseButton.LEFT.asArg()
        ));

        driver.perform(List.of(swipe));
    }

    public void swipeDown() {
        AppiumDriver driver;

        try {
            driver = (AppiumDriver) WebDriverRunner.getWebDriver();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Dimension size = driver.manage().window().getSize();
        int x = size.getWidth();
        int y = size.getHeight();

        int startX = x / 2;
        int startY = (int) (y * 0.2);
        int endY = (int) (y * 0.8);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(
                finger.createPointerMove(
                        Duration.ZERO,
                        PointerInput.Origin.viewport(),
                        startX, startY
                )
        );

        swipe.addAction((
                finger.createPointerDown(
                        PointerInput.MouseButton.LEFT.asArg()
                )
        ));

        swipe.addAction((
                finger.createPointerMove(
                        Duration.ofMillis(400),
                        PointerInput.Origin.viewport(),
                        startX, endY
                )
        ));

        swipe.addAction((
                finger.createPointerUp(
                        PointerInput.MouseButton.LEFT.asArg()
                )
        ));

        driver.perform(List.of(swipe));
    }
}
