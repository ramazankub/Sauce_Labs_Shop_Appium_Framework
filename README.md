# Mobile Automation Tests for "Sauce Labs" Demo App (Appium)

This project contains automated UI tests for native mobile application using **Appium**, **Java 21**, **JUnit 5**, and **Selenide Appium**.

The project demonstrates a clean and scalable mobile test automation framework with proper separation of concerns and the Page Object pattern.

## Quick Start
- Clone this project
```bash
git clone https://github.com/ramazankub/Sauce_Labs_Shop_Appium_Framework.git
cd Sauce_Labs_Shop_Appium_Framework
```

- Ensure Java 21 is installed

Android:
- Start an Android emulator or connect a real device

```bash
./bootstrap.sh
appium
./gradlew test
```

iOS:
- Start an iOS simulator or connect a real device

```bash
./bootstrap.sh
appium
./gradlew test -Dplatform=ios
```
---

## Tech Stack

- Java **21**
- Gradle
- Appium **3.3.1**
- UIAutomator2
- XCUITest
- Android SDK
- JUnit 5
- Selenide Appium
- Allure Reports

---

## Project Structure

```
src/test/java
├── drivers/
│   ├── DriverFactory.java          # Entry point: selects platform driver (android / ios)
│   ├── AndroidDriverFactory.java   # Creates AndroidDriver with UiAutomator2Options
│   └── IOSDriverFactory.java       # Creates IOSDriver with XCUITestOptions
│
├── pages/
│   ├── LoginPage.java              # Login screen: input fields, login button, auth error
│   └── MainPage.java               # Main screen: verifies successful login
│
├── tests/
│   ├── BaseTest.java               # Driver lifecycle management (@BeforeEach / @AfterEach)
│   └── LoginTest.java              # Test cases: positive login, negative login, parameterized
│
├── utils/
│   ├── GesturesHelper.java         # Low-level gestures via W3C Actions API (swipeUp / swipeDown)
│   └── NavigationUiHelper.java     # High-level UI actions: clicks, input, scroll to element, assertions
│
└── testData/
    ├── authData/
    │   └── Credentials.java        # Login credentials (with environment variable support)
    └── ErrorMessages/
        └── ErrorMessage.java       # Error message text constants

src/test/resources
└── capabilities/
    ├── android.properties          # Appium capabilities for Android (UIAutomator2)
    └── ios.properties              # Appium capabilities for iOS (XCUITest)
```

---

## Application Under Test

Tests are executed against Sauce Labs demo mobile applications (Android and iOS).

---

## Prerequisites

Before running the tests, make sure the following tools are installed.

### 1. Install Java 21

Download and install **Java 21 (LTS)** from the official site:
https://adoptium.net/

This project requires **Java 21**.  
If another Java version is used, Gradle Toolchain auto-provisioning can automatically download the required JDK.

To verify which Java version the project uses, run:
```bash
./gradlew printToolchain
```
Check your system Java:

```bash
java -version
```

---

### 2. Install Dependencies

You can bootstrap the full mobile test environment automatically:

```bash
chmod +x bootstrap.sh
./bootstrap.sh
```

The bootstrap script installs and validates:

- Node.js / npm
- Appium
- UIAutomator2 driver
- XCUITest driver (macOS only)
- Android SDK environment variables for the current shell session

Or install dependencies manually step by step.

#### Install Node.js (18+)

Download and install **Node.js LTS**:

https://nodejs.org/

Verify installation:

```bash
node -v
npm -v
```

---

### 3. Install Appium 3.x

Install Appium globally:

```bash
npm install -g appium
```

Verify installation:

```bash
appium -v
```

Install required Appium drivers:

```bash
appium driver install uiautomator2
```

For iOS (macOS only):

```bash
appium driver install xcuitest
```

---

### 4. Install Android SDK

Install **Android Studio** (includes Android SDK):

https://developer.android.com/studio

After installation:

- Install Android SDK and Platform Tools
- Create and start an Android Emulator

or connect a real Android device with USB debugging enabled.

Verify ADB connection:

```bash
adb devices
```

---

## How to Run Tests

### 1. Start a device

Use one of:

- Android emulator
- Real Android device
- iOS simulator
- Real iOS device

---

### 2. Start Appium server

```bash
appium
```

---

### 3. Run tests

Android (default):

```bash
./gradlew test
```

iOS:

```bash
./gradlew test -Dplatform=ios
```

---

### 4. Generate Allure report (optional)

Generate report:

```bash
./gradlew allureReport
```

Open report locally:

```bash
./gradlew allureServe
```


---
## Test Coverage

- Login (positive and negative scenarios)
- Validation error messages
- Parameterized tests for invalid credentials
- Basic UI navigation checks
- Gesture-based interactions (scroll / swipe)

---

## Key Design Decisions

- Page Object pattern is used to separate test logic from UI implementation
- Test data is centralized and separated from test logic
- No shared mutable state is used
- Tests are isolated and independent
- Parameterized tests are applied for negative scenarios
- Capabilities are configured via properties files
- No hardcoded waits (`Thread.sleep`) are used
- Explicit waits are handled via Selenide conditions
- Modern Appium W3C Actions API is used for gestures

---

## Test Execution Flow

1. Driver is created via `DriverFactory`
2. Capabilities are loaded from `src/test/resources/capabilities`
3. Tests are executed using JUnit 5
4. Driver is terminated after each test
5. Allure results are generated after execution

---

## Environment

- Java 21
- Appium 3.x
- UIAutomator2
- XCUITest
- Android Emulator or real Android device
- iOS Simulator or real iOS device
- Gradle build system

---
## CI Pipeline

The project includes a GitHub Actions pipeline triggered on:

- Push to `main`
- Manual trigger (`workflow_dispatch`)

The pipeline consists of a single job — **Android UI Tests**:
- KVM hardware acceleration setup for the emulator
- Node.js, Appium 3.x and UIAutomator2 driver installation
- Sauce Labs APK download from GitHub Releases
- Appium server startup in background
- Android Emulator startup (Pixel 5, API 31) via `android-emulator-runner`
- Full test suite execution (`./gradlew test`)
- Upload of test result artifacts (Allure results, reports, Appium log)
- Telegram notification with run status, branch and link

Workflow definition:

```text
.github/workflows/sauceLabs-ui-tests.yml
```

> **Note on iOS:**  
> iOS simulator CI was intentionally skipped. Running XCUITest requires a `macos-latest` runner which is 10× more expensive than `ubuntu-latest` and consumes GitHub Actions minutes much faster. iOS support is fully implemented in the framework and works locally on macOS — see [How to Run Tests](#how-to-run-tests).

---
## Troubleshooting

### Appium server is not running

Start Appium manually:

```bash
appium
```

---

### Android device or emulator is not detected

Check connected devices:

```bash
adb devices
```

Make sure emulator is started or USB debugging is enabled.

---

### Required Appium driver is missing

Install UIAutomator2:

```bash
appium driver install uiautomator2
```

Install XCUITest (macOS):

```bash
appium driver install xcuitest
```

---

### Android SDK is not found

Run bootstrap again:

```bash
./bootstrap.sh
```

Or verify:

```bash
adb version
```

---

### Wrong Java version is used

Check Gradle toolchain:

```bash
./gradlew printToolchain
```

Check system Java:

```bash
java -version
```

---

### Tests fail because platform was not specified

Android runs by default:

```bash
./gradlew test
```

Run iOS explicitly:

```bash
./gradlew test -Dplatform=ios
```

---
### iOS simulator not found

Check available simulators:

```bash
xcrun simctl list devices
```
