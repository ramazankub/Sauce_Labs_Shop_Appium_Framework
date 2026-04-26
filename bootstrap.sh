#!/usr/bin/env bash

set -e

echo "Starting mobile test environment bootstrap..."


# -------------------------
# Detect Package Manager
# -------------------------

if command -v brew >/dev/null
then
 PACKAGE_MANAGER="brew"

elif command -v apt-get >/dev/null
then
 PACKAGE_MANAGER="apt"

else
 echo "ERROR: Supported package manager not found."
 exit 1
fi


# -------------------------
# Check Android SDK
# -------------------------

echo "Checking Android SDK..."

if [[ "$OSTYPE" == "darwin"* ]]
then
  SDK_PATH="$HOME/Library/Android/sdk"

elif [ "$PACKAGE_MANAGER" = "apt" ]
then
  SDK_PATH="$HOME/Android/Sdk"

else
  echo "WARNING: Unsupported OS for Android SDK detection"
  SDK_PATH=""
fi


if [ -n "$SDK_PATH" ] && [ -d "$SDK_PATH" ]
then
  echo "Android SDK found: $SDK_PATH"

  export ANDROID_HOME="$SDK_PATH"
  export ANDROID_SDK_ROOT="$SDK_PATH"
  export PATH="$PATH:$SDK_PATH/platform-tools:$SDK_PATH/emulator"

  if command -v adb >/dev/null
  then
     echo "adb available"
  else
     echo "WARNING: adb not found, Android tests may not run"
  fi

  echo "ANDROID_HOME configured for current session"

  echo "Tip: add these variables to your shell profile for persistence:"
  echo "export ANDROID_HOME=$SDK_PATH"
  echo 'export ANDROID_SDK_ROOT=$ANDROID_HOME'
  echo 'export PATH=$PATH:$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator'

else
  echo "WARNING: Android SDK not found. Skipping Android setup."
fi

# -------------------------
# Install Node if missing
# -------------------------

echo "Checking Node.js..."

if command -v node >/dev/null
then
 echo "Node.js already installed"

else
 echo "Node.js not found. Installing..."

 if [ "$PACKAGE_MANAGER" = "brew" ]
 then
   brew install node

 elif [ "$PACKAGE_MANAGER" = "apt" ]
 then
   curl -fsSL https://deb.nodesource.com/setup_lts.x | sudo -E bash -
   sudo apt-get install -y nodejs
 fi
fi

hash -r


# -------------------------
# Verify Node version
# -------------------------

NODE_VERSION=$(node -v | cut -d'v' -f2 | cut -d'.' -f1)

if [ "$NODE_VERSION" -lt 18 ]
then
 echo "ERROR: Node.js 18+ required. Current: $NODE_VERSION"
 exit 1
fi

echo "Node version OK: $(node -v)"


# -------------------------
# Verify npm
# -------------------------

echo "Checking npm..."

if command -v npm >/dev/null
then
 echo "npm installed"
else
 echo "ERROR: npm missing"
 exit 1
fi


# -------------------------
# Install Appium
# -------------------------

echo "Checking Appium..."

if command -v appium >/dev/null
then
 echo "Appium already installed"

else
 echo "Installing Appium..."
 npm install -g appium
fi

hash -r

echo "Using Appium version:"
appium -v


# -------------------------
# UIAutomator2
# -------------------------

echo "Checking UIAutomator2..."

OUTPUT=$(appium driver install uiautomator2 2>&1)

if echo "$OUTPUT" | grep -q "already installed"
then
 echo "UIAutomator2 already installed"

elif echo "$OUTPUT" | grep -q "successfully installed"
then
 echo "UIAutomator2 installed"

else
 echo "$OUTPUT"
 exit 1
fi


# -------------------------
# XCUITest (macOS only)
# -------------------------

if [[ "$OSTYPE" == "darwin"* ]]
then

 echo "Checking XCUITest..."

 OUTPUT=$(appium driver install xcuitest 2>&1)
 STATUS=$?

 if echo "$OUTPUT" | grep -q "already installed"
 then
   echo "XCUITest already installed"

 elif [ $STATUS -eq 0 ]
 then
   echo "XCUITest installed"

 else
   echo "$OUTPUT"
   echo "ERROR: XCUITest installation failed"
   exit 1
 fi

fi


echo "Bootstrap completed successfully."