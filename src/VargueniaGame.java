import game.*;
import core.GameState;
import managers.MusicManager;

public class VargueniaGame {
    private static GameWindow window;
    private static GameState gameState;
    private static GameEngine gameEngine;
    private static MenuScreen menuScreen;
    private static PasswordScreen passwordScreen;
    private static LogsScreen logsScreen;
    private static OptionsScreen optionsScreen;
    private static CreditsScreen creditsScreen;
    
    private static UiState currentUiState = UiState.TITLE_SCREEN;
    private static boolean audioEnabled = true;
    
    public static void main(String[] args) {
        // Criar janela
        window = new GameWindow();
        window.setVisible(true);
        
        try {
            // Mostrar tela de boot estilo MS-DOS 1986
            showBootScreenMSDOS();
            
            // Ir para menu principal (menu avançado com grid)
            goToMainMenu();
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    
    private static void showBootScreenMSDOS() throws InterruptedException {
        window.setColorScheme(GameWindow.ColorScheme.OS_TERMINAL);
        window.clearText();
        currentUiState = UiState.TITLE_SCREEN;
        
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
    
    /**
     * Vai para o menu principal
     */
    private static void goToMainMenu() {
        currentUiState = UiState.MAIN_MENU;
        gameState = new GameState();
        menuScreen = new MenuScreen(window);
        passwordScreen = new PasswordScreen(window);
        logsScreen = new LogsScreen(window);
        optionsScreen = new OptionsScreen(window);
        creditsScreen = new CreditsScreen(window);
        
        window.setColorScheme(GameWindow.ColorScheme.OS_TERMINAL);
        menuScreen.display();
        window.setInputHandler(createMainMenuHandler());
        
        // Iniciar música de menu
        MusicManager.getInstance().startTheme(MusicManager.MusicTheme.MENU);
    }
    
    /**
     * Handler para o menu principal (com suporte a setas LEFT/RIGHT)
     */
    private static GameWindow.InputHandler createMainMenuHandler() {
        return new GameWindow.InputHandler() {
            @Override
            public void onEnter() {
                if (currentUiState != UiState.MAIN_MENU) return;
                
                String selected = menuScreen.handleEnter();
                handleMainMenuSelection(selected);
            }
            
            @Override
            public void onChoice(String key) {
                if (currentUiState != UiState.MAIN_MENU) return;
                
                try {
                    String selected = menuScreen.handleNumberSelect(Integer.parseInt(key));
                    if (audioEnabled && selected != null) {
                        MusicManager.getInstance().playSoundKeyPress();
                    }
                } catch (NumberFormatException e) {
                    // Ignorar
                }
            }
            
            @Override
            public void onArrowUp() {
                if (currentUiState != UiState.MAIN_MENU) return;
                menuScreen.handleUpArrow();
                if (audioEnabled) {
                    MusicManager.getInstance().playSoundKeyPress();
                }
            }
            
            @Override
            public void onArrowDown() {
                if (currentUiState != UiState.MAIN_MENU) return;
                menuScreen.handleDownArrow();
                if (audioEnabled) {
                    MusicManager.getInstance().playSoundKeyPress();
                }
            }
            
            // Métodos adicionais para setas laterais (via reflection em GameWindow)
            public void onArrowLeft() {
                if (currentUiState != UiState.MAIN_MENU) return;
                menuScreen.handleLeftArrow();
                if (audioEnabled) {
                    MusicManager.getInstance().playSoundKeyPress();
                }
            }
            
            public void onArrowRight() {
                if (currentUiState != UiState.MAIN_MENU) return;
                menuScreen.handleRightArrow();
                if (audioEnabled) {
                    MusicManager.getInstance().playSoundKeyPress();
                }
            }
        };
    }
    
    /**
     * Processa seleção do menu principal
     */
    private static void handleMainMenuSelection(String selected) {
        if (audioEnabled) {
            MusicManager.getInstance().playSoundConfirm();
        }
        
        switch (selected) {
            case "Novo Jogo":
                startNewGame();
                break;
            case "Continuar (Password)":
                showPasswordScreen();
                break;
            case "Arquivos":
                showLogsScreen();
                break;
            case "Opções":
                showOptionsScreen();
                break;
            case "Créditos":
                showCreditsScreen();
                break;
            case "Sair":
                System.exit(0);
                break;
        }
    }
    
    /**
     * Inicia um novo jogo
     */
    private static void startNewGame() {
        MusicManager.getInstance().stopTheme();
        
        currentUiState = UiState.GAME;
        gameEngine = new GameEngine(window, gameState);
        window.setEngine(gameEngine);
        
        if (audioEnabled) {
            MusicManager.getInstance().startTheme(MusicManager.MusicTheme.EXPLORATION);
        }
        
        gameEngine.showCurrentScene();
    }
    
    /**
     * Tela de password
     */
    private static void showPasswordScreen() {
        currentUiState = UiState.MENU_PASSWORD;
        passwordScreen.clear();
        passwordScreen.display();
        
        window.setInputHandler(new GameWindow.InputHandler() {
            @Override
            public void onEnter() {
                if (currentUiState != UiState.MENU_PASSWORD) return;
                
                String pwd = passwordScreen.getPassword();
                if (passwordScreen.validateAndDecode(pwd)) {
                    try {
                        Thread.sleep(1000);
                        // TODO: Restaurar estado do password (futura implementação)
                        goToMainMenu();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Thread.sleep(800);
                        passwordScreen.clear();
                        passwordScreen.display();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            
            @Override
            public void onChoice(String key) {
                if (currentUiState != UiState.MENU_PASSWORD) return;
                
                if (key.length() == 1 && Character.isLetterOrDigit(key.charAt(0))) {
                    passwordScreen.addCharacter(key.charAt(0));
                    if (audioEnabled) {
                        MusicManager.getInstance().playSoundKeyPress();
                    }
                }
            }
            
            @Override
            public void onArrowUp() {}
            
            @Override
            public void onArrowDown() {}
        });
    }
    
    /**
     * Tela de logs/arquivos
     */
    private static void showLogsScreen() {
        currentUiState = UiState.MENU_LOGS;
        logsScreen.display();
        
        window.setInputHandler(new GameWindow.InputHandler() {
            private boolean readingLog = false;
            
            @Override
            public void onEnter() {
                if (currentUiState != UiState.MENU_LOGS) return;
                
                if (!readingLog) {
                    logsScreen.readSelectedLog();
                    readingLog = true;
                } else {
                    logsScreen.display();
                    readingLog = false;
                }
                
                if (audioEnabled) {
                    MusicManager.getInstance().playSoundConfirm();
                }
            }
            
            @Override
            public void onChoice(String key) {
                if (currentUiState != UiState.MENU_LOGS || readingLog) return;
                
                try {
                    int num = Integer.parseInt(key);
                    if (num >= 1 && num <= 4) {
                        if (audioEnabled) {
                            MusicManager.getInstance().playSoundKeyPress();
                        }
                    }
                } catch (NumberFormatException e) {
                    // Ignorar
                }
            }
            
            @Override
            public void onArrowUp() {
                if (currentUiState != UiState.MENU_LOGS || readingLog) return;
                logsScreen.handleUpArrow();
                if (audioEnabled) {
                    MusicManager.getInstance().playSoundKeyPress();
                }
            }
            
            @Override
            public void onArrowDown() {
                if (currentUiState != UiState.MENU_LOGS || readingLog) return;
                logsScreen.handleDownArrow();
                if (audioEnabled) {
                    MusicManager.getInstance().playSoundKeyPress();
                }
            }
        });
    }
    
    /**
     * Tela de opções
     */
    private static void showOptionsScreen() {
        currentUiState = UiState.MENU_OPTIONS;
        optionsScreen.display();
        
        window.setInputHandler(new GameWindow.InputHandler() {
            @Override
            public void onEnter() {
                if (currentUiState != UiState.MENU_OPTIONS) return;
                if (audioEnabled) {
                    MusicManager.getInstance().playSoundConfirm();
                }
                goToMainMenu();
            }
            
            @Override
            public void onChoice(String key) {}
            
            @Override
            public void onArrowUp() {
                if (currentUiState != UiState.MENU_OPTIONS) return;
                optionsScreen.handleUpArrow();
                if (audioEnabled) {
                    MusicManager.getInstance().playSoundKeyPress();
                }
            }
            
            @Override
            public void onArrowDown() {
                if (currentUiState != UiState.MENU_OPTIONS) return;
                optionsScreen.handleDownArrow();
                if (audioEnabled) {
                    MusicManager.getInstance().playSoundKeyPress();
                }
            }
        });
        
        // Também permitir esquerda/direita para ajustar valores
        // TODO: Expandir InputHandler para suportar onLeftArrow() / onRightArrow()
    }
    
    /**
     * Tela de créditos
     */
    private static void showCreditsScreen() {
        currentUiState = UiState.MENU_CREDITS;
        creditsScreen.display();
        
        window.setInputHandler(new GameWindow.InputHandler() {
            @Override
            public void onEnter() {
                if (currentUiState != UiState.MENU_CREDITS) return;
                if (audioEnabled) {
                    MusicManager.getInstance().playSoundConfirm();
                }
                goToMainMenu();
            }
            
            @Override
            public void onChoice(String key) {
                if (currentUiState != UiState.MENU_CREDITS) return;
                if (audioEnabled) {
                    MusicManager.getInstance().playSoundConfirm();
                }
                goToMainMenu();
            }
            
            @Override
            public void onArrowUp() {
                if (currentUiState != UiState.MENU_CREDITS) return;
                creditsScreen.scrollUp();
            }
            
            @Override
            public void onArrowDown() {
                if (currentUiState != UiState.MENU_CREDITS) return;
                creditsScreen.scrollDown();
            }
        });
    }
    
    public static UiState getCurrentUiState() {
        return currentUiState;
    }
    
    public static boolean isAudioEnabled() {
        return audioEnabled;
    }
}

