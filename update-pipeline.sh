#!/bin/bash
# Auto-update pipeline for Kotatsu TV
# Pulls upstream Kotatsu-Redo, applies TV patches, builds

set -euo pipefail

REPO_URL="https://github.com/Kotatsu-Redo/Kotatsu-Redo.git"
SOURCE_DIR="source"
PATCH_DIR="tv-patches"

# Pull latest upstream
if [ -d "$SOURCE_DIR/.git" ]; then
    echo "[UPDATE] Pulling latest upstream changes..."
    cd "$SOURCE_DIR"
    git fetch origin main || git fetch origin master
    git reset --hard origin/main || git reset --hard origin/master
    cd ..
else
    echo "[UPDATE] Cloning upstream..."
    rm -rf "$SOURCE_DIR"
    git clone "$REPO_URL" "$SOURCE_DIR"
fi

# Apply TV patches if any exist
echo "[UPDATE] Applying TV patches..."
if [ -d "$PATCH_DIR" ]; then
    for patch in "$PATCH_DIR"/*.patch; do
        [ -e "$patch" ] || continue
        echo "  Applying $(basename $patch)"
        git -C "$SOURCE_DIR" apply "$patch" || echo "  (patch may already be applied or not needed)"
    done
fi

echo "[UPDATE] Build ready at $SOURCE_DIR"
echo "Run: ./gradlew :app:assembleRelease inside $SOURCE_DIR"
