package game;

public class CreditsScreen {
    private GameWindow window;
    private int scrollPosition = 0;
    private static final String[] CREDITS_TEXT = {
        "",
        "",
        "════════════════════════════════════════════════════════════════",
        "",
        "A FUNDAÇÃO VARGUÉLIA",
        "A Lenda de 1983",
        "",
        "════════════════════════════════════════════════════════════════",
        "",
        "",
        "Desenvolvido por:",
        "JN GAMES STUDIO",
        "",
        "",
        "Inspirado em:",
        "'Fundação Varguélia - Ella é Demais'",
        "",
        "",
        "Prólogo Narrativo:",
        "Niuwë despertou em uma estação que não era sua.",
        "Agora, Ella procura pelas respostas que ele deixou para trás.",
        "",
        "",
        "Sistemas Avançados:",
        "• Sistema de Sanidade Dinâmico",
        "• Memórias Interativas e Reconfiguráveis",
        "• Terminal Sensível ao Estado do Jogador",
        "• Decodificação de Passwords Avançada",
        "",
        "",
        "VARGUEN-OS (c) 1983",
        "Todos os direitos reservados.",
        "",
        "",
        "════════════════════════════════════════════════════════════════",
        "",
        "Obrigado por jogar!",
        "",
        "Pressione qualquer tecla para voltar ao menu...",
        ""
    };
    
    public CreditsScreen(GameWindow window) {
        this.window = window;
    }
    
    public void display() {
        window.clearText();
        
        // Mostrar janela de créditos
        int visibleLines = 20;  // Quantas linhas mostrar por vez
        
        for (int i = 0; i < visibleLines && (scrollPosition + i) < CREDITS_TEXT.length; i++) {
            String line = CREDITS_TEXT[scrollPosition + i];
            
            if (line.contains("════")) {
                window.appendText(line + "\n", "cyan");
            } else if (line.contains("Desenvolvido por:") || 
                      line.contains("Inspirado em:") ||
                      line.contains("Prólogo Narrativo:") ||
                      line.contains("Sistemas Avançados:")) {
                window.appendText(line + "\n", "yellow");
            } else if (line.startsWith("•")) {
                window.appendText(line + "\n", "cyan");
            } else if (line.contains("VARGUEN-OS") || 
                      line.contains("Obrigado por jogar!") ||
                      line.contains("A FUNDAÇÃO VARGUÉLIA")) {
                window.appendText(line + "\n", "error");
            } else {
                window.appendText(line + "\n", "default");
            }
        }
        
        window.setWaitingForInput(true);
    }
    
    /**
     * Rola os créditos para baixo
     */
    public void scrollDown() {
        if (scrollPosition < CREDITS_TEXT.length - 20) {
            scrollPosition++;
            display();
        }
    }
    
    /**
     * Rola os créditos para cima
     */
    public void scrollUp() {
        if (scrollPosition > 0) {
            scrollPosition--;
            display();
        }
    }
    
    /**
     * Verifica se chegou ao final dos créditos
     */
    public boolean isAtEnd() {
        return scrollPosition >= CREDITS_TEXT.length - 20;
    }
    
    /**
     * Auto-scroll para demonstração (pode ser chamado em thread)
     */
    public void autoScroll() {
        while (!isAtEnd()) {
            scrollDown();
            try {
                Thread.sleep(500);  // Aguardar 500ms entre rolagens
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
