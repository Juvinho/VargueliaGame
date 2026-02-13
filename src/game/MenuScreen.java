package game;

import javax.swing.Timer;

/**
 * Menu Principal Avançado - Aparência de mini "desktop" textual (v1.0)
 * 
 * Layout em GRID 3x2:
 *   [ Novo Jogo ]          Continuar (Password)         Arquivos
 *       Opções                 Créditos                  Sair
 * 
 * Navegação: setas ← → ↑ ↓ | Confirmação: ENTER
 * Destaque: opção selecionada em cyan com [ ... ]
 * Dica contextual que muda conforme seleção
 */
public class MenuScreen {
    private GameWindow window;
    private int selectedIndex = 0;
    private static final int COLS = 70;
    private static final int GRID_COLS = 3;
    private static final int GRID_ROWS = 2;
    
    // Opções em ordem: [0][1][2] (linha 1) / [3][4][5] (linha 2)
    private String[] options = {
        "Novo Jogo",
        "Continuar (Password)",
        "Arquivos",
        "Opções",
        "Créditos",
        "Sair"
    };
    
    // Dicas contextuais para cada opção
    private String[] dicas = {
        "Use Novo Jogo para acessar o LOG original de Niuwë.",
        "Recupere um jogo salvo com PASSWORD.",
        "Veja registros secretos e arquivos da Varguën.",
        "Ajuste som, glitches e velocidade do texto.",
        "Conheça os autores dessa obra-prima de 1983.",
        "Sair do sistema Varguén."
    };
    
    private Timer piscaTimer;
    private boolean piscaVisivel = true;
    
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
    
    public void display() {
        window.clearText();
        
        // Parar timer anterior se existir
        if (piscaTimer != null && piscaTimer.isRunning()) {
            piscaTimer.stop();
        }
        piscaVisivel = true;
        
        // Cabeçalho
        window.appendText("\n\n", "default");
        String header = center("V A R G U E N . O S");
        window.appendText(header + "\n", "yellow");
        
        String subtitle = center("FUNDAÇÃO VARGUÉLIA [v1.0]");
        window.appendText(subtitle + "\n", "error"); // Vermelho
        
        window.appendText("\n", "default");
        
        // Linha separadora
        String separator = repeat("-", COLS);
        window.appendText(separator + "\n\n", "error");
        
        // Linha 1 do grid (índices 0, 1, 2)
        drawGridRow(0);
        window.appendText("\n\n", "default");
        
        // Linha 2 do grid (índices 3, 4, 5)
        drawGridRow(1);
        window.appendText("\n\n", "default");
        
        // Outra linha separadora
        window.appendText(separator + "\n\n", "error");
        
        // Dica contextual
        String dica = dicas[selectedIndex];
        window.appendText(center("Dica: " + dica) + "\n", "cyan");
        
        window.appendText("\n", "default");
        
        // Instruções de navegação
        String nav = "Use ← → ↑ ↓ ou números 1-6 | ENTER para confirmar";
        window.appendText(center(nav) + "\n", "cyan");
        
        window.setWaitingForInput(true);
        
        // Iniciar timer de piscar na opção selecionada
        startPiscaTimer();
    }
    
    /**
     * Desenha uma linha do grid (row: 0 ou 1)
     */
    private void drawGridRow(int row) {
        StringBuilder line = new StringBuilder();
        int colWidth = COLS / GRID_COLS;
        int startIdx = row * GRID_COLS;
        
        for (int col = 0; col < GRID_COLS; col++) {
            int idx = startIdx + col;
            String option = options[idx];
            
            if (idx == selectedIndex && piscaVisivel) {
                // Mostrar opção selecionada com destaque
                option = "[ " + option + " ]";
                // Padding centralizado para a coluna
                int padding = (colWidth - option.length()) / 2;
                if (padding < 0) padding = 0;
                line.append(repeat(" ", padding)).append(option);
                
                // Preencher o resto da coluna
                int remaining = colWidth - (padding + option.length());
                line.append(repeat(" ", Math.max(0, remaining)));
            } else {
                // Opção normal
                int padding = (colWidth - option.length()) / 2;
                if (padding < 0) padding = 0;
                line.append(repeat(" ", padding)).append(option);
                
                // Preencher o resto da coluna
                int remaining = colWidth - (padding + option.length());
                line.append(repeat(" ", Math.max(0, remaining)));
            }
        }
        
        // Aplicar estilo à linha baseado na seleção
        if (selectedIndex >= startIdx && selectedIndex < startIdx + GRID_COLS) {
            // Tem alguma opção selecionada nessa linha
            if (piscaVisivel) {
                window.appendText(line.toString() + "\n", "cyan");
            } else {
                // Piscada invisível: usar cor neutra
                window.appendText(line.toString() + "\n", "default");
            }
        } else {
            // Nenhuma seleção nessa linha: cor avermelhada
            window.appendText(line.toString() + "\n", "error");
        }
    }
    
    /**
     * Inicia timer de piscar (500ms) na opção selecionada
     */
    private void startPiscaTimer() {
        piscaTimer = new Timer(500, e -> {
            piscaVisivel = !piscaVisivel;
        });
        piscaTimer.start();
    }
    
    /**
     * Para o timer de piscar
     */
    private void stopPiscaTimer() {
        if (piscaTimer != null && piscaTimer.isRunning()) {
            piscaTimer.stop();
        }
    }
    
    /**
     * Navega para cima (setas ↑)
     */
    public void handleUpArrow() {
        stopPiscaTimer();
        // Seta pra cima = subir uma linha (diminuir índice em 3)
        selectedIndex = Math.max(0, selectedIndex - GRID_COLS);
        display();
    }
    
    /**
     * Navega para baixo (setas ↓)
     */
    public void handleDownArrow() {
        stopPiscaTimer();
        // Seta pra baixo = descer uma linha (aumentar índice em 3)
        selectedIndex = Math.min(options.length - 1, selectedIndex + GRID_COLS);
        display();
    }
    
    /**
     * Navega para esquerda (setas ←)
     */
    public void handleLeftArrow() {
        stopPiscaTimer();
        if (selectedIndex % GRID_COLS > 0) {
            selectedIndex--;
        }
        display();
    }
    
    /**
     * Navega para direita (setas →)
     */
    public void handleRightArrow() {
        stopPiscaTimer();
        if (selectedIndex % GRID_COLS < GRID_COLS - 1) {
            selectedIndex++;
        }
        display();
    }
    
    /**
     * Seleciona por número (1-6)
     */
    public String handleNumberSelect(int number) {
        if (number >= 1 && number <= options.length) {
            selectedIndex = number - 1;
            display();
            return options[selectedIndex];
        }
        return null;
    }
    
    /**
     * Confirma seleção atual (ENTER)
     */
    public String handleEnter() {
        stopPiscaTimer();
        return options[selectedIndex];
    }
}
