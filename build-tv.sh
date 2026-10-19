#!/bin/bash
# Build script for Kotatsu TV
set -euo pipefail

echo "[BUILD] Building Kotatsu TV..."
cd source
./gradlew :app:assembleRelease 2>&1 | tail -30
echo "[BUILD] APK should be at source/app/build/outputs/apk/release/"
