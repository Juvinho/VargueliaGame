package game;

import java.awt.*;

public class MenuScreen {
    private GameWindow window;
    private int selectedOption = 0;
    private String[] options = {"NOVO JOGO", "CONTINUAR COM PASSWORD", "SAIR"};
    private boolean waitingForSelection = true;
    
    public MenuScreen(GameWindow window) {
        this.window = window;
    }
    
    public void display() {
        StringBuilder menu = new StringBuilder();
        
        // Título em vermelho (simulado com caracteres destaque)
        menu.append("\n\n\n");
        menu.append("╔═══════════════════════════════════════════════════════════════════╗\n");
        menu.append("║                                                                   ║\n");
        menu.append("║                      FUNDAÇÃO VARGUÉLIA                           ║\n");
        menu.append("║                                                                   ║\n");
        menu.append("║                      ELLA É DEMAIS                                ║\n");
        menu.append("║                                                                   ║\n");
        menu.append("╚═══════════════════════════════════════════════════════════════════╝\n");
        menu.append("\n");
        menu.append("═════════════════════════════════════════════════════════════════════\n");
        menu.append("\n");
        
        // Opções
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                menu.append("                    ► ").append(options[i]).append(" ◄\n");
            } else {
                menu.append("                      ").append(options[i]).append("\n");
            }
            menu.append("\n");
        }
        
        menu.append("\n═════════════════════════════════════════════════════════════════════\n");
        menu.append("\n");
        menu.append("Use SETAS para navegar | ENTER para selecionar\n");
        menu.append("ou digite 1, 2, 3\n");
        
        window.setText(menu.toString());
        window.setWaitingForInput(true);
    }
    
    public void handleUpArrow() {
        selectedOption = (selectedOption - 1 + options.length) % options.length;
        managers.RetroSoundGenerator.playSelectBeep();
        display();
    }
    
    public void handleDownArrow() {
        selectedOption = (selectedOption + 1) % options.length;
        managers.RetroSoundGenerator.playSelectBeep();
        display();
    }
    
    public String handleEnter() {
        managers.RetroSoundGenerator.playBeep(1000, 50);
        return options[selectedOption];
    }
    
    public String handleNumberSelect(int number) {
        if (number >= 1 && number <= options.length) {
            selectedOption = number - 1;
            managers.RetroSoundGenerator.playSelectBeep();
            display();
            return options[selectedOption];
        }
        return null;
    }
    
    public int getSelectedOption() {
        return selectedOption;
    }
}
