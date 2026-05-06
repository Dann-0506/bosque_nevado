#!/usr/bin/env bash
set -euo pipefail

VERSION="${1:?Uso: build-linux.sh <version>}"
APP_NAME="BosqueNevado"
WORK_DIR="target/jpackage-work"
APP_DIR="target/AppDir"
OUTPUT="target/bosque-nevado-${VERSION}.AppImage"

command -v jpackage    >/dev/null || { echo "Error: jpackage no encontrado en PATH (requiere JDK 14+)"; exit 1; }
command -v appimagetool >/dev/null || { echo "Error: appimagetool no encontrado en PATH."; echo "Descárgalo en: https://github.com/AppImage/AppImageKit/releases"; exit 1; }

echo "→ Generando app-image con jpackage..."
rm -rf "${WORK_DIR}"
jpackage \
  --type app-image \
  --input target \
  --main-jar "bosque-nevado-${VERSION}.jar" \
  --name "${APP_NAME}" \
  --app-version "${VERSION}" \
  --dest "${WORK_DIR}"

echo "→ Preparando AppDir..."
rm -rf "${APP_DIR}"
cp -r "${WORK_DIR}/${APP_NAME}" "${APP_DIR}"

cat > "${APP_DIR}/AppRun" << 'APPRUN'
#!/bin/bash
HERE="$(dirname "$(readlink -f "${0}")")"
exec "${HERE}/bin/BosqueNevado" "$@"
APPRUN
chmod +x "${APP_DIR}/AppRun"

cat > "${APP_DIR}/bosquevevado.desktop" << DESKTOP
[Desktop Entry]
Name=Bosque Nevado
Exec=BosqueNevado
Icon=bosquevevado
Type=Application
Categories=Game;
DESKTOP

echo "→ Empaquetando AppImage..."
ARCH=x86_64 appimagetool "${APP_DIR}" "${OUTPUT}"

echo "✓ Listo: ${OUTPUT}"
