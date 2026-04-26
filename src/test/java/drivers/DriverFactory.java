package drivers;

import io.appium.java_client.AppiumDriver;

public class DriverFactory {

    private DriverFactory() {
    }

    public static AppiumDriver createDriver() {

        String platform = System.getProperty("platform", "ios").toLowerCase();

        return switch (platform) {
            case "android" -> AndroidDriverFactory.createDriver();
            case "ios" -> IOSDriverFactory.createDriver();
            default -> throw new IllegalArgumentException("Unknown platform: " + platform);
        };
    }
}
