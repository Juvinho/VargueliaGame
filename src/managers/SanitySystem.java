package managers;

import game.GameWindow;

/**
 * Sistema de sanidade complexo que afeta a percepção visual do jogo.
 * Sanidade varia de 0 (insano) a 100 (perfeitamente lucido).
 * Afeta:
 * - Distorção de texto
 * - Descrições paranoides
 * - Conteúdo exclusivo de baixa sanidade
 * - Mensagens da "Ponte dos Eventos"
 */
public class SanitySystem {
    private int sanity = 100;  // Começa no máximo (Ella intacta)
    private GameWindow window;
    
    // Limiares de sanidade para estados mentais
    public static final int PERFECTLY_SANE = 80;
    public static final int WORRIED = 60;
    public static final int PARANOID = 40;
    public static final int DISSOCIATED = 20;
    public static final int COMPLETELY_INSANE = 0;
    
    public SanitySystem(GameWindow window) {
        this.window = window;
    }
    
    /**
     * Reduz sanidade por um evento traumático
     */
    public void trauma(int amount) {
        sanity = Math.max(0, sanity - amount);
        if (sanity < 50) {
            MusicManager.getInstance().startTheme(MusicManager.MusicTheme.TENSION);
        }
    }
    
    /**
     * Restaura sanidade (momentos de clareza/alívio)
     */
    public void recover(int amount) {
        sanity = Math.min(100, sanity + amount);
    }
    
    /**
     * Retorna o nível de sanidade atual
     */
    public int getSanity() {
        return sanity;
    }
    
    /**
     * Verifica se o jogador está em determinado nível mental
     */
    public boolean isState(int threshold) {
        return sanity <= threshold;
    }
    
    /**
     * Exibe texto com distorção baseada em sanidade
     * Em sanidade baixa, caracteres são trocados e se "corrigem" aos poucos
     */
    public void displayDistortedText(String text) throws InterruptedException {
        if (sanity > PARANOID) {
            // Sanidade alta: texto normal
            window.appendText(text, "default");
            return;
        }
        
        // Sanidade baixa: distorcer e corrigir
        String glitchChars = "#@*&%$!?><[]{}()ÄÖÜäöü¢£¥₧ñ§¶†‡ƒ„…‰";
        StringBuilder distorted = new StringBuilder();
        
        // Criar versão distorcida
        for (char c : text.toCharArray()) {
            if (Math.random() < 0.2) {
                distorted.append(glitchChars.charAt((int) (Math.random() * glitchChars.length())));
            } else {
                distorted.append(c);
            }
        }
        
        // Mostrar versão distorcida
        window.appendText(distorted.toString(), "error");
        Thread.sleep(300);
        
        // Limpar última linha e mostrar versão corrigida
        window.clearText();
        window.appendText(text, "default");
    }
    
    /**
     * Gera descrição paranoia baseada em sanidade
     * Mesma cena, mas interpretação diferente conforme sanidade
     */
    public String getParanoiaDescription(String normalDesc, String paranoiaDesc) {
        if (sanity <= PARANOID) {
            return paranoiaDesc;
        }
        return normalDesc;
    }
    
    /**
     * Adiciona "ruído" de outra entidade escrevendo (muito low sanity)
     */
    public void addExtraneousText(String extraText) throws InterruptedException {
        if (sanity <= DISSOCIATED) {
            window.appendText("\n\n", "default");
            window.appendText("[INTRUSO] > ", "error");
            window.appendText(extraText, "error");
            window.appendText("\n", "default");
            Thread.sleep(500);
        }
    }
    
    /**
     * Imprime mensagem do terminal como se questionasse o jogador
     */
    public void printTerminalComment(String comment) throws InterruptedException {
        window.appendText("\n\n", "default");
        window.appendText("[VARGUEN] ? > ", "cyan");
        window.appendText(comment, "yellow");
        window.appendText("\n", "default");
        Thread.sleep(600);
    }
    
    /**
     * Obtém descrição do estado mental do jogador (para logs)
     */
    public String getStateDescription() {
        if (sanity > PERFECTLY_SANE) {
            return "ESTÁVEL";
        } else if (sanity > WORRIED) {
            return "CONCENTRADA";
        } else if (sanity > PARANOID) {
            return "ANSIOSA";
        } else if (sanity > DISSOCIATED) {
            return "DESVINCULADA";
        } else {
            return "CRÍTICA";
        }
    }
}
