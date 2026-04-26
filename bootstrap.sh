#!/usr/bin/env bash

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
# Install Node if missing
# -------------------------

echo "Starting mobile test environment bootstrap..."

echo "Checking Node.js..."

if command -v node >/dev/null
then
 echo "Node.js already installed"

else
 echo "Node.js not found. Installing..."

 if [ "$PACKAGE_MANAGER" = "brew" ]
 then
   brew install node || {
      echo "ERROR: Node installation failed"
      exit 1
   }

 elif [ "$PACKAGE_MANAGER" = "apt" ]
 then
   curl -fsSL https://deb.nodesource.com/setup_lts.x | sudo -E bash -

   sudo apt-get install -y nodejs || {
      echo "ERROR: Node installation failed"
      exit 1
   }
 fi
fi

hash -r

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

 npm install -g appium || {
    echo "ERROR: Appium installation failed"
    exit 1
 }
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
# XCUITest
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