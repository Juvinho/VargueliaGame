package game;

import java.awt.*;

public class MenuScreen {
    private GameWindow window;
    private int selectedOption = 0;
    private String[] options = {"NOVO JOGO", "CONTINUAR COM PASSWORD", "ÁUDIO: LIGADO", "SAIR"};
    private boolean waitingForSelection = true;
    
    public MenuScreen(GameWindow window) {
        this.window = window;
    }
    
    public void display() {
        window.clearText();
        
        // Cabeçalho
        window.appendText("\n\n\n", "default");
        window.appendText("┌───────────────────────────────────────────────────────────────────┐\n", "default");
        window.appendText("│                                                                   │\n", "default");
        window.appendText("│                   ", "default");
        window.appendText("FUNDAÇÃO VARGUÉLIA", "cyan");
        window.appendText("                            │\n", "default");
        window.appendText("│                                                                   │\n", "default");
        window.appendText("│                      ", "default");
        window.appendText("ELLA É DEMAIS", "yellow");
        window.appendText("                          │\n", "default");
        window.appendText("│                                                                   │\n", "default");
        window.appendText("└───────────────────────────────────────────────────────────────────┘\n", "default");
        
        window.appendText("\n", "default");
        window.appendText("═══════════════════════════════════════════════════════════════════\n", "default");
        window.appendText("\n\n", "default");
        
        // Opções
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                window.appendText("                    ", "default");
                window.appendText("►", "cyan");
                window.appendText(" ");
                window.appendText(options[i], "cyan");
                window.appendText(" ", "default");
                window.appendText("◄\n", "cyan");
            } else {
                window.appendText("                       " + options[i] + "\n", "default");
            }
            window.appendText("\n", "default");
        }
        
        window.appendText("\n", "default");
        window.appendText("═══════════════════════════════════════════════════════════════════\n", "default");
        window.appendText("\n", "default");
        window.appendText("Use ", "default");
        window.appendText("SETAS", "cyan");
        window.appendText(" para navegar | ", "default");
        window.appendText("ENTER", "cyan");
        window.appendText(" para selecionar\n", "default");
        window.appendText("ou digite ", "default");
        window.appendText("1, 2, 3, 4\n", "cyan");
        
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
    
    public void updateAudioOption(boolean enabled) {
        options[2] = enabled ? "ÁUDIO: LIGADO" : "ÁUDIO: DESLIGADO";
        display();
    }
}
