/**
 * GUIA DE IMPLEMENTAÇÃO: FUNÇÕES AVANÇADAS PARA VARGUELIA
 * 
 * Este arquivo documenta como usar os novos sistemas para criar experiências
 * "perturbadoras" e meta-narrativas que parecem vivas e conscientes.
 */

// ============================================================================
// 1. SISTEMA DE SANIDADE (Ato 2 - Ella)
// ============================================================================

/*
Exemplo de uso em uma cena:

    // Em GameEngine ou Scene
    private SanitySystem sanitySystem;
    
    public void someScene() {
        // Ella descobre algo traumático
        sanitySystem.trauma(15);  // -15 sanidade
        
        // Usar descrição paranoia baseada em sanidade
        String desc = sanitySystem.getParanoiaDescription(
            "A estação parecia abandonada.",
            "A estação PARECIA estar esperando. Sempre esteve aqui?"
        );
        window.appendText(desc, "default");
        
        // Se sanidade muito baixa, mostrar conteúdo exclusivo
        if (sanitySystem.isState(SanitySystem.DISSOCIATED)) {
            window.appendText("Você vê pegadas que não deveriam estar aqui.\n", "error");
        }
        
        // Exibir texto distorcido que se auto-corrige
        sanitySystem.displayDistortedText("Uma mensagem importante do sistema.");
        
        // Terminal questiona a realidade
        if (sanitySystem.getSanity() < 40) {
            sanitySystem.printTerminalComment("Você confia em si mesma?");
        }
    }
*/

// ============================================================================
// 2. TERMINAL SYSTEM (Reações da Interface)
// ============================================================================

/*
Exemplo de uso:

    private TerminalSystem terminalSystem;
    
    public void handlePlayerChoice(String choiceText) {
        // Se foi uma decisão importante, terminal reage
        terminalSystem.onChoiceMade(choiceText, true);  // true = decisão importante
        
        // Terminal auto-corrige como se outra entidade editasse o log
        terminalSystem.selfCorrection(
            "Você salvou o sobrevivente.",
            "Você TENTOU salvar o sobrevivente."
        );
        
        // Detectar se é replay (player jogando novamente)
        terminalSystem.detectReplay();
        if (terminalSystem.getGameplayLoops() > 1) {
            // ELLA reconhece que Você já fez isso antes
            window.appendText("Você já escolheu assim antes?\n", "error");
        }
        
        // Registrar consequências no log
        String[] consequences = {
            "Niuwë ganhou os alunos",
            "Melaninne não desconfia",
            "A Ponte observou"
        };
        terminalSystem.printChoiceConsequences(consequences);
        
        // Relatório de anomalias
        terminalSystem.reportAnomaly(
            "ANOMALIA TEMPORAL: Arquivo modificado 2 vezes simultaneamente?"
        );
    }
*/

// ============================================================================
// 3. FILE SYSTEM (DOS Commands)
// ============================================================================

/*
Exemplo de uso em uma cena:

    private VarguenFileSystem fileSystem;
    
    public void fileAccessScene() {
        // Ella pode explorar arquivos do "PC de Varguén"
        fileSystem.executeDir("C:\\VARGUEN\\");
        
        // Lê um arquivo específico
        fileSystem.executeType("LOG_NIUWE.TXT");
        
        // O jogo cria novo arquivo quando Ella faz découvertas
        fileSystem.createFile(
            "ELLA_REPORT_001.TXT",
            "INVESTIGAÇÃO INICIAL\n[03:45] Encontrei anomalias temporais."
        );
        
        // Usar conteúdo de arquivo na narrativa
        String logContent = fileSystem.getFileContent("LOG_NIUWE.TXT");
        window.appendText(logContent, "default");
    }
*/

// ============================================================================
// 4. MEMORY SYSTEM (Replay com Variações)
// ============================================================================

/*
Exemplo de uso:

    private MemorySystem memorySystem;
    
    public void ellaReviewsMemories() {
        // Ella revê o despertar de Niuwë
        memorySystem.replayWakingUp(true);  // true = Niuwë sobreviveu
        
        // Replay de uma decisão moral do Ato 1
        memorySystem.replayMoralChoice("Jorgensen Roger", true);  // ajudou
        
        // Mostrar impacto daquela decisão
        memorySystem.showImpact(
            "Ela salvou Jorgensen",
            "Jorgensen agora é um arquivo corrompido"
        );
        
        // Replay customizado com distorção por sanidade
        memorySystem.replayScene(
            "O Encontro",
            "Niuwë viu uma forma na sombra.",
            "Niuwë VIU TUDO. O sorriso que a consumiu."
        );
    }
*/

// ============================================================================
// 5. INACTIVITY MONITOR (Mensagens Misteriosas)
// ============================================================================

/*
Exemplo de uso:

    private InactivityMonitor inactivityMonitor;
    
    public void startGameWithMonitor() {
        // Criar monitor que detecta inatividade
        inactivityMonitor = new InactivityMonitor(window);
        inactivityMonitor.setInactivityThreshold(10000);  // 10 segundos
        
        // Quando jogador interage, registrar
        @Override
        public void keyPressed(KeyEvent e) {
            inactivityMonitor.registerActivity();  // Reset timer
            // ... resto do código
        }
        
        // Ao sair do jogo
        @Override
        public void windowClosing() {
            inactivityMonitor.stop();
        }
    }
*/

// ============================================================================
// 6. INTEGRANDO TUDO NA NARRATIVA
// ============================================================================

/*
Estrutura recomendada para ATO 2 (ELLA):

    class AtoDoElla extends Scene {
        private SanitySystem sanity;
        private TerminalSystem terminal;
        private VarguenFileSystem fileSystem;
        private MemorySystem memory;
        
        public void initialize() {
            // Ella recebe "dados" de Niuwë
            terminal.displayDosCommand(
                "LOAD NIUWE_BACKUP.001",
                "255,456 bytes carregados de ARCHIVE01.VOL"
            );
            
            // Mostrar consequências do Ato 1
            if (gameState.niuweSalvouMaisAlunos) {
                fileSystem.createFile(
                    "SURVIVORS_LIST.TXT",
                    "17 alunos marcados como ALIVE\n" +
                    "Status atual: ???"
                );
            }
        }
        
        public void onChoice(String choice) {
            // Terminal reage à decisão dela
            terminal.onChoiceMade(choice, true);
            
            // Sanidade pode cair
            if (choice.contains("investigar")) {
                sanity.trauma(5);
            }
            
            // Se sanidade muito baixa, conteúdo especial
            if (sanity.isState(SanitySystem.PARANOID)) {
                // Versão sinister de eventos
                showParanoidInterpretation();
            }
        }
    }
*/

// ============================================================================
// 7. PASSWORD INTELIGENTE
// ============================================================================

/*
Exemplo de como integrar:

    public String generateAdvancedPassword() {
        StringBuilder pw = new StringBuilder();
        
        // Flags básicas (já existem)
        pw.append(gameState.generatePassword());
        
        // Novo: codificar sanidade final
        pw.append("_SN" + gameState.ellaSanity);
        
        // Novo: quantas vezes foi jogado
        pw.append("_LOP" + gameState.totalPlaythroughs);
        
        // Novo: quais memórias foram acessadas
        if (gameState.ellaAccessedFileSystem) {
            pw.append("_FS");
        }
        if (gameState.ellaViewedNiuweMemories) {
            pw.append("_MEM");
        }
        
        return pw;  // Exemplo: NW_EVA_S4_DLG_ST2_SN23_LOP2_FS_MEM
    }
    
    public void restoreFromAdvancedPassword(String password) {
        // Parse e restaura todo o estado complexo
        // Mundo é reconstruído com base nas variações
    }
*/

// ============================================================================
// 8. MÚSICA DINÂMICA COM SANIDADE
// ============================================================================

/*
Estender MusicManager para alterar tempo/pitch:

    public class MusicManager {
        public void adjustForSanity(int sanityLevel) {
            if (sanityLevel < SanitySystem.PARANOID) {
                // Aumentar tempo (mais lento e sinistro)
                this.tempoMultiplier = 0.8f;
                this.insertGlitches = true;
            }
            
            if (sanityLevel < SanitySystem.DISSOCIATED) {
                // Muito lento, tom distante
                this.tempoMultiplier = 0.6f;
                this.additionalDissonance = true;
            }
        }
    }
*/

// ============================================================================
// 9. EXEMPLOS DE CENAS QUE USAM OS SISTEMAS
// ============================================================================

/*
CENA 1: Ella descobre o arquivo de Niuwë

    public void scene_FindNiuweLogs() {
        terminal.displayDosCommand(
            "FIND *.LOG /S",
            "1 arquivo encontrado:\n" +
            "LOG_NIUWE_FINAL.TXT (23,456 K)"
        );
        
        System.out.println("Deseja ler? (S/N)");
        
        // Se SIM:
        fileSystem.executeType("LOG_NIUWE_FINAL.TXT");
        sanity.trauma(10);  // Chocante
        
        terminal.onChoiceMade("Leu os logs de Niuwë", true);
        terminal.reportAnomaly(
            "ARQUIVO CONTÉM REFERÊNCIAS À ENTIDADE DESCONHECIDA"
        );
    }

CENA 2: Ella reavalia uma decisão de Niuwë

    public void scene_EllaJudgesNiuwesChoice() {
        memory.replayMoralChoice("Jorgensen", gameState.niuweAjudouJorgenssenRoger);
        
        if (!gameState.niuweAjudouJorgenssenRoger) {
            sanity.printTerminalComment(
                "Niuwë deixou " + name + " para trás. " +
                "É você que está aqui agora."
            );
            sanity.trauma(20);
        }
    }

CENA 3: Inatividade desenha a Sombra

    public void scene_WaitingInTheDark() {
        // Se jogador ficar sem fazer nada por 15 segundos:
        inactivityMonitor.setInactivityThreshold(15000);
        
        // Uma mensagem aparece:
        // "[?] VOCÊ SENTE ALGO OLHANDO?"
        // E sanidade cai
    }
*/

// ============================================================================
// DICAS DE DESIGN
// ============================================================================

/*
1. PROGRESSIVE REVELATION:
   - Começa simples (UI normal, sem glitches)
   - Conforme Ella descobre coisas, aumenta a "contaminação"
   - Terminal fica cada vez mais vivo/paranoia
   
2. SANIDADE COMO MECÂNICA:
   - Obter informação = trauma
   - Algumas opções só aparecem em baixa sanidade
   - Final diferente dependendo da sanidade final
   
3. META-NARRATIVA:
   - Sistema comenta sobre replays
   - Detecta padrões de comportamento do jogador
   - Cria sensação de "algo sabe que você está jogando"
   
4. PASSWORD COMO STORY:
   - Não é salvar/carregar genérico
   - É reconstruir um universo alternativo
   - Cada password é uma "timeline" diferente
*/
