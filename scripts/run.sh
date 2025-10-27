#!/bin/bash

# Script para ejecutar la aplicación GUMA
# Uso: bash scripts/run.sh

echo "🚀 Iniciando GUMA..."
echo ""

# Verificar que existan los archivos compilados
if [ ! -d "./out" ]; then
    echo "❌ No se encontraron archivos compilados"
    echo "   Ejecuta primero: bash scripts/build.sh"
    exit 1
fi

# Ejecutar la aplicación con el classpath correcto (incluye lib/*)
if [ -z "$1" ]; then
    # Si no se especifica clase, usar LoginDialog por defecto
    java -cp "./out:./lib/*" com.guma.frontend.ui.LoginDialog
else
    # Si se especifica una clase, usarla
    java -cp "./out:./lib/*" "$1"
fi

echo ""
echo "👋 GUMA finalizado"
