@echo off
REM Script para executar o jogo Varguélia

REM Definir caminho do Java do Adobe Animate
set "JAVA_HOME=C:\Program Files\Adobe\Adobe Animate 2024\jre"
set "PATH=%JAVA_HOME%\bin;%PATH%"

REM Definir diretório do projeto
set "GAME_DIR=c:\Users\Jnews\Downloads\Varguelia - Ella ´é Ella\VargueniaGame"

echo.
echo ╔═════════════════════════════════════════════════════════════╗
echo ║           FUNDAÇÃO VARGUËLIA - JOGO TERMINAL 1983          ║
echo ╚═════════════════════════════════════════════════════════════╝
echo.
echo Verificando compilacao...

REM Verificar se out/ existe, senão compilar
if not exist "%GAME_DIR%\out" (
    echo Compilando projeto...
    cd /d "%GAME_DIR%"
    javac -encoding UTF-8 -d out src\*.java src\core\*.java src\game\*.java src\managers\*.java
    if errorlevel 1 (
        echo ERRO: Falha na compilacao
        pause
        exit /b 1
    )
    echo Compilacao concluida!
) else (
    echo Arquivos compilados encontrados.
)

echo.
echo Iniciando jogo...
echo.

REM Navegar para diretório raiz e executar com classpath apontando para out/
cd /d "%GAME_DIR%"
java -cp out VargueniaGame

echo.
echo Jogo encerrado.
pause
