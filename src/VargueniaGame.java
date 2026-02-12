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
    private static ApplicationState appState = ApplicationState.BOOT;
    
    private enum ApplicationState {
        BOOT, MENU, GAME, PASSWORD
    }
    
    public static void main(String[] args) {
        // Criar janela
        window = new GameWindow();
        window.setVisible(true);
        
        try {
            // Mostrar tela de boot
            showBootScreen();
            
            // Criar estado e menu
            gameState = new GameState();
            menuScreen = new MenuScreen(window);
            
            // Trocar para tela de menu
            appState = ApplicationState.MENU;
            menuScreen.display();
            window.setInputHandler(createMenuHandler());
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private static void showBootScreen() throws InterruptedException {
        window.clearText();
        appState = ApplicationState.BOOT;
        
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
        
        Thread.sleep(1000);
    }
    
    private static GameWindow.InputHandler createMenuHandler() {
        return new GameWindow.InputHandler() {
            @Override
            public void onEnter() {
                if (appState != ApplicationState.MENU) return;
                
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
                if (appState != ApplicationState.MENU) return;
                
                try {
                    String selected = menuScreen.handleNumberSelect(Integer.parseInt(key));
                    // Apenas muda a seleção, não confirma
                } catch (NumberFormatException e) {
                    // Ignorar
                }
            }
            
            @Override
            public void onArrowUp() {
                if (appState != ApplicationState.MENU) return;
                menuScreen.handleUpArrow();
            }
            
            @Override
            public void onArrowDown() {
                if (appState != ApplicationState.MENU) return;
                menuScreen.handleDownArrow();
            }
        };
    }
    
    private static void startNewGame() {
        RetroSoundGenerator.playBeep(1200, 100);
        
        // Iniciar jogo
        appState = ApplicationState.GAME;
        gameEngine = new GameEngine(window, gameState);
        window.setEngine(gameEngine);
        window.setInputHandler(null); // Game engine vai lidar com input
        gameEngine.showCurrentScene();
    }
    
    private static void showPasswordScreen() {
        RetroSoundGenerator.playBeep(1200, 100);
        appState = ApplicationState.PASSWORD;
        
        window.clearText();
        window.appendText("\n");
        window.appendText("═════════════════════════════════════════════════════════════════════\n");
        window.appendText("                    CONTINUAR MISSÃO\n");
        window.appendText("═════════════════════════════════════════════════════════════════════\n\n");
        window.appendText("Digite seu código de acesso (PASSWORD):\n\n");
        window.appendText("> ");
        
        window.setInputHandler(new GameWindow.InputHandler() {
            private StringBuilder password = new StringBuilder();
            
            @Override
            public void onEnter() {
                if (password.toString().isEmpty()) {
                    return;
                }
                
                // Tentar restaurar estado
                gameState.restoreFromPassword(password.toString());
                
                // Voltar ao menu
                appState = ApplicationState.MENU;
                menuScreen.display();
                window.setInputHandler(createMenuHandler());
            }
            
            @Override
            public void onChoice(String key) {
                // Ignorar números durante entrada de password
            }
            
            @Override
            public void onArrowUp() {}
            
            @Override
            public void onArrowDown() {}
        });
    }
    
    public static ApplicationState getAppState() {
        return appState;
    }
}

