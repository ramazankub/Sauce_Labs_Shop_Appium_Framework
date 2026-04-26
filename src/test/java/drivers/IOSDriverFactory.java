package drivers;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.net.URL;
import java.util.Properties;


public class IOSDriverFactory {
    public static IOSDriver createDriver() {
        try {
            Properties properties = new Properties();
            properties.load(IOSDriverFactory.class.getClassLoader().getResourceAsStream("capabilities/ios.properties"));


            XCUITestOptions xcuiTestOptions = new XCUITestOptions()
                    .setPlatformName(properties.getProperty("platformName"))
                    .setAutomationName(properties.getProperty("automationName"))
                    .setDeviceName(properties.getProperty("deviceName"))
                    .setPlatformVersion(properties.getProperty("platformVersion"))
                    .setBundleId(properties.getProperty("bundleId"));

            return new IOSDriver(
                    new URL(properties.getProperty("appium.url")),xcuiTestOptions

            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
