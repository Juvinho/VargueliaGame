# Script para executar o jogo Varguélia em PowerShell

$JavaPath = "C:\Program Files\Adobe\Adobe Animate 2024\jre\bin"
$env:Path = $JavaPath + ";" + $env:Path

$ProjectPath = "c:\Users\Jnews\Downloads\Varguelia - Ella ´é Ella\VargueniaGame\out"
Set-Location $ProjectPath

Write-Host "╔═════════════════════════════════════════════════════════════╗"
Write-Host "║           FUNDAÇÃO VARGUËLIA - JOGO TERMINAL 1983          ║"
Write-Host "╚═════════════════════════════════════════════════════════════╝"
Write-Host ""
Write-Host "Iniciando jogo..."
Write-Host ""

java VargueniaGame

Write-Host ""
Write-Host "Jogo encerrado."
