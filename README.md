# 🎮 VARGUELIA - Ella é Demais

> Uma visual novel interativa em Java com narrativa expandida, sistema de escolhas ramificadas e efeitos audiovisuais.

## 📖 Sinopse

**Varguelia** é uma experiência narrativa imersiva ambientada em uma escola tomada por mistério. Você controla **Niuwë**, um estudante narcisista que deve coordenar uma resposta de emergência contra **Sepharoth**, uma criatura colossal de 27 cabeças que se aproxima ameaçadoramente.

O jogo combina:
- 💬 Diálogos com efeito typewriter de 50ms por caractere
- 🎯 Escolhas que ramificam a narrativa
- 🔊 Feedback auditivo (beeps do sistema)
- 🌈 Sistema de cores para 8 personagens únicos
- 📝 Quebra de linhas inteligente (65 caracteres/linha)

## 🚀 Início Rápido

### Requisitos
- **Java 17+** (Adobe Animate 2024 JRE recomendado)
- Windows/Linux/macOS

### Instalação

```bash
# Clone o repositório
git clone https://github.com/Juvinho/VargueliaGame.git
cd VargueliaGame

# Compile
javac -encoding UTF-8 src/VargueliaGameExpanded.java

# Ou use o script de compilação
./VarigueliaGame.bat  (Windows)
./run.sh              (Linux/macOS)
```

### Como Jogar

```bash
# Execute diretamente
cd src
java VargueliaGameExpanded

# Ou via Java com classpath
java -cp src VargueliaGameExpanded
```

**Controles:**
- `ENTER` - Avançar diálogos e aceitar escolhas
- `↑/W` - Menu para cima
- `↓/S` - Menu para baixo
- `ENTER` - Confirmar seleção
- `ESC` - Fechar jogo

## 📚 Estrutura do Projeto

```
VargueliaGame/
├── README.md                           # Este arquivo
├── src/
│   ├── VargueliaGameExpanded.java     # ⭐ Arquivo principal (892 linhas)
│   ├── VargueliaGameFinal.java        # Versão anterior (568 linhas)
│   ├── VargueliaRefactored.java       # Demonstração do padrão CardLayout
│   ├── VargueniaGame.java             # Versão original (arquivada)
│   ├── core/
│   │   ├── GameWindow.java
│   │   └── BootScreen.java
│   ├── game/
│   │   └── GameEngine.java
│   └── managers/
│       └── SoundManager.java
├── resources/
│   ├── sounds/
│   └── images/
├── out/                                 # Classes compiladas
└── .git/                               # Repositório Git
```

## 🎬 Recursos Implementados

### v2.0 EXPANDED (Atual)
- ✅ **32 cenas** organizadas em narrativa linear
- ✅ **SoundManager** com beeps em eventos
- ✅ **Text Wrapping** (quebra automática em 65 chars)
- ✅ **3 pontos de escolha** com branches narrativos
- ✅ **GameState** rastreando decisões do jogador
- ✅ **NarratorScene** para descrições cinemáticas
- ✅ **Password validation** (código: BRIDGE)
- ✅ **Typewriter effect** (50ms/char + cursor 400ms)
- ✅ Compilação UTF-8 sem erros

### Personagens (8 atores com cores únicas)

| Nome | Cor | Função |
|------|-----|--------|
| 🟡 **NIUWË** | Ouro (#FFD800) | Protagonista, estudante narcisista |
| 🔵 **TAILA** | Cyan (#00FFC8) | Piloto de helicóptero (Mensa-Viktör) |
| 🟣 **SELENNA** | Magenta (#FF64FF) | Combatente da equipe |
| 🔴 **JORGENSSEN** | Vermelho (#FF6464) | Líder da operação |
| 🧊 **ROGER** | Azul claro (#64C8FF) | Analista de dados |
| ⚪ **DASKO** | Branco (#C8C8C8) | Diretor da Escola |
| 💜 **ELLA** | Roxo (#C800C8) | Entidade misteriosa |
| 🩸 **SEPHAROTH** | Vermelho escuro (#C80000) | Antagonista (27 cabeças) |

## 🎮 Sistema de Cenas

### Tipos de Cena Implementados

#### 1. **TitleScreen**
- Animação de 150 estrelas
- Título pulsante "Press ENTER"
- Aviso de conteúdo impróprio

#### 2. **DialogueScene**
- Nome do personagem em cor única
- Typewriter effect (50ms por caractere)
- Cursor piscante (400ms)
- Avança com ENTER

#### 3. **NarratorScene** ⭐ NOVO
- Descrições cinemáticas
- Fonte itálica menor
- Label "[ NARRADOR ]"
- Quebra e formatação automática

#### 4. **ChoiceScene**
- Prompt com typewriter
- Menu com ↑↓/WS navigation
- Seleção destacada com ▶ marker
- 2-3 opções por cena

#### 5. **InputScene**
- Campo de texto seguro
- Validação de senha
- Erro formatado em vermelho
- Exemplo: código "BRIDGE"

## 📖 Fluxo Narrativo

### Ato 1: O Silêncio é um Bom Aliado

**Prólogo** (Cenas 1-3)
- Alarme de toque de recolher ecoa pela escola
- Caos entre alunos
- Diretor Dasko observa criatura se aproximando

**Niuwë** (Cenas 4-5)
- Personagem no chuveiro, narcisista
- Observa céu vermelho anormal

**Coordenação** (Cenas 6-10)
- Jorgenssen ativa plano de evacuação
- Roger calcula: 52 minutos até chegada
- Niuwë liga para Taila (helicóptero)

**### BRANCHING POINT 1: Reação de Niuwë**
```
├─ Reação SHOCK: "Merda… três cabeças?!" → Reação agressiva
├─ Reação DÚVIDA: "Você tem certeza?" → Análise
└─ Reação PRÁTICA: "Quanto tempo?" → Pragmatismo
```

**Mobilização** (Cenas 12-17)
- Niuwë organiza estudantes
- Coordena com Dasko e Taila
- Todos convergem para Estação 4

**### BRANCHING POINT 2: Estratégia de Combate**
```
├─ CONFRONTO: Enfrentar direto
├─ ANÁLISE: Roger identifica padrãos
└─ FUGA: Evacuação e regroup
```

**Revelação** (Cenas 24-30)
- Sepharoth aparece no horizonte
- 200m altura, 3 cabeças principais, 24 auxiliares
- Descrição de horror cósmico
- Sistema Genesis se ativa

**Código de Acesso** (Cena 31)
- Dasko pede código da Fundação
- Entrada: BRIDGE
- Sucesso → Ella aparece

**Desfecho** (Cena 32)
- Ella na ponte de comando
- Revelação da verdade sobre Varguelia
- Fim do prólogo

## 🛠️ Arquitetura Técnica

### Stack Tecnológico
- **GUI Framework**: Java Swing (CardLayout)
- **Threading**: javax.swing.Timer (animações)
- **Input**: KeyListener, InputMap/ActionMap
- **Encoding**: UTF-8
- **Compilação**: javac com suporte a Unicode

### Padrões de Design
- **CardLayout**: Gerenciamento de cenas
- **Enum Actors**: Sistema de personagens extensível
- **Inner Classes**: Encapsulamento de componentes
- **Runnable Callbacks**: Navegação entre cenas

### Classes Principais

#### VargueliaGameExpanded
```java
public class VargueliaGameExpanded extends JFrame {
    private CardLayout cardLayout;           // Gerenciador de cenas
    private JPanel root;                     // Painel raiz
    private SoundManager soundManager;       // Sons
    private GameState gameState;             // Estado da partida
}
```

#### GameState
```java
class GameState {
    String niuweReaction = "";   // Escolha: Como Niuwë reage?
    String strategy = "";        // Escolha: Qual estratégia?
}
```

#### SoundManager
```java
class SoundManager {
    void playCharacterSound() {
        SwingUtilities.invokeLater(() -> {
            Toolkit.getDefaultToolkit().beep();  // Beep sincronizado
        });
    }
}
```

#### Typewriter (Text Wrapping)
```java
static Writer create(Actor who, String text, Color color) {
    // Quebra automática em 65 caracteres
    List<String> lines = wrapText(text, 65);
}
```

## 🎨 Customização

### Adicionar novo personagem
```java
enum Actor {
    NOVO_CHAR(
        new Font("Monospaced", Font.PLAIN, 20),
        new Color(R, G, B)  // RGB customizado
    )
}
```

### Adicionar nova cena
```java
root.add(new DialogueScene(
    Actor.FULANO,
    "Seu diálogo aqui…",
    () -> gotoScene("proxima_cena")
), "id_unico");
```

### Ajustar velocidade do typewriter
```java
new javax.swing.Timer(50, e -> {  // Mudar 50ms aqui
    chars++;
    repaint();
})
```

## 📊 Stats do Projeto

| Métrica | Valor |
|---------|-------|
| Linhas de código | 893 |
| Cenas implementadas | 32 |
| Personagens | 8 |
| Pontos de escolha | 3 |
| Tipo de cenas | 5 |
| Commits | 7 |
| Histórico narrativo | 11,637 linhas |

## 🐛 Troubleshooting

### "Erro de encoding windows-1252"
```bash
# Use sempre -encoding UTF-8
javac -encoding UTF-8 src/VargueliaGameExpanded.java
```

### "Beep do sistema não funciona"
- Verifique permissões de áudio do SO
- Verifique se o terminal está com som habilitado
- Checked: `SwingUtilities.invokeLater()` para sincronização

### "Texto cortado nas cenas"
- Ajuste a largura da janela (1100x700 é o padrão)
- Reduza tamanho da fonte em `setFont()`

### "Escolha não responde"
- Clique na janela para garantir foco
- Pressione `UP`, `DOWN`, `W`, `S` ou `↑`, `↓`
- Confirme com `ENTER`

## 🚀 Futuro (Roadmap)

### v3.0 (Planejado)
- [ ] Integração de imagens de fundo
- [ ] Sistema de save/load
- [ ] Múltiplos finais baseados em branches
- [ ] Capítulos 2-5 da narrativa completa
- [ ] Efeitos visuais (glitch, transições)
- [ ] Tema escuro/claro customizável
- [ ] Localizações (PT-BR, EN, ES)

### v4.0+ (Ideias)
- [ ] Sistema de relacionamentos (sanidade/lealdade)
- [ ] Minigames (hacking, combate)
- [ ] Cinemáticas com sprites
- [ ] Música de fundo adaptativa
- [ ] Web version (GWT)

## 📝 Licença

Este projeto é de **código aberto** sob licença MIT.
Veja `LICENSE.md` para detalhes.

## 👥 Créditos

**Desenvolvimento:** GitHub Copilot + Juvinho
**Narrativa:** Baseado em "História" original
**Engine:** Java Swing, Adobe Animate 2024 JRE
**Data:** Fevereiro de 2026

## 📞 Suporte

Para relatórios de bugs ou sugestões:
1. Abra uma issue no GitHub
2. Descreva o problema com detalhes
3. Inclua a versão do Java usada
4. Anexe screenshots se possível

---

**Varguelia: Ella é Demais**  
*"Acredito em você, Niuwë…"* — Diretor Dasko

🎮 **Play now:** `java VargueliaGameExpanded`

