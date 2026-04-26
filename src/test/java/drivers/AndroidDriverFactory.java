package drivers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.URL;
import java.time.Duration;
import java.util.Properties;


public class AndroidDriverFactory {
        public static AndroidDriver createDriver() {
            try {
                Properties properties = new Properties();
                properties.load(AndroidDriverFactory.class.getClassLoader().getResourceAsStream("capabilities/android.properties"));


                String app = properties.getProperty("app");

                UiAutomator2Options options = new UiAutomator2Options()
                        .setPlatformName(properties.getProperty("platformName"))
                        .setAutomationName(properties.getProperty("automationName"))
                        .setAutoGrantPermissions(
                                Boolean.parseBoolean(
                                        properties.getProperty("autoGrantPermissions")))
                        .setFullReset(
                                Boolean.parseBoolean(
                                        properties.getProperty("fullReset")));

                if (app != null && !app.isBlank()) {
                    // CI mode: install apk and allow splash/startup activities
                    options.setApp(app);
                    options.setAppActivity("com.swaglabsmobileapp.SplashActivity");
                    options.setAppWaitActivity("com.swaglabsmobileapp.*");
                    options.setAppWaitDuration(Duration.ofSeconds(90));
                } else {
                    options.setAppPackage(properties.getProperty("appPackage"));
                    options.setAppActivity(properties.getProperty("appActivity"));
                }

                return new AndroidDriver(
                        new URL("http://127.0.0.1:4723"),
                        options
                );

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
}
