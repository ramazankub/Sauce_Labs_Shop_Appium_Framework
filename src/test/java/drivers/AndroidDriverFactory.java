package drivers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.URL;
import java.util.Properties;

public class AndroidDriverFactory {

    public static AndroidDriver createDriver() {
        try {
            Properties properties = new Properties();
            properties.load(
                    AndroidDriverFactory.class
                            .getClassLoader()
                            .getResourceAsStream("capabilities/android.properties")
            );

            String app = properties.getProperty("app", "").trim();

            UiAutomator2Options options = new UiAutomator2Options()
                    .setPlatformName(properties.getProperty("platformName"))
                    .setAutomationName(properties.getProperty("automationName"))
                    .setAutoGrantPermissions(
                            Boolean.parseBoolean(
                                    properties.getProperty("autoGrantPermissions")))
                    .setFullReset(
                            Boolean.parseBoolean(
                                    properties.getProperty("fullReset")));

            // CI mode: APK downloaded by pipeline
            if (!app.isBlank()) {
                options.setApp(app);
            }

            // Handle splash screen -> main activity transition
            String appWaitActivity = properties.getProperty("appWaitActivity", "").trim();
            if (!appWaitActivity.isBlank()) {
                options.setAppWaitActivity(appWaitActivity);
            }

            return new AndroidDriver(
                    new URL(properties.getProperty("appium.url")),
                    options
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to create Android driver", e);
        }
    }
}