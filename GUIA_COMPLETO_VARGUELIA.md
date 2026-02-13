# GUIA COMPLETO - VARGUELIA: ELLA É DEMAIS (Jogo Java 1983)

## Visão Geral

**Varguelia: Ella é Demais** é um jogo narrativo baseado em texto, em Java/Swing, inspirado no universo do livro homônimo. O jogo simula ser um programa de MS-DOS de 1983, mas com funcionalidades narrativas absurdamente avançadas para a época.

O jogo está dividido em **dois atos**:
- **Ato 1**: Controlar Niuwë em suas últimas horas na Varguën, culminando em morte inevitável (mas com escolhas que mudam o legado).
- **Ato 2**: Controlar Ella anos depois, investigando a verdade sobre Niuwë e enfrentando a Ponte dos Eventos.

---

## 1. ARQUITETURA - ESTRUTURA DE PACOTES E CLASSES

### 1.1. Pacotes

```
src/
├── VargueniaGame.java           # Main e orquestrador principal
├── core/
│   ├── GameState.java           # Estado global do jogo
│   └── Scene.java               # Estrutura de uma cena
├── game/
│   ├── GameWindow.java          # JFrame + JTextPane (UI principal)
│   ├── GameEngine.java          # Motor de narrativa
│   ├── MenuScreen.java          # Tela do menu principal
│   ├── PasswordScreen.java      # Tela de entrada de senha
│   ├── LogsScreen.java          # Tela de arquivos DOS
│   ├── OptionsScreen.java       # Tela de configurações
│   ├── CreditsScreen.java       # Tela de créditos
│   └── UiState.java             # Enum de estados da UI
└── managers/
    ├── MusicManager.java        # Gerenciador de trilha sonora
    ├── SanitySystem.java        # Sistema de sanidade
    ├── TerminalSystem.java      # Terminal "vivo" e reações
    ├── MemorySystem.java        # Replay de memórias
    ├── VarguenFileSystem.java   # Simulação de sistema de arquivos
    ├── InactivityMonitor.java   # Monitor de inatividade
    ├── RetroSoundGenerator.java # Síntese de áudio (PCM)
    └── RetroMusicGenerator.java # Composição de temas musicais
```

---

## 2. ESTÉTICA VISUAL (MS-DOS BRANCA - 1986)

### 2.1. Configuração de Janela (`GameWindow.java`)

```java
// Exemplo de setup
JFrame frame = new JFrame("Varguelia - Ella é Demais");
frame.setSize(800, 600);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setResizable(false);

JTextPane textPane = new JTextPane();
textPane.setBackground(Color.BLACK);
textPane.setForeground(new Color(220, 220, 220)); // Branco/cinza
textPane.setFont(new Font("Monospaced", Font.PLAIN, 18));
textPane.setEditable(false);

JScrollPane scrollPane = new JScrollPane(textPane);
frame.add(scrollPane);
```

### 2.2. Sistema de Cores

Use `StyledDocument` com múltiplos `Style` objects:

| Estilo | Cor | Uso |
|--------|-----|-----|
| `default` | #DCDCDC (branco/cinza) | Texto padrão |
| `error` | #FF4040 (vermelho) | "Varguelia", erros, tensão |
| `cyan` | #00FFFF | Destaques, opções selecionadas |
| `yellow` | #FFFF00 | Títulos, instruções importantes |
| `cursor` | #DCDCDC piscante | Cursor DOS simulado |

### 2.3. Método de Append Colorido

```java
public void appendText(String text, String styleName) {
    StyledDocument doc = textPane.getStyledDocument();
    doc.insertString(doc.getLength(), text, getStyle(styleName));
}

// Uso:
window.appendText("Varguelia", "error");           // Vermelho
window.appendText(" - Ella é Demais", "default");  // Branco
```

---

## 3. FLUXO DE INICIALIZAÇÃO

### 3.1. Sequência Completa

1. **Boot Screen** (2-3 segundos)
   - ASCII art com logo VARGUEN.OS
   - Mensagens de inicialização (Memory check, Bus inicializado, etc.)
   - Efeito de carregamento com `[OK]` em cyan
   - Som de boot synth

2. **Tela de Título** (Estática até ENTER)
   ```
   ════════════════════════════════════════════════════════════════
   ║                                                              ║
   ║  Varguelia - Ella é Demais                                  ║
   ║                                                              ║
   ════════════════════════════════════════════════════════════════

               Pressione ENTER para continuar
   ```
   - "Varguelia" em **vermelho**
   - Resto em branco
   - Centralizado na tela

3. **Menu Principal** (Aguarda escolha 1-6)
   ```
   ════════════════════════════════════════════════════════════════
   ║  Varguelia - Ella é Demais                                  ║
   ════════════════════════════════════════════════════════════════

           MENU PRINCIPAL

      1. NOVO JOGO
      2. CONTINUAR (PASSWORD)
      3. ARQUIVOS DA VARGUËN
      4. OPÇÕES
      5. CRÉDITOS
      6. SAIR
   ```

---

## 4. SISTEMA DE ESTADOS (UiState)

```java
public enum UiState {
    TITLE_SCREEN,      // Tela de título
    MAIN_MENU,         // Menu principal
    MENU_PASSWORD,     // Entrada de password
    MENU_LOGS,         // Tela de arquivos DOS
    MENU_OPTIONS,      // Configurações
    MENU_CREDITS,      // Créditos
    GAME               // Gameplay
}
```

Cada estado tem um `InputHandler` específico que processa teclado diferentes maneiras.

---

## 5. MENU PRINCIPAL - 6 OPÇÕES

### 5.1. Opção 1: NOVO JOGO

```
Resultado:
- Reseta GameState completamente
- Define currentPlayer = NIUWE
- Começa na primeira cena do Ato 1
- Toca som de confirmação
- Inicia tema de exploração (música)
```

### 5.2. Opção 2: CONTINUAR (PASSWORD)

Abre `PasswordScreen`:
```
C:\VARGUEN\LOGS

Para continuar sua jornada, digite seu código de salva jogo:

Código salvo: [exemplo: VARG-1A2B3C]

Digite o código: _
```

- Input aceita alfanuméricos (A-Z, 0-9)
- Formata automaticamente: `XXXX-XXXXX`
- Ao pressionar ENTER: decodifica password → restaura `GameState` → entra no Ato 2

### 5.3. Opção 3: ARQUIVOS DA VARGUËN

Abre `LogsScreen` com simulação DOS:

```
C:\VARGUEN\LOGS> DIR

1. LOG_NIUWE.TXT        [Arquivo de eventos de Niuwë]
2. BRIDGE_EVENTS.DAT    [Anomalias temporais detectadas]
3. ELLA_DATA.BIN        [Arquivo binário - corrompido]
4. SYSTEM.LOG           [Log do sistema VARGUEN-OS]

Selecione um arquivo:
```

Ao pressionar ENTER em um arquivo: exibe conteúdo com `TYPE`

```
C:\VARGUEN\LOGS> TYPE LOG_NIUWE.TXT

[16/11/2247 04:32] Acordei em uma cápsula. Não é a estação.
[16/11/2247 04:35] Dasko estava aqui. Ele desapareceu.
[16/11/2247 05:12] A Ponte... algo está acontecendo lá.
...
```

**Nota**: Alguns arquivos só aparecem se certas flags forem verdadeiras.

### 5.4. Opção 4: OPÇÕES

Tela de configurações com ↑/↓ para navegar e ←/→ para ajustar:

```
════════════════════════════════════════════════════════════════
║ CONFIGURAÇÕES                                                 ║
════════════════════════════════════════════════════════════════

   ► VOLUME DA MÚSICA         [MÉDIO]  ◄
     VOLUME DE EFEITOS       [MÉDIO]
     VELOCIDADE DO TEXTO     [RÁPIDO]
     GLITCHES VISUAIS        [LIGADO]
     BRILHO DO TERMINAL      [NORMAL]
```

Valores ajustáveis:
- **Volume**: MUDO, BAIXO, MÉDIO, ALTO
- **Velocidade**: INSTANTÂNEO, RÁPIDO, LENTO
- **Glitches**: LIGADO / DESLIGADO
- **Brilho**: ESCURO, NORMAL, BRILHO MÁX

### 5.5. Opção 5: CRÉDITOS

Mostra texto rolando (20 linhas por vez, navegável com ↑/↓):

```
════════════════════════════════════════════════════════════════

         A FUNDAÇÃO VARGUÉLIA
         A Lenda de 1983

════════════════════════════════════════════════════════════════

Desenvolvido por:
JN GAMES STUDIO

Inspirado em:
'Fundação Varguélia - Ella é Demais'

[... mais créditos ...]

VARGUEN-OS (c) 1983
Todos os direitos reservados.

Obrigado por jogar!

Pressione qualquer tecla para voltar...
```

### 5.6. Opção 6: SAIR

```
C:\VARGUEN> SHUTDOWN /Y

Sistema desligando...

[Som de desligar]
[Janela fecha]
```

---

## 6. ESTRUTURA DE DADOS DO JOGO

### 6.1. GameState.java

```java
public class GameState {
    // Personagem atual
    public Player currentPlayer;  // NIUWE ou ELLA
    public String currentSceneId; // ID da cena atual
    
    // Sanidade da Ella (0-100)
    public int ellaSanity = 100;
    
    // Flags do Ato 1 (Niuwë) que afetam Ato 2
    public boolean niuweFoiImpulsivo = false;
    public boolean niuweSalvouMaisAlunos = false;
    public boolean niuweExplorouEstacao4 = false;
    public boolean niuweDialogouComDasko = false;
    public boolean niuweLutouDiretoComSepharoth = false;
    public boolean niuweAjudouJorgenssenRoger = false;
    
    // Flags gerais
    public boolean ponteAtiva = false;
    public boolean sombraEncontrada = false;
    
    // Meta-tracking
    public int totalPlaythroughs = 0;
    public long totalGameplayTime = 0;
    public int choicesMade = 0;
    public boolean ellaViewedNiuweMemories = false;
    public boolean ellaAccessedFileSystem = false;
    
    // Métodos de codificação/decodificação de password
    public String encodeToPassword() { /* ... */ }
    public void decodeFromPassword(String password) { /* ... */ }
}
```

### 6.2. Scene.java

```java
public class Scene {
    public String id;              // Identificador único (ex: "ato1_inicio")
    public Player player;          // NIUWE ou ELLA
    public String text;            // Descrição da cena (narrativa)
    public List<SceneOption> options; // Opções disponíveis
    
    // Métodos
    public void display(GameWindow window) { /* Escreve a cena na tela */ }
}

public class SceneOption {
    public String key;      // "1", "2", "A", etc.
    public String text;     // Texto exibido da opção
    public String nextId;   // ID da próxima cena
}
```

---

## 7. NARRATIVA - ATOS E CENAS

### 7.1. Ato 1 - Niuwë (Inevitável morte, escolhas que mudam legado)

#### Cenas principais:

1. **ato1_inicio** (Cena inicial)
   - Niuwë acorda após toque de recolher
   - Aviso: "Todos ao hall central imediatamente"
   - Caos, alunos em pânico
   - Opções:
     - 1: "Correr direto pro elevador de segurança"
     - 2: "Ajudar alunos no dormitório antes"

2. **ato1_conversa_jorgenssen**
   - Telefone toca no quarto
   - Amigo Jorgenssen avisa sobre estação 4
   - Opções:
     - 1: "Agradecer e desligar (ação rápida)"
     - 2: "Insistir em detalhes da situação"

3. **ato1_corredor_alunos**
   - Niuwë corre pelos corredores
   - Encontra grupo de alunos assustados
   - Opções:
     - 1: "Levar todos para zona de segurança"
     - 2: "Deixar com segurança e ir descobrir o que acontece"

4. **ato1_encontro_dasko**
   - Niuwë encontra Dasko em área administrativa
   - Dasko sabe algo sobre estação 4
   - Opções:
     - 1: "Escutar Dasko (racional)"
     - 2: "Insistir que saia agora (impulsivo)"

5. **ato1_helicoptero**
   - Helicóptero com Taila, Selenna, Dasko
   - Veem coisa colossal (Sepharoth)
   - Descrição: silhueta impossível, desproporcional
   - Opções:
     - 1: "Tentar se comunicar com piloto"
     - 2: "Preparar-se para o impacto"

6. **ato1_floresta**
   - Queda na floresta
   - Taila sacrifica-se para salvar Niuwë
   - Niuwë corre para o interior
   - Sem opção (linear, transitório)

7. **ato1_ponte_eventos** (Climax)
   - Niuwë encontra a Ponte dos Eventos
   - Encontra Melaninne/Häita
   - Luta sobrenatural
   - Algo invisível o estrangula
   - Opções:
     - 1: "Resistir (aumenta sanidade da Ella depois)"
     - 2: "Render-se (diminui sanidade da Ella depois)"

8. **ato1_morte** (Cena final)
   - Niuwë morre
   - Terminal mostra glitches
   - Transição para Ato 2
   - Sem opção (linear)

---

### 7.2. Ato 2 - Ella (Investigação, sobrenatural, escolhas que afetam final)

#### Cenas principais:

1. **ato2_inicio** (Nova vida)
   - Ella no dormitório, semana normal
   - Aula na Varguén, amigos (Nithönne, Tayrone)
   - Estranhamento quando passa pela estátua (Niuwë, Selenna, Taila)
   - Opções:
     - 1: "Ignorar sensação e ir para aula"
     - 2: "Investigar a estátua mais de perto"

2. **ato2_estatua**
   - Ella observa os três guerreiros esculpidos
   - Sente algo próximo
   - Pode ler placa com nomes
   - Descoberta: Niuwë era um deles
   - Opções:
     - 1: "Perguntar a amigos sobre Niuwë"
     - 2: "Ir direto para arquivo investigar"

3. **ato2_amigos_info**
   - Nithönne ou Tayrone comentam sobre Niuwë
   - Descrição varia conforme flags do Ato 1
   - Se `niuweSalvouMaisAlunos`: "herói que salvou muita gente"
   - Se `niuweFoiImpulsivo`: "aquele que agia sem pensar"
   - Opções:
     - 1: "Pedir mais detalhes"
     - 2: "Mudar de assunto calmamente"

4. **ato2_arquivo**
   - Ella acessa logs por password (pode ser encontrado)
   - Lê sobre eventos da Ponte
   - Sanidade pode diminuir aqui (dependendo de how much lê)
   - Opções:
     - 1: "Ler todos os arquivos (coragem alta, sanidade diminui)"
     - 2: "Ler pouco e sair (cautela, sanidade preservada)"

5. **ato2_quarto_rosa**
   - Ella tem acesso ao quarto onde Niuwë dormia (preservado?)
   - Urso de pelúcia na cama
   - Sensações estranhas
   - Talvez apareça sombra sorridente (depende de sanidade)
   - Opções:
     - 1: "Cuidar do quarto, ficar para investigar"
     - 2: "Sair rápido, sensação é demais"

6. **ato2_testes**
   - Preparação para se tornar guerreira
   - Testes físicos (taekwondo, tiro, rifle rosa)
   - Descrições mudam conforme sanidade da Ella
   - Opções variadas dependendo do teste

7. **ato2_estacao4** (Opcional, unlock com certas flags)
   - Se `niuweExplorouEstacao4 == true`: Ella sabe onde é
   - Ella explora, encontra pistas/artefatos
   - Muito perturbador se sanidade baixa
   - Opções:
     - 1: "Explorar completamente"
     - 2: "Sair rápido"

8. **ato2_ponte_eventos_ella** (Climax)
   - Ella enfrenta a Ponte dos Eventos
   - Encontra remanescentes de Niuwë
   - Pode encontrar Melaninne
   - Final: depende de sanidade e escolhas
   - Opções:
     - 1: "Lutar / Resistir"
     - 2: "Conversar / Compreender"

---

## 8. SISTEMAS AVANÇADOS

### 8.1. Sistema de Sanidade (`SanitySystem.java`)

Escala 0-100:
- **100-80**: Perfeitamente Sã - texto limpo, descrições objetivas
- **80-60**: Preocupada - ocasionais glitches, pequenas variações
- **60-40**: Paranóica - mais glitches, textos inicialmente corrompidos que se autocorrigem
- **40-20**: Dissociada - distorções severas, terminal comenta ações, reações estranhas dos NPCs
- **20-0**: Completamente Insana - textos altamente corrompidos, memórias confusas, reality break

**Método: `displayDistortedText(String text)`**
```java
// Com sanidade baixa, o texto aparece inicialmente assim:
"A Sombra m0v0-s3 p0r aí... [ERRO] ...move-se por aí..."
// Depois se autocorrige para:
"A Sombra move-se por aí..."
```

**Modificadores de Sanidade**:
- Ler muitos arquivos sobre Ponte: -15
- Encontrar objeto relacionado a Niuwë: -5
- Escolha "corajosa" em cena perigosa: -10
- Escolha "cautelosa": +5
- Passar tempo descansando: +10

---

### 8.2. Terminal "Vivo" (`TerminalSystem.java`)

O terminal reage ao jogador:

1. **Comentários em escolhas**:
   ```
   [VARGUEN] Você escolheu a opção arriscada.
   [VARGUEN] Será que é sábio?
   ```

2. **Auto-correção de texto**:
   ```
   A Sombra est4 aqui —> [CORRIGINDO] —> A Sombra está aqui
   ```

3. **Detecção de replay**:
   ```
   [VARGUEN] Eu lembro de você já ter feito isso antes.
   [VARGUEN] Desta vez será diferente?
   ```

4. **Anomalias relatadas**:
   ```
   [ANOMALIA] Inconsistência temporal detectada no setor 7.
   ```

---

### 8.3. Sistema de Memória (`MemorySystem.java`)

Ella pode "rever" cenas do Niuwë como logs:

```java
public void replayWakingUp(int ellaSanity) {
    if (ellaSanity > 60) {
        // Versão clara
        window.appendText("Niuwë acordou confuso. Onde estava?");
    } else {
        // Versão paranóica
        window.appendText("[ERRO - MEMÓRIA CORROMPIDA]\nNiuwë acordou. Medo. Sangue.");
    }
}
```

Cenas de replay possíveis:
- Acordar confuso na cápsula
- Conversa com amigo (Jorgenssen)
- Encontro na Ponte (em forma de "registro")
- Morte de Taila

---

### 8.4. Simulação de FileSystem (`VarguenFileSystem.java`)

Simula arquivos DOS:

```
C:\VARGUEN\LOGS\
├─ LOG_NIUWE.TXT (sempre presente)
├─ BRIDGE_EVENTS.DAT (sempre presente)
├─ ELLA_DATA.BIN (sempre presente, "corrompido")
├─ SYSTEM.LOG (sempre presente)
└─ ESTACAO4.LOG (só aparece se niuweExplorouEstacao4 == true)
```

Cada arquivo tem conteúdo pré-escrito:

```java
executeType("LOG_NIUWE.TXT") -> 
  "[16/11/2247 04:32:17] Acordei..."
  "[16/11/2247 04:35:42] Dasko estava aqui..."
  ...
```

---

### 8.5. Monitor de Inatividade (`InactivityMonitor.java`)

Thread daemon que imprime mensagens misteriosas a cada 10+ segundos de inatividade:

```
[10s inativo] "Você ainda está aí?"
[20s inativo] "Ela também esperava..."
[30s inativo] "VOLTE? POR FAVOR?" (urgente)
```

Banco com 15+ mensagens:
- De perspectiva de Melaninne
- De perspectiva da Ponte
- De perspectiva da Sombra

---

### 8.6. Gerenciador de Música (`MusicManager.java`)

4 temas musicais síntese PCM em loop:

1. **EXPLORATION** (Misterioso, tom menor, pausas longas)
2. **TENSION** (Notas altas repetitivas, para Ponte)
3. **DRAMATIC** (Progressão ascendente, clímaxes)
4. **MENU** (Melódico, amigável)

Efeitos sonoros:
- `playSoundKeyPress()` - som de tecla
- `playSoundError()` - erro/glitch
- `playSoundConfirm()` - confirmação
- `playSoundBoot()` - inicialização

**Feature futura**: Musik muda em tempo real com sanidade:
- Sanidade alta: melodia clara
- Sanidade média: pequenos statics
- Sanidade baixa: pitch distorcido, tempos errados

---

## 9. PASSWORD - CODIFICAÇÃO / DECODIFICAÇÃO

### 9.1. Esquema de Codificação

Formato: `VARG-XXXXXX` (10 caracteres, incluindo hífen)

Cada posição codifica um valor:

```
V A R G - X X X X X X
0 1 2 3   4 5 6 7 8 9

Posições 1-3: Versão (sempre "VAR")
Posição 4: Tipo (sempre "G")
Hífen: separador
Posições 5-6: Flags de Niuwë (encoding em base 36)
Posições 7-8: Sanidade inicial da Ella (00-99)
Posições 9-10: Checksum
```

### 9.2. Tabela de Flags

Use máscara de bits:

```
Bit 0: niuweFoiImpulsivo
Bit 1: niuweSalvouMaisAlunos
Bit 2: niuweExplorouEstacao4
Bit 3: niuweDialogouComDasko
Bit 4: niuweLutouDiretoComSepharoth
Bit 5: niuweAjudouJorgenssenRoger
```

Encode em **base 36** (0-9, A-Z):
```
byte flags = (niuweFoiImpulsivo ? 1 : 0) | 
             (niuweSalvouMaisAlunos ? 2 : 0) | 
             ...
String encoded = Integer.toString(flags, 36); // "A", "B", etc.
```

### 9.3. Exemplo Completo

```
Entrada:
- niuweSalvouMaisAlunos = true (Bit 1)
- ellaSanity = 75
- Flags byte = 0b00000010 = 2 (em base 36 = "2")

Password gerado: VARG-20759Y

Decodificação:
- Posição 5-6: "20" = 2 em base 36
- Posição 7-8: "75" = sanidade 75
- Posição 9-10: "9Y" = checksum
```

---

## 10. FLUXO GERAL DO JOGO

```
[Boot VARGUEN-OS] (3s)
        ↓
[Tela de Título] (aguarda ENTER)
        ↓
[Menu Principal] (aguarda 1-6)
        ├──1──→ [Reseta GameState] → Ato 1, Cena 1
        ├──2──→ [Password Screen] → Decodifica → Ato 2, Cena 1
        ├──3──→ [Arquivos DOS] → (volta ao menu)
        ├──4──→ [Opções] → (volta ao menu)
        ├──5──→ [Créditos] → (volta ao menu)
        └──6──→ [Sair] → System.exit(0)
        
[Gameplay - Ato 1]
    Niuwë → Sequ ência de cenas → Morte
        ↓
[Geração de Password] → exibe na tela
        ↓
[Volta ao Menu Principal]

[Gameplay - Ato 2]
    Ella → Sequência de cenas → Final (depende de sanidade + escolhas)
        ↓
[Créditos / Encerramento]
```

---

## 11. IMPLEMENTAÇÃO ATUAL (CHECKLIST)

✅ **Completado:**
- Boot screen MS-DOS com carregamento
- Tela de título com "Varguelia" em vermelho
- Menu principal com 6 opções
- PasswordScreen (entrada visual)
- LogsScreen (simulação DIR/TYPE)
- OptionsScreen (ajustes de som, velocidade, etc.)
- CreditsScreen (créditos rolando)
- MusicManager (4 temas + efeitos, síntese PCM)
- SanitySystem (0-100, distorções, paranoia)
- TerminalSystem (reações, auto-correção, detecção de replay)
- VarguenFileSystem (6 arquivos simulados)
- MemorySystem (replay de cenas com variações)
- InactivityMonitor (daemon com mensagens misteriosas)
- GameState com flags e meta-tracking
- UiState enum com 7 estados
- Compilação bem-sucedida (Exit Code 0)

⏳ **Em Progresso:**
- Integração completa do GameEngine
- Definição de todas as cenas (Ato 1 e 2)
- Password encoding/decoding (framework pronto)

❌ **Ainda a Fazer:**
- Cenas narrativas completas (Ato 1: 8 cenas, Ato 2: 8+ cenas)
- Aplicação de consequências (flags, sanidade)
- Efeito typewriter de texto (velocidade variável)
- Meta-interações avançadas (terminal que questiona realidade)
- Tela de game over / final variations
- Ajusta música com sanidade (pitch, tempo)
- Mobile/Web port (futuros, se necessário)

---

## 12. CONFIGURAÇÃO TÉCNICA

### 12.1. Requisitos

- Java 17+ (testado com Adobe Animate 2024 JRE)
- Swing (built-in)
- javax.sound.sampled (built-in)
- Sistema de 64-bit recomendado

### 12.2. Compilação

```bash
# Com Maven (futuro):
mvn clean compile

# Com javac direto:
javac -encoding UTF-8 -d out src/*.java \
                             src/core/*.java \
                             src/game/*.java \
                             src/managers/*.java
```

### 12.3. Execução

```bash
# Depois de compilar:
java -cp out VargueniaGame

# Com classpath específico:
java -cp "out:lib/*" VargueniaGame
```

### 12.4. Integração com Git

```bash
# Clonar:
git clone https://github.com/Juvinho/VargueliaGame.git

# Trabalhar:
git add -A
git commit -m "Mensagem"
git push origin main
```

---

## 13. ESTRUTURA DE CENAS - EXEMPLO

Para criar novas cenas, use esse padrão:

```java
// Em GameEngine.java ou classe separada SceneLoader.java
public static Scene createAto1Inicio() {
    Scene scene = new Scene();
    scene.id = "ato1_inicio";
    scene.player = Player.NIUWE;
    scene.text = "Você acorda no quarto com o toque de recolher soando...\n" +
                 "Alguém grita nos corredores.";
    
    scene.options = new ArrayList<>();
    scene.options.add(new SceneOption("1", "Correr direto pro elevador", "ato1_elevador"));
    scene.options.add(new SceneOption("2", "Ajudar alunos no dormitório", "ato1_corredor_alunos"));
    
    return scene;
}

// Registrar no mapa:
scenes.put("ato1_inicio", createAto1Inicio());
```

---

## 14. PRÓXIMOS PASSOS (PARA CONTINUAR DESENVOLVIMENTO)

1. **Implementar todas as cenas** (Ato 1 e 2)
   - Escrever narrativa (original, inspirada no livro)
   - Conectar navegação entre cenas

2. **Consequências de escolhas** em `GameEngine.aplicarConsequencias()`
   - Quando Niuwë escolhe "salvar alunos" → `niuweSalvouMaisAlunos = true`
   - Quando Ella lê muitos arquivos → `ellaSanity -= 20`

3. **Efeito typewriter em texto**
   - Incrementar caractere por caractere em delay configurável

4. **Música dinâmica**
   - Ajustar pitch/tempo com base em `ellaSanity` em tempo real

5. **Password encoding/decoding final**
   - Implementar checksum
   - Testar múltiplas decodificações

6. **Telas de final**
   - Múltiplos finais baseados em:
     - Sanidade final
     - Escolhas principais
     - Flags do Ato 1

7. **Testes e balanceamento**
   - Testar todas as rotas de cenas
   - Ajustar dificuldade/sanidade
   - Validar duração total (~2-3 horas por playthrough)

---

## 15. ARQUIVOS-FONTE - ÁRVORE COMPLETA

```
VargueliaGame/
├── .git/                           # Repositório Git
├── src/
│   ├── VargueniaGame.java         # Main + Orquestrador
│   ├── core/
│   │   ├── GameState.java         # Estado global
│   │   └── Scene.java             # Estrutura de cena
│   ├── game/
│   │   ├── GameWindow.java        # JFrame + JTextPane
│   │   ├── GameEngine.java        # Motor narrativo
│   │   ├── MenuScreen.java        # Menu principal
│   │   ├── PasswordScreen.java    # Password input
│   │   ├── LogsScreen.java        # Arquivos DOS
│   │   ├── OptionsScreen.java     # Configurações
│   │   ├── CreditsScreen.java     # Créditos
│   │   └── UiState.java           # Estados UI (enum)
│   └── managers/
│       ├── MusicManager.java      # Trilha sonora
│       ├── SanitySystem.java      # Sistema sanidade
│       ├── TerminalSystem.java    # Terminal "vivo"
│       ├── MemorySystem.java      # Replay memórias
│       ├── VarguenFileSystem.java # Arquivos DOS
│       ├── InactivityMonitor.java # Monitor inatividade
│       ├── RetroSoundGenerator.java # Síntese áudio
│       └── RetroMusicGenerator.java # Composição temas
├── out/                            # Compiled .class files
├── README.md                       # Documentação
└── BUILD_AND_RUN.txt              # Instruções
```

---

## 16. RESUMO EXECUTIVO

**Varguelia: Ella é Demais** é um jogo narrativo interativo que simula um programa MS-DOS de 1983, mas com mecânicas absurdamente avançadas:

- **Gameplay**: Escolhas em dois atos (Niuwë → Ella) que realmente afetam a narrativa
- **Estética**: Terminal preto com texto branco, bordas ASCII, som sintetizado
- **Meta-narrativa**: Sistema sensível ao estado do jogador, terminal que comenta, memórias replay
- **Sanidade**: Afeta como o texto é exibido, descrições NPCs, e acessibilidade de cenas
- **Password**: Codifica estado e permite continuar em novo playthrough

**Próximas semanas**: Implementar todas as cenas, testar fluxo narrativo, ajustar balanceamento.

---

**Última atualização**: Fevereiro 2026  
**Versão**: 0.2 (Menu e sistemas avançados funcionais)  
**Status**: Pronto para integração de narrativa completa
