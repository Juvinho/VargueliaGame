╔═════════════════════════════════════════════════════════════╗
║           FUNDAÇÃO VARGUËLIA - JOGO EM JAVA 1983            ║
║                    Game Development Setup                    ║
╚═════════════════════════════════════════════════════════════╝

=== ESTRUTURA DO PROJETO ===

VargueniaGame/
├── src/
│   ├── VargueniaGame.java          (Main - entrada do jogo)
│   ├── core/                        (Estruturas base)
│   │   ├── Player.java              (Enum: NIUWE, ELLA)
│   │   ├── GameState.java           (Estado global + passwords)
│   │   ├── Scene.java               (Cena do jogo)
│   │   └── SceneOption.java         (Opções de escolha)
│   ├── game/                        (Lógica principal)
│   │   ├── GameWindow.java          (Interface Swing)
│   │   └── GameEngine.java          (Motor de jogo)
│   ├── managers/                    (Gerenciadores)
│   │   └── SoundManager.java        (Sistema de áudio)
│   └── scenes/                      (Definições de cenas - futuro)
└── resources/
    └── sounds/                      (Arquivos .wav)

=== COMPILAÇÃO ===

O projeto já foi compilado! Os arquivos .class estão em /out

Se precisar recompilar manualmente (Windows PowerShell):

$env:Path = "C:\Program Files\Adobe\Adobe Animate 2024\jre\bin;" + $env:Path
cd "c:\Users\Jnews\Downloads\Varguelia - Ella ´é Ella\VargueniaGame"
javac -encoding UTF-8 -d out src/*.java src/core/*.java src/game/*.java src/managers/*.java

=== EXECUÇÃO ===

Opção 1 - Script BAT (Windows CMD):
  Abra run.bat (duplo clique)

Opção 2 - Script PowerShell:
  PowerShell -ExecutionPolicy Bypass -File run.ps1

Opção 3 - Linha de comando:
  $env:Path = "C:\Program Files\Adobe\Adobe Animate 2024\jre\bin;" + $env:Path
  cd "c:\Users\Jnews\Downloads\Varguelia - Ella ´é Ella\VargueniaGame\out"
  java VargueniaGame

=== RECURSOS DE SOM (Futuro) ===

Os seguintes arquivos .wav devem estar em resources/sounds/:
- boot.wav: Som de inicialização do terminal
- key.wav: Som de tecla pressionada
- glitch.wav: Som de distorção/erro

Por enquanto, esses sons são opcionais.

=== CONTROLES ===

- ENTER / SPACE: Avançar texto quando não há opções
- 1, 2, 3, etc: Selecionar opção de escolha

=== PRÓXIMAS ETAPAS ===

1. ✓ Estrutura base implementada
2. ✓ Primeiras cenas do Ato 1 (Niuwë)
3. ✓ Sistema de opções funcional
4. ○ Expandir cenas do Ato 1 até conclusão
5. ○ Implementar Ato 2 (Ella) completo
6. ○ Adicionar efeitos de glitch visual
7. ○ Integrar sons (SoundManager)
8. ○ Sistema de password refinado
9. ○ Menu inicial com opções de NOVO JOGO / CONTINUAR
10. ○ Balanceamento de narrativa entre atos

=== NOTAS TÉCNICAS ===

- Código compatível com Java 8+
- Sem dependências externas (apenas JDK)
- Interface Swing com tema "terminal retro"
- Cores: Preto + Verde fosforescente
- Fonte: Monospaced, 14pt

=== AUTOR ===

Desenvolvimento de jogo narrativo baseado em
"Fundação Varguélia - Ella é demais"

Data de início: Fevereiro 2026
