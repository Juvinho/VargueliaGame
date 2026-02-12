import game.GameWindow;
import game.GameEngine;
import core.GameState;

public class VargueniaGame {
    public static void main(String[] args) {
        // Criar janela
        GameWindow window = new GameWindow();
        window.setVisible(true);
        
        // Criar estado do jogo
        GameState state = new GameState();
        
        // Criar engine e conectar com janela
        GameEngine engine = new GameEngine(window, state);
        window.setEngine(engine);
        
        // Mostrar tela de boot (simulado)
        try {
            showBootScreen(window);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private static void showBootScreen(GameWindow window) throws InterruptedException {
        window.clearText();
        window.appendText("▄▀▀▀▀▄ SYSTEM BOOTING ▄▀▀▀▀▄\n\n");
        
        Thread.sleep(300);
        window.appendText("> Initializing Varguën Terminal...\n");
        Thread.sleep(500);
        
        window.appendText("> Loading core systems");
        for (int i = 0; i < 3; i++) {
            window.appendText(".");
            Thread.sleep(300);
        }
        window.appendText("\n");
        
        Thread.sleep(400);
        window.appendText("> MEMORY: 64MB ONLINE\n");
        Thread.sleep(300);
        window.appendText("> STORAGE: 2.5GB AVAILABLE\n");
        Thread.sleep(300);
        window.appendText("> AUDIO SYSTEM: READY\n");
        Thread.sleep(300);
        
        window.appendText("\n▀▄▄▄▄▀ BOOT COMPLETE ▀▄▄▄▄▀\n");
        window.appendText("================================================\n");
        window.appendText("Welcome to Fundação Varguélia Terminal System\n");
        window.appendText("Version 0.1 (1983)\n");
        window.appendText("================================================\n\n");
        
        Thread.sleep(800);
        window.appendText("Pressione ENTER para iniciar...\n");
    }
}
