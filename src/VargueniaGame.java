import game.GameWindow;
import game.GameEngine;
import game.MenuScreen;
import core.GameState;
import managers.RetroSoundGenerator;
import managers.RetroMusicGenerator;
import managers.MusicManager;

public class VargueniaGame {
    private static GameWindow window;
    private static GameState gameState;
    private static GameEngine gameEngine;
    private static MenuScreen menuScreen;
    private static ApplicationState appState = ApplicationState.BOOT;
    private static boolean audioEnabled = true;
    
    private enum ApplicationState {
        BOOT, MENU, GAME, PASSWORD
    }
    
    public static void main(String[] args) {
        // Criar janela
        window = new GameWindow();
        window.setVisible(true);
        
        try {
            // Mostrar tela de boot estilo MS-DOS 1986
            showBootScreenMSDOS();
            
            // Criar estado e menu
            gameState = new GameState();
            menuScreen = new MenuScreen(window);
            
            // Trocar para tela de menu
            appState = ApplicationState.MENU;
            window.setColorScheme(GameWindow.ColorScheme.OS_TERMINAL);
            menuScreen.display();
            window.setInputHandler(createMenuHandler());
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private static void showBootScreenMSDOS() throws InterruptedException {
        window.setColorScheme(GameWindow.ColorScheme.OS_TERMINAL);
        window.clearText();
        appState = ApplicationState.BOOT;
        
        // Efeito de boot do sistema
        if (audioEnabled) {
            MusicManager.getInstance().playSoundBoot();
        }
        
        Thread.sleep(800); // Pausa dramática
        
        // === FASE 1: Logo VARGUEN OS ===
        StringBuilder screen = new StringBuilder();
        screen.append("\n\n");
        screen.append("┌────────────────────────────────────────────────────────────────────┐\n");
        screen.append("│                      V A R G U E N . O S                           │\n");
        screen.append("│                                                                    │\n");
        screen.append("│                   Version 1.0 - 1983 Hardware                      │\n");
        screen.append("└────────────────────────────────────────────────────────────────────┘\n");
        screen.append("\n");
        
        window.setText(screen.toString());
        Thread.sleep(600);
        
        // === FASE 2: Verificação de Hardware ===
        window.showBootLoadingMessage("VARGUEN HARDWARE CHECK");
        Thread.sleep(100);
        
        window.showBootLoadingMessage("MEMORY:          256K");
        Thread.sleep(100);
        
        window.showBootLoadingMessage("SYSTEM BUS:      ACTIVE");
        Thread.sleep(100);
        
        window.showBootLoadingMessage("COM. ARRAY:      INITIALIZED");
        Thread.sleep(200);
        
        // === FASE 3: Carregando programa principal ===
        screen = new StringBuilder();
        screen.append("┌────────────────────────────────────────────────────────────────────┐\n");
        screen.append("│                      V A R G U E N . O S                           │\n");
        screen.append("│                                                                    │\n");
        screen.append("│                   Version 1.0 - 1983 Hardware                      │\n");
        screen.append("└────────────────────────────────────────────────────────────────────┘\n");
        screen.append("\n\n");
        
        window.setText(screen.toString());
        
        window.showBootLoadingMessage("LOADING GAME.EXE: ELLA_DEMAIS.COM");
        window.showBootLoadingMessage("LOADING NARRATIVE DATABASE");
        window.showBootLoadingMessage("INITIALIZING TEMPORAL PROTOCOL");
        window.showBootLoadingMessage("BRIDGE MONITOR: ACTIVE  ???");
        
        Thread.sleep(400);
        
        // === FASE 4: Sistema pronto, aguardando ENTER ===
        screen = new StringBuilder();
        screen.append("┌────────────────────────────────────────────────────────────────────┐\n");
        screen.append("│                      V A R G U E N . O S                           │\n");
        screen.append("│                        FUNDAÇÃO VARGUÉLIA                         │\n");
        screen.append("│                                                                    │\n");
        screen.append("│                    ELLA É DEMAIS [v1.0]                           │\n");
        screen.append("└────────────────────────────────────────────────────────────────────┘\n");
        screen.append("\n\n");
        screen.append("          Press ENTER to access the memory banks of Varguén...\n");
        screen.append("                       (or DIE trying)\n");
        
        window.setText(screen.toString());
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
        // Iniciar música de menu ao entrar no handler
        MusicManager.getInstance().startTheme(MusicManager.MusicTheme.MENU);
        
        return new GameWindow.InputHandler() {
            @Override
            public void onEnter() {
                if (appState != ApplicationState.MENU) return;
                
                String selected = menuScreen.handleEnter();
                
                if (selected.equals("NOVO JOGO")) {
                    MusicManager.getInstance().playSoundConfirm();
                    startNewGame();
                } else if (selected.equals("CONTINUAR COM PASSWORD")) {
                    MusicManager.getInstance().playSoundConfirm();
                    showPasswordScreen();
                } else if (selected.equals("ÁUDIO: LIGADO")) {
                    audioEnabled = false;
                    MusicManager.getInstance().setAudioEnabled(false);
                    menuScreen.updateAudioOption(false);
                } else if (selected.equals("ÁUDIO: DESLIGADO")) {
                    audioEnabled = true;
                    MusicManager.getInstance().setAudioEnabled(true);
                    menuScreen.updateAudioOption(true);
                    MusicManager.getInstance().playSoundConfirm();
                } else if (selected.equals("SAIR")) {
                    System.exit(0);
                }
            }
            
            @Override
            public void onChoice(String key) {
                if (appState != ApplicationState.MENU) return;
                
                try {
                    String selected = menuScreen.handleNumberSelect(Integer.parseInt(key));
                    if (audioEnabled) {
                        MusicManager.getInstance().playSoundKeyPress();
                    }
                } catch (NumberFormatException e) {
                    // Ignorar
                }
            }
            
            @Override
            public void onArrowUp() {
                if (appState != ApplicationState.MENU) return;
                menuScreen.handleUpArrow();
                if (audioEnabled) {
                    MusicManager.getInstance().playSoundKeyPress();
                }
            }
            
            @Override
            public void onArrowDown() {
                if (appState != ApplicationState.MENU) return;
                menuScreen.handleDownArrow();
                if (audioEnabled) {
                    MusicManager.getInstance().playSoundKeyPress();
                }
            }
        };
    }
    
    private static void startNewGame() {
        // Parar música do menu
        MusicManager.getInstance().stopTheme();
        
        // Tocar som de confirmação e iniciar música de exploração
        if (audioEnabled) {
            MusicManager.getInstance().playSoundConfirm();
        }
        
        // Iniciar jogo
        appState = ApplicationState.GAME;
        gameEngine = new GameEngine(window, gameState);
        window.setEngine(gameEngine);
        window.setInputHandler(null); // Game engine vai lidar com input
        
        // Iniciar tema de exploração para o jogo
        if (audioEnabled) {
            MusicManager.getInstance().startTheme(MusicManager.MusicTheme.EXPLORATION);
        }
        
        gameEngine.showCurrentScene();
    }
    
    private static void showPasswordScreen() {
        // Tocar som de confirmação
        if (audioEnabled) {
            MusicManager.getInstance().playSoundConfirm();
        }
        
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
    
    public static boolean isAudioEnabled() {
        return audioEnabled;
    }
}

