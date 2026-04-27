#!/bin/bash
set -e

echo "Starting Appium..."
appium --log appium.log --log-timestamp > /dev/null 2>&1 &

READY=false

echo "Waiting for Appium..."

for i in $(seq 1 30); do
  if curl -s http://127.0.0.1:4723/status | grep -q '"ready":true'; then
    echo "Appium ready after ${i}s"
    READY=true
    break
  fi

  sleep 1
done

if [ "$READY" = false ]; then
  echo "Appium failed to start"
  exit 1
fi

echo "Running tests..."
./gradlew clean test