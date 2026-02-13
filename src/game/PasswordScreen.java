package game;

public class PasswordScreen {
    private GameWindow window;
    private StringBuilder passwordInput = new StringBuilder();
    private static final int PASSWORD_LENGTH = 10;  // Formato: XXXX-XXXXX
    
    public PasswordScreen(GameWindow window) {
        this.window = window;
    }
    
    public void display() {
        window.clearText();
        
        window.appendText("\n\n\n", "default");
        window.appendText("════════════════════════════════════════════════════════════════\n", "default");
        window.appendText("║                                                              ║\n", "default");
        window.appendText("║  ", "default");
        window.appendText("CONTINUAR JOGO", "cyan");
        window.appendText("                                      ║\n", "default");
        window.appendText("║                                                              ║\n", "default");
        window.appendText("════════════════════════════════════════════════════════════════\n\n", "default");
        
        window.appendText("Para continuar sua jornada, digite seu código de salva jogo:\n\n", "default");
        window.appendText("Código salvo na última vez:", "yellow");
        window.appendText(" [exemplo: VARG-1A2B3C]\n\n", "default");
        
        window.appendText("Digite o código: ", "cyan");
        
        // Mostrar entrada do usuário com mascaramento visual
        if (passwordInput.length() == 0) {
            window.appendText("_", "default");
        } else {
            window.appendText(formatPassword(passwordInput.toString()), "yellow");
        }
        
        window.appendText("\n\nPressione ", "default");
        window.appendText("ENTER", "cyan");
        window.appendText(" para confirmar \n", "default");
        window.appendText("ou ", "default");
        window.appendText("ESC", "cyan");
        window.appendText(" para voltar ao menu\n\n", "default");
        
        if (passwordInput.length() > 0) {
            window.appendText("Caracteres digitados: ", "yellow");
            window.appendText(passwordInput.length() + "/" + PASSWORD_LENGTH + "\n", "cyan");
        }
        
        window.appendText("════════════════════════════════════════════════════════════════\n", "default");
        window.setWaitingForInput(true);
    }
    
    /**
     * Formata a senha com padrão XXXX-XXXXX
     */
    private String formatPassword(String input) {
        if (input.length() >= 5 && !input.contains("-")) {
            // Inserir hífen automaticamente após 4 caracteres
            return input.substring(0, 4) + "-" + input.substring(4);
        }
        return input;
    }
    
    /**
     * Adiciona um caractere à entrada (apenas alfanuméricos)
     */
    public void addCharacter(char c) {
        if (Character.isLetterOrDigit(c) && passwordInput.length() < PASSWORD_LENGTH) {
            passwordInput.append(Character.toUpperCase(c));
            display();
        }
    }
    
    /**
     * Remove o último caractere
     */
    public void removeCharacter() {
        if (passwordInput.length() > 0) {
            passwordInput.deleteCharAt(passwordInput.length() - 1);
            display();
        }
    }
    
    /**
     * Decodifica o password e retorna se é válido
     */
    public boolean validateAndDecode(String password) {
        // Log: mostrar que está validando
        window.appendText("\n\n", "default");
        window.appendText("[VARGUEN] Validando password...", "yellow");
        window.appendText("\n", "default");
        
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Por enquanto, aceitar qualquer password com 9+ caracteres como válido
        if (password.replaceAll("-", "").length() >= 8) {
            window.appendText("[VARGUEN] ", "cyan");
            window.appendText("PASSWORD ACEITO!", "yellow");
            window.appendText("\n", "default");
            window.appendText("Carregando dados...\n", "default");
            return true;
        } else {
            window.appendText("[VARGUEN] ", "error");
            window.appendText("PASSWORD INVÁLIDO!", "error");
            window.appendText("\n", "default");
            window.appendText("Tente novamente.\n", "default");
            return false;
        }
    }
    
    /**
     * Retorna o password inserido
     */
    public String getPassword() {
        return passwordInput.toString();
    }
    
    /**
     * Limpa a entrada
     */
    public void clear() {
        passwordInput.setLength(0);
    }
}
