package game;

/**
 * Enum para gerenciar estados da interface do usuário
 */
public enum UiState {
    TITLE_SCREEN,      // Tela de título com moldura
    MAIN_MENU,         // Menu principal com 6 opções
    MENU_PASSWORD,     // Tela de decodificação de password
    MENU_LOGS,         // Submenu de arquivos/logs
    MENU_OPTIONS,      // Configurações (som, velocidade, etc.)
    MENU_CREDITS,      // Créditos
    GAME              // Gameplay
}
