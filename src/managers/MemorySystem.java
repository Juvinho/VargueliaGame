package managers;

import game.GameWindow;

/**
 * Sistema que permite Ella rever cenas do Ato 1 (Niuwë) como logs reproduzidos.
 * As reprises são influenciadas pelas flags e sanidade de Ella,
 * mostrando versões ligeiramente diferentes dos mesmos eventos.
 */
public class MemorySystem {
    private GameWindow window;
    private SanitySystem sanitySystem;
    
    public MemorySystem(GameWindow window, SanitySystem sanitySystem) {
        this.window = window;
        this.sanitySystem = sanitySystem;
    }
    
    /**
     * Replay de cena genérica: Niuwë acordando na estação
     */
    public void replayWakingUp(boolean niuweSurvived) throws InterruptedException {
        window.clearText();
        window.appendText("\n\n", "default");
        window.appendText("[MEMORY REPLAY - ATO 1]\n", "cyan");
        window.appendText("Reconstruindo de LOG_NIUWE.TXT...\n\n", "default");
        Thread.sleep(500);
        
        if (sanitySystem.isState(SanitySystem.PARANOID)) {
            // Versão paranoia
            window.appendText("Niuwë acordou. Ou acordou?\n", "default");
            window.appendText("A estação oscilava. Ou era ela oscilando?\n", "error");
            window.appendText("Às ", "default");
            window.appendText("2:13 AM", "yellow");
            window.appendText(", a Ponte piscou em vermelho.\n", "default");
            Thread.sleep(400);
        } else {
            // Versão normal
            window.appendText("Niuwë acordou na Estação Vargüen.\n", "default");
            window.appendText("Tudo parecia normal. Mas não era.\n", "default");
            window.appendText("Às 2:13 AM, as luzes piscaram.\n", "default");
            Thread.sleep(300);
        }
        
        if (!niuweSurvived) {
            window.appendText("\n", "default");
            window.appendText("[NOTA: Este registro é incompleto.]\n", "error");
            window.appendText("[Niuwë não sobreviveu para completá-lo.]\n", "error");
            Thread.sleep(400);
        }
    }
    
    /**
     * Replay da escolha de ajudar ou ignorar alguém
     */
    public void replayMoralChoice(String targetName, boolean helped) throws InterruptedException {
        window.clearText();
        window.appendText("\n\n", "default");
        window.appendText("[MEMORY - " + targetName.toUpperCase() + "]\n", "cyan");
        Thread.sleep(300);
        
        if (helped) {
            window.appendText("Niuwë escolheu ajudar ", "default");
            window.appendText(targetName, "yellow");
            window.appendText(".\n", "default");
            
            if (sanitySystem.isState(SanitySystem.PARANOID)) {
                window.appendText("Mas era a escolha certa?\n", "error");
                window.appendText("Ou foi a Ponte que a fez escolher?\n", "error");
            }
        } else {
            window.appendText("Niuwë ignorou ", "default");
            window.appendText(targetName, "error");
            window.appendText(".\n", "default");
            
            if (sanitySystem.isState(SanitySystem.PARANOID)) {
                window.appendText("Agora ", "error");
                window.appendText(targetName, "yellow");
                window.appendText(" está aqui. Sempre esteve?\n", "error");
            }
        }
        
        Thread.sleep(500);
    }
    
    /**
     * Replay do encontro com Sepharot (o ser sombrio)
     */
    public void replayBridgeEncounter(int sanity) throws InterruptedException {
        window.clearText();
        window.appendText("\n\n", "default");
        window.appendText("[MEMORY - O ENCONTRO]\n", "cyan");
        Thread.sleep(400);
        
        if (sanity > SanitySystem.PARANOID) {
            window.appendText("Niuwë viu a forma na sombra.\n", "default");
            window.appendText("Parecia sorrir. Ou era uma ilusão de ótica?\n", "default");
        } else {
            window.appendText("Niuwë LA VIU.\n", "error");
            window.appendText("Os olhos. O sorriso que não deveria ser.\n", "error");
            window.appendText("Ela ", "error");
            window.appendText("AINDA", "yellow");
            window.appendText(" está sorrindo para você?\n", "error");
        }
        
        Thread.sleep(600);
    }
    
    /**
     * Replay genérico com distorção baseada em sanidade
     */
    public void replayScene(String sceneTitle, String normalVersion, 
                           String paranoidVersion) throws InterruptedException {
        window.clearText();
        window.appendText("\n\n[MEMORY - " + sceneTitle.toUpperCase() + "]\n", "cyan");
        Thread.sleep(300);
        
        if (sanitySystem.isState(SanitySystem.PARANOID)) {
            window.appendText(paranoidVersion, "error");
        } else {
            window.appendText(normalVersion, "default");
        }
        
        Thread.sleep(500);
    }
    
    /**
     * Feedback: mostrar como a decisão passada afeta agora
     */
    public void showImpact(String pastChoice, String currentConsequence) 
            throws InterruptedException {
        window.appendText("\n\n[IMPACTO DETECTADO]\n", "cyan");
        window.appendText("Escolha anterior: ", "default");
        window.appendText(pastChoice, "yellow");
        window.appendText("\n", "default");
        window.appendText("Consequência agora: ", "default");
        window.appendText(currentConsequence, "error");
        window.appendText("\n\n", "default");
        Thread.sleep(600);
    }
}
