package drivers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.URL;
import java.util.Properties;


public class AndroidDriverFactory {
        public static AndroidDriver createDriver() {
            try {
                Properties properties = new Properties();
                properties.load(AndroidDriverFactory.class.getClassLoader().getResourceAsStream("capabilities/android.properties"));


                UiAutomator2Options uiAutomator2Options = new UiAutomator2Options()
                        .setPlatformName(properties.getProperty("platformName"))
                        .setAutomationName(properties.getProperty("automationName"))
                        .setAppPackage(properties.getProperty("appPackage"))
                        .setAppActivity(properties.getProperty("appActivity"))
                        .setAutoGrantPermissions(Boolean.parseBoolean(properties.getProperty("autoGrantPermissions")))
                        .setPlatformVersion(properties.getProperty("platformVersion"))
                        .setFullReset(Boolean.parseBoolean(properties.getProperty("fullReset")));

                return new AndroidDriver(
                        new URL("http://127.0.0.1:4723"),
                        uiAutomator2Options
                );

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
}
