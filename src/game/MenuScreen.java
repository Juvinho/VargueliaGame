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
    
    /**
     * Imprime linha em vermelho
     */
    private void printlnRed(String text) {
        try {
            window.getStyledDocument().insertString(
                window.getStyledDocument().getLength(), 
                text + "\n", 
                window.getStyledDocument().getStyle("red")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Imprime linha com estilo customizado
     */
    private void printlnStyled(String text, String style) {
        try {
            window.getStyledDocument().insertString(
                window.getStyledDocument().getLength(), 
                text + "\n", 
                window.getStyledDocument().getStyle(style)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void display() {
        window.clearText();
        
        String border = "|" + repeat("-", COLS - 2) + "|";
        
        // Espaço inicial
        window.appendText("\n\n", "default");
        
        // Moldura superior em vermelho
        printlnRed(border);
        printlnRed(center("Varguelia - Ella é Demais"));
        printlnRed(border);
        
        window.appendText("\n", "default");
        printlnStyled(center("MENU PRINCIPAL"), "yellow");
        window.appendText("\n", "default");
        
        // Opções de menu centralizadas
        for (int i = 0; i < options.length; i++) {
            String prefix = (i + 1) + ". " + options[i];
            String line;
            
            if (i == selectedOption) {
                line = center("► " + prefix + " ◄");
                printlnStyled(line, "cyan");
            } else {
                line = center(prefix);
                window.appendText(line + "\n", "default");
            }
            window.appendText("\n", "default");
        }
        
        // Moldura inferior em vermelho
        printlnRed(border);
        window.appendText("\n", "default");
        
        // Instruções
        String instrucoes1 = "Use ↑/↓ ou 1-6 para escolher";
        String instrucoes2 = "Pressione ENTER para confirmar";
        printlnStyled(center(instrucoes1), "cyan");
        printlnStyled(center(instrucoes2), "cyan");
        
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
