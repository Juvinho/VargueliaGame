@echo off
REM Script para executar o jogo Varguélia

REM Definir caminho do Java do Adobe Animate
set "JAVA_HOME=C:\Program Files\Adobe\Adobe Animate 2024\jre"
set "PATH=%JAVA_HOME%\bin;%PATH%"

REM Navegar para diretório do projeto compilado
cd /d "c:\Users\Jnews\Downloads\Varguelia - Ella ´é Ella\VargueniaGame\out"

REM Executar
java VargueniaGame

pause
