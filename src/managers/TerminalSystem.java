package managers;

import game.GameWindow;

/**
 * Sistema que controla reações do próprio terminal/interface.
 * O terminal não é passivo - comenta decisões, questiona, faz glitches,
 * como se algo (a Ponte dos Eventos?) estivesse editando o log em tempo real.
 */
public class TerminalSystem {
    private GameWindow window;
    private int choicesMade = 0;
    private int gameplayLoops = 0;  // Detecta se é replay
    
    public TerminalSystem(GameWindow window) {
        this.window = window;
    }
    
    /**
     * Quando jogador faz uma escolha, terminal reagir
     */
    public void onChoiceMade(String choiceText, boolean isMainDecision) throws InterruptedException {
        choicesMade++;
        
        if (!isMainDecision) {
            return;  // Só reagir a decisões importantes
        }
        
        // Aleatoriamente, o terminal questiona a escolha
        if (Math.random() < 0.3) {
            Thread.sleep(500);
            String[] comments = {
                "Essa não era a sua única opção...",
                "Interessante. Mesmo assim?",
                "Você tem certeza?",
                "A Ponte está observando esta decisão.",
                "Registrado. Continue.",
                "Você seria diferente em outro tempo?"
            };
            
            String comment = comments[(int)(Math.random() * comments.length)];
            window.appendText("\n\n", "default");
            window.appendText("[SISTEMA] ", "cyan");
            window.appendText(comment, "yellow");
            window.appendText("\n", "default");
            Thread.sleep(600);
        }
    }
    
    /**
     * Terminal se auto-corrige como se outra entidade editasse o log
     */
    public void selfCorrection(String wrongText, String correctText) throws InterruptedException {
        // Exibir texto errado
        window.appendText(wrongText, "default");
        Thread.sleep(400);
        
        // Glitch visual enquanto "corrige"
        String glitchVersion = wrongText.replaceAll("[a-zA-Z]", "#");
        window.clearText();
        window.appendText(glitchVersion, "error");
        Thread.sleep(200);
        
        // Substituir pela versão corrigida
        window.clearText();
        window.appendText(correctText, "default");
    }
    
    /**
     * Questiona o jogador sobre reexecução/replay
     */
    public void detectReplay() throws InterruptedException {
        gameplayLoops++;
        
        if (gameplayLoops > 1) {
            Thread.sleep(800);
            window.appendText("\n\n", "default");
            window.appendText("[VARGUEN] ", "cyan");
            window.appendText("Você já esteve aqui antes, não esteve?\n", "yellow");
            Thread.sleep(600);
        }
    }
    
    /**
     * Exibe comando DOS fake como se terminal estivesse explorando arquivos
     */
    public void displayDosCommand(String command, String output) throws InterruptedException {
        window.appendText("\n\n", "default");
        window.appendText("C:\\VARGUEN> ", "cyan");
        window.appendText(command, "default");
        window.appendText("\n\n", "default");
        Thread.sleep(300);
        window.appendText(output, "default");
        window.appendText("\n", "default");
        Thread.sleep(500);
    }
    
    /**
     * Imprime lista de "efeitos" que as decisões tiveram no mundo
     */
    public void printChoiceConsequences(String[] consequences) throws InterruptedException {
        window.appendText("\n\n", "default");
        window.appendText("[VARGUEN] Consequências registradas:\n", "cyan");
        
        for (String consequence : consequences) {
            window.appendText(" • ", "yellow");
            window.appendText(consequence, "default");
            window.appendText("\n", "default");
            Thread.sleep(150);
        }
    }
    
    /**
     * Anomalia detectada - algo não está certo
     */
    public void reportAnomaly(String anomaly) throws InterruptedException {
        Thread.sleep(400);
        window.appendText("\n\n", "error");
        window.appendText("[!] ANOMALIA DETECTADA [!]\n", "error");
        window.appendText(anomaly, "error");
        window.appendText("\n\n", "default");
        Thread.sleep(700);
    }
    
    /**
     * Mensagem da Ponte dos Eventos - como glitch cósmico
     */
    public void bridgeMessage(String message) throws InterruptedException {
        // Efeito de glitch temporal
        for (int i = 0; i < 2; i++) {
            window.clearText();
            window.appendText(message, "default");
            Thread.sleep(100);
            window.clearText();
            window.appendText(message, "error");
            Thread.sleep(100);
        }
        window.clearText();
        window.appendText(message, "cyan");
        Thread.sleep(800);
    }
    
    /**
     * Registra linha de log como se o terminal estivesse documentando
     */
    public void logEntry(String timestamp, String entry) throws InterruptedException {
        window.appendText("\n[LOG ", "default");
        window.appendText(timestamp, "yellow");
        window.appendText("] ", "default");
        window.appendText(entry, "default");
        window.appendText("\n", "default");
        Thread.sleep(100);
    }
    
    public int getChoicesMade() {
        return choicesMade;
    }
    
    public int getGameplayLoops() {
        return gameplayLoops;
    }
}
