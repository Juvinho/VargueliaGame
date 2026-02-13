package game;

public class MenuScreen {
    private GameWindow window;
    private int selectedOption = 0;
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
    
    public void display() {
        window.clearText();
        
        // Espaço inicial
        window.appendText("\n\n", "default");
        
        // Moldura superior com título
        window.appendText("════════════════════════════════════════════════════════════════\n", "default");
        window.appendText("║                                                              ║\n", "default");
        window.appendText("║  ", "default");
        window.appendText("Varguelia", "error");  // Vermelho
        window.appendText(" - Ella é Demais                               ║\n", "default");
        window.appendText("║                                                              ║\n", "default");
        window.appendText("════════════════════════════════════════════════════════════════\n\n", "default");
        
        // Subtítulo
        window.appendText("         MENU PRINCIPAL\n\n", "yellow");
        
        // Opções de menu
        for (int i = 0; i < options.length; i++) {
            String prefix = (i + 1) + ". ";
            
            if (i == selectedOption) {
                window.appendText("         ", "default");
                window.appendText("►", "cyan");
                window.appendText(" ");
                window.appendText(prefix, "cyan");
                window.appendText(options[i], "cyan");
                window.appendText(" ", "default");
                window.appendText("◄\n", "cyan");
            } else {
                window.appendText("           ");
                window.appendText(prefix);
                window.appendText(options[i] + "\n");
            }
            window.appendText("\n", "default");
        }
        
        window.appendText("════════════════════════════════════════════════════════════════\n\n", "default");
        window.appendText("Use ", "default");
        window.appendText("↑/↓", "cyan");
        window.appendText(" ou ", "default");
        window.appendText("1-6", "cyan");
        window.appendText(" para escolher | ", "default");
        window.appendText("ENTER", "cyan");
        window.appendText(" para confirmar\n", "default");
        
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
