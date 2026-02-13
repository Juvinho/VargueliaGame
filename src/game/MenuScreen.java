package game;

public class MenuScreen {
    private GameWindow window;
    private int selectedOption = 0;
    private static final int COLS = 60;
    
    private String[] options = {
        "NOVO JOGO",
        "CONTINUAR (PASSWORD)",
        "ARQUIVOS DA VARGUËN",
        "OPÇÕES",
        "CRÉDITOS",
        "SAIR"
    };
    
    public MenuScreen(GameWindow window) {
        this.window = window;
    }
    
    /**
     * Repete uma string n vezes
     */
    private String repeat(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
    
    /**
     * Centraliza texto em coluna fixa
     */
    private String center(String text) {
        int left = (COLS - text.length()) / 2;
        if (left < 0) left = 0;
        return repeat(" ", left) + text;
    }
    
    public void display() {
        window.clearText();
        
        String border = "|" + repeat("-", COLS - 2) + "|";
        
        // Espaço inicial
        window.appendText("\n\n", "default");
        
        // Moldura superior em vermelho
        window.appendText(border + "\n", "error");
        window.appendText(center("Varguelia - Ella é Demais") + "\n", "error");
        window.appendText(border + "\n", "error");
        
        window.appendText("\n", "default");
        window.appendText(center("MENU PRINCIPAL") + "\n", "yellow");
        window.appendText("\n", "default");
        
        // Opções de menu centralizadas
        for (int i = 0; i < options.length; i++) {
            String prefix = (i + 1) + ". " + options[i];
            String line;
            
            if (i == selectedOption) {
                line = center("► " + prefix + " ◄");
                window.appendText(line + "\n", "cyan");
            } else {
                line = center(prefix);
                window.appendText(line + "\n", "default");
            }
            window.appendText("\n", "default");
        }
        
        // Moldura inferior em vermelho
        window.appendText(border + "\n", "error");
        window.appendText("\n", "default");
        
        // Instruções
        String instrucoes1 = "Use ↑/↓ ou 1-6 para escolher";
        String instrucoes2 = "Pressione ENTER para confirmar";
        window.appendText(center(instrucoes1) + "\n", "cyan");
        window.appendText(center(instrucoes2) + "\n", "cyan");
        
        window.setWaitingForInput(true);
    }
    
    public void handleUpArrow() {
        selectedOption = (selectedOption - 1 + options.length) % options.length;
        display();
    }
    
    public void handleDownArrow() {
        selectedOption = (selectedOption + 1) % options.length;
        display();
    }
    
    public String handleEnter() {
        return options[selectedOption];
    }
    
    public String handleNumberSelect(int number) {
        if (number >= 1 && number <= options.length) {
            selectedOption = number - 1;
            display();
            return options[selectedOption];
        }
        return null;
    }
    
    public int getSelectedOption() {
        return selectedOption;
    }
}
