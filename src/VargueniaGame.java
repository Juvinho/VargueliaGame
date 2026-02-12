import game.GameWindow;
import game.GameEngine;
import game.MenuScreen;
import core.GameState;
import managers.RetroSoundGenerator;

public class VargueniaGame {
    private static GameWindow window;
    private static GameState gameState;
    private static GameEngine gameEngine;
    private static MenuScreen menuScreen;
    private static int currentScreen = 0; // 0 = menu, 1 = game
    
    public static void main(String[] args) {
        // Criar janela
        window = new GameWindow();
        window.setVisible(true);
        
        try {
            // Mostrar tela de boot com verificação
            showBootScreen();
            
            // Aguardar um pouco antes de mostrar menu
            Thread.sleep(1500);
            
            // Criar estado e menu
            gameState = new GameState();
            menuScreen = new MenuScreen(window);
            gameEngine = new GameEngine(window, gameState);
            
            // Mostrar menu
            window.setMenu(menuScreen);
            window.setInputHandler(createMenuHandler());
            menuScreen.display();
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private static void showBootScreen() throws InterruptedException {
        window.clearText();
        
        // Tocar som de startup
        RetroSoundGenerator.playStartupSound();
        
        window.appendText("\n");
        window.appendText("▄▀▀▀▀▄ SYSTEM BOOTING ▄▀▀▀▀▄\n\n");
        
        Thread.sleep(400);
        
        // Verificação de integridade
        window.appendText("> Initializing Varguën Terminal...\n");
        Thread.sleep(300);
        
        window.appendText("> Loading core systems");
        for (int i = 0; i < 3; i++) {
            window.appendText(".");
            Thread.sleep(200);
        }
        window.appendText(" OK\n");
        Thread.sleep(300);
        
        window.appendText("> Checking memory integrity");
        for (int i = 0; i < 3; i++) {
            window.appendText(".");
            Thread.sleep(200);
        }
        window.appendText(" PASSED\n");
        Thread.sleep(300);
        
        window.appendText("> Verifying archive data");
        for (int i = 0; i < 3; i++) {
            window.appendText(".");
            Thread.sleep(200);
        }
        window.appendText(" OK\n");
        Thread.sleep(300);
        
        window.appendText("> Loading narrative modules");
        for (int i = 0; i < 3; i++) {
            window.appendText(".");
            Thread.sleep(200);
        }
        window.appendText(" READY\n");
        Thread.sleep(300);
        
        window.appendText("> MEMORY: 64MB ONLINE\n");
        Thread.sleep(200);
        window.appendText("> STORAGE: 2.5GB AVAILABLE\n");
        Thread.sleep(200);
        window.appendText("> AUDIO SYSTEM: READY\n");
        Thread.sleep(200);
        window.appendText("> NEURAL LINK: CONNECTED\n");
        Thread.sleep(300);
        
        window.appendText("\n▀▄▄▄▄▀ BOOT COMPLETE ▀▄▄▄▄▀\n");
        window.appendText("================================================\n");
        window.appendText("Welcome to Fundação Varguélia Terminal System\n");
        window.appendText("Version 0.1 (1983)\n");
        window.appendText("All systems nominal.\n");
        window.appendText("================================================\n\n");
        
        window.appendText("Pressione ENTER para continuar...\n");
        window.setWaitingForInput(true);
        
        // Aguardar ENTER
        Object lock = new Object();
        window.setInputHandler(new GameWindow.InputHandler() {
            @Override
            public void onEnter() {
                synchronized (lock) {
                    lock.notify();
                }
            }
            @Override
            public void onChoice(String key) {}
            @Override
            public void onArrowUp() {}
            @Override
            public void onArrowDown() {}
        });
        
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static GameWindow.InputHandler createMenuHandler() {
        return new GameWindow.InputHandler() {
            @Override
            public void onEnter() {
                String selected = menuScreen.handleEnter();
                
                if (selected.equals("NOVO JOGO")) {
                    startNewGame();
                } else if (selected.equals("CONTINUAR COM PASSWORD")) {
                    showPasswordScreen();
                } else if (selected.equals("SAIR")) {
                    System.exit(0);
                }
            }
            
            @Override
            public void onChoice(String key) {
                String selected = menuScreen.handleNumberSelect(Integer.parseInt(key));
                if (selected != null) {
                    // Após selecionar com número, aguarda ENTER
                }
            }
            
            @Override
            public void onArrowUp() {
                menuScreen.handleUpArrow();
            }
            
            @Override
            public void onArrowDown() {
                menuScreen.handleDownArrow();
            }
        };
    }
    
    private static void startNewGame() {
        RetroSoundGenerator.playBeep(1200, 100);
        
        // Iniciar jogo
        window.setInputHandler(null);
        gameEngine = new GameEngine(window, gameState);
        window.setEngine(gameEngine);
        gameEngine.showCurrentScene();
    }
    
    private static void showPasswordScreen() {
        RetroSoundGenerator.playBeep(1200, 100);
        
        window.clearText();
        window.appendText("\n");
        window.appendText("═════════════════════════════════════════════════════════════════════\n");
        window.appendText("                    CONTINUAR MISSÃO\n");
        window.appendText("═════════════════════════════════════════════════════════════════════\n\n");
        window.appendText("Digite seu código de acesso (PASSWORD):\n\n");
        window.appendText("> ");
        
        // TODO: Implementar leitura de password
        // Por enquanto, voltar ao menu após um tempo
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        window.setInputHandler(createMenuHandler());
        menuScreen.display();
    }
}

