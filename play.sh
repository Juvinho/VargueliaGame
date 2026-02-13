#!/bin/bash

# ===== VARGUELIA - Ella é Demais =====
# Script para iniciar o jogo facilmente

clear
echo ""
echo "========================================"
echo "   VARGUELIA - Ella é Demais"
echo "========================================"
echo ""
echo "Iniciando jogo..."
echo ""

# Detectar o caminho Java
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR/src"

# Tentar usar java do PATH
if command -v java &> /dev/null; then
    java VargueliaGameExpanded
else
    echo "[ERRO] Java não encontrado no PATH!"
    echo ""
    echo "Instale Java 17+ e tente novamente."
    exit 1
fi

# Se chegou aqui, o jogo foi fechado
echo ""
echo "Jogo encerrado."
