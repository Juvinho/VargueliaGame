@echo off
setlocal enabledelayedexpansion

REM ===== VARGUELIA - Ella é Demais =====
REM Script para iniciar o jogo facilmente

cls
echo.
echo ========================================
echo   VARGUELIA - Ella é Demais
echo ========================================
echo.
echo Iniciando jogo...
echo.

REM Detectar o caminho do Java
set "JAVA_PATH=C:\Program Files\Adobe\Adobe Animate 2024\jre\bin\java.exe"

if not exist "%JAVA_PATH%" (
    echo [ERRO] Java não encontrado em:
    echo %JAVA_PATH%
    echo.
    echo Tente instalar Adobe Animate 2024 ou ajuste o caminho no arquivo.
    pause
    exit /b 1
)

REM Mudar para a pasta src
cd /d "%~dp0src"

REM Executar o jogo
"%JAVA_PATH%" VargueliaGameExpanded

REM Se chegou aqui, o jogo foi fechado
echo.
echo Jogo encerrado.
pause
