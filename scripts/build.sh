#!/usr/bin/env bash

# ============================================================================
# Script: build.sh
# Descripción: Compila el proyecto GUMA
# ============================================================================

set -e

# Cambiar al directorio raíz del proyecto
cd "$(dirname "$0")/.."

echo ""
echo "🔨 Compilando proyecto GUMA..."
echo ""

# Crear directorio de salida si no existe
mkdir -p out

# Classpath con las librerías
CP="lib/*:resources"

# Compilar todos los archivos Java
echo "📦 Compilando archivos Java..."
javac -encoding UTF-8 -source 17 -target 17 -cp "$CP" -d out \
  $(find src -name "*.java")

# Copiar recursos (application.properties)
echo "📋 Copiando archivos de configuración..."
cp -r resources/* out/ 2>/dev/null || true

echo ""
echo "✅ Compilación exitosa"
echo ""
echo "📁 Archivos compilados en: ./out/"
echo "📁 Recursos copiados en: ./out/"
echo ""
