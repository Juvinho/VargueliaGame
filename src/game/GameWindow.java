package game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameWindow extends JFrame implements KeyListener {
    private JTextPane textPane;
    private JScrollPane scrollPane;
    private GameEngine engine;
    private boolean isWaitingForInput;
    private InputHandler inputHandler;
    private StyledDocument doc;
    
    // Estilos de texto para cores pontuais
    private Style styleDefault;    // Branco/Cinza regular
    private Style styleError;      // Vermelho
    private Style styleCyan;       // Ciano (destaques/opções)
    private Style styleYellow;     // Amarelo
    private Style styleCursor;     // Cursor piscante
    
    // Esquema de cores - MS-DOS puro
    public enum ColorScheme {
        OS_TERMINAL(Color.BLACK, new Color(220, 220, 220));  // Preto + Branco/Cinza
        
        public Color bgColor;
        public Color textColor;
        
        ColorScheme(Color bg, Color text) {
            this.bgColor = bg;
            this.textColor = text;
        }
    }
    
    private ColorScheme currentScheme = ColorScheme.OS_TERMINAL;
    
    public interface InputHandler {
        void onEnter();
        void onChoice(String key);
        void onArrowUp();
        void onArrowDown();
    }
    
    public GameWindow() {
        setTitle("Varguelia - Ella é Demais [MS-DOS 1986]");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Configurar painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(currentScheme.bgColor);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Usar JTextPane para suportar cores
        textPane = new JTextPane();
        textPane.setBackground(currentScheme.bgColor);
        textPane.setForeground(currentScheme.textColor);
        textPane.setFont(new Font("Monospaced", Font.PLAIN, 18));
        textPane.setEditable(false);
        textPane.setCaretColor(currentScheme.textColor);
        textPane.setMargin(new Insets(10, 10, 10, 10));
        
        // Configurar StyledDocument e estilos
        doc = textPane.getStyledDocument();
        setupStyles();
        
        // Scroll
        scrollPane = new JScrollPane(textPane);
        scrollPane.setBackground(currentScheme.bgColor);
        scrollPane.setBorder(BorderFactory.createLineBorder(currentScheme.textColor, 1));
        scrollPane.getViewport().setBackground(currentScheme.bgColor);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel);
        
        // KeyListeners
        this.addKeyListener(this);
        mainPanel.addKeyListener(this);
        textPane.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        
        this.isWaitingForInput = false;
        
        // Garantir foco ao abrir
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                GameWindow.this.setFocusable(true);
                GameWindow.this.requestFocus();
            }
        });
    }
    
    /**
     * Configura estilos de texto para cores pontuais
     */
    private void setupStyles() {
        // Estilo padrão (branco/cinza)
        styleDefault = textPane.addStyle("default", null);
        StyleConstants.setForeground(styleDefault, currentScheme.textColor);
        StyleConstants.setFontFamily(styleDefault, "Monospaced");
        StyleConstants.setFontSize(styleDefault, 18);
        
        // Estilo erro (vermelho)
        styleError = textPane.addStyle("error", null);
        StyleConstants.setForeground(styleError, new Color(255, 64, 64));
        StyleConstants.setFontFamily(styleError, "Monospaced");
        StyleConstants.setFontSize(styleError, 18);
        
        // Estilo ciano (destaques, opções)
        styleCyan = textPane.addStyle("cyan", null);
        StyleConstants.setForeground(styleCyan, new Color(0, 255, 255));
        StyleConstants.setFontFamily(styleCyan, "Monospaced");
        StyleConstants.setFontSize(styleCyan, 18);
        
        // Estilo amarelo (mensagens especiais)
        styleYellow = textPane.addStyle("yellow", null);
        StyleConstants.setForeground(styleYellow, new Color(255, 255, 0));
        StyleConstants.setFontFamily(styleYellow, "Monospaced");
        StyleConstants.setFontSize(styleYellow, 18);
        
        // Estilo cursor (branco com fundo para simular cursor)
        styleCursor = textPane.addStyle("cursor", null);
        StyleConstants.setForeground(styleCursor, currentScheme.bgColor);
        StyleConstants.setBackground(styleCursor, currentScheme.textColor);
        StyleConstants.setFontFamily(styleCursor, "Monospaced");
        StyleConstants.setFontSize(styleCursor, 18);
    }
    
    public void setEngine(GameEngine engine) {
        this.engine = engine;
    }
    
    public void setInputHandler(InputHandler handler) {
        this.inputHandler = handler;
        // Garantir foco quando coloca novo handler
        this.requestFocus();
    }
    
    public void setColorScheme(ColorScheme scheme) {
        this.currentScheme = scheme;
        textPane.setBackground(scheme.bgColor);
        textPane.setForeground(scheme.textColor);
        textPane.setCaretColor(scheme.textColor);
        scrollPane.setBorder(BorderFactory.createLineBorder(scheme.textColor, 1));
        scrollPane.setBackground(scheme.bgColor);
        scrollPane.getViewport().setBackground(scheme.bgColor);
    }
    
    /**
     * Adiciona texto com estilo padrão (branco/cinza)
     */
    public void appendText(String text) {
        appendText(text, "default");
    }
    
    /**
     * Adiciona texto com estilo específico: "default", "error", "cyan", "yellow"
     */
    public void appendText(String text, String styleName) {
        try {
            Style style = textPane.getStyle(styleName);
            if (style == null) style = styleDefault;
            doc.insertString(doc.getLength(), text, style);
            textPane.setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
            System.err.println("Erro ao adicionar texto: " + e.getMessage());
        }
    }
    
    /**
     * Limpa todo o texto
     */
    public void clearText() {
        try {
            doc.remove(0, doc.getLength());
        } catch (BadLocationException e) {
            System.err.println("Erro ao limpar texto: " + e.getMessage());
        }
    }
    
    /**
     * Define texto, limpando o anterior
     */
    public void setText(String text) {
        setText(text, "default");
    }
    
    /**
     * Define texto com estilo
     */
    public void setText(String text, String styleName) {
        clearText();
        appendText(text, styleName);
    }
    
    public void setWaitingForInput(boolean waiting) {
        this.isWaitingForInput = waiting;
    }
    
    public boolean isWaitingForInput() {
        return this.isWaitingForInput;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        // Debug: mostrar que tecla foi pressionada
        // System.out.println("Key pressed: " + e.getKeyCode() + " (" + e.getKeyChar() + ")");
        
        // Sempre manter o foco na janela
        if (!this.hasFocus()) {
            this.requestFocus();
        }
        
        // Setas (funcionam com qualquer handler ativo)
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (inputHandler != null) {
                inputHandler.onArrowUp();
            }
            e.consume();
            return;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (inputHandler != null) {
                inputHandler.onArrowDown();
            }
            e.consume();
            return;
        }
        
        // ENTER ou SPACE
        if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (inputHandler != null) {
                inputHandler.onEnter();
            } else if (engine != null && !engine.isWaitingForChoice()) {
                engine.onAdvance();
            }
            e.consume();
            return;
        }
        
        // Números 1-9
        if (Character.isDigit(e.getKeyChar())) {
            if (inputHandler != null) {
                inputHandler.onChoice(String.valueOf(e.getKeyChar()));
            } else if (engine != null && engine.isWaitingForChoice()) {
                engine.onChoice(String.valueOf(e.getKeyChar()));
            }
            e.consume();
            return;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    /**
     * Efeito typewriter: texto aparece caractere por caractere
     */
    public void typeText(String text, int delayMs) throws InterruptedException {
        clearText();
        for (char c : text.toCharArray()) {
            appendText(String.valueOf(c), "default");
            Thread.sleep(delayMs);
        }
    }
    
    /**
     * Efeito de glitch: distorce texto temporariamente
     */
    public void glitchEffect(String originalText, int glitchDurationMs) throws InterruptedException {
        String glitchChars = "#@*&%$!?><[]{}()ÄÖÜäöü";
        clearText();
        
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < glitchDurationMs) {
            StringBuilder glitched = new StringBuilder();
            for (char c : originalText.toCharArray()) {
                if (Math.random() < 0.3) {
                    glitched.append(glitchChars.charAt((int) (Math.random() * glitchChars.length())));
                } else {
                    glitched.append(c);
                }
            }
            setText(glitched.toString(), "default");
            Thread.sleep(100);
        }
        
        // Restaurar texto original
        setText(originalText, "default");
    }
    
    /**
     * Animação de carregamento com pontos progressivos
     */
    public void showLoadingAnimation(String message, int durationMs) throws InterruptedException {
        clearText();
        int iterations = durationMs / 200;
        for (int i = 0; i < iterations; i++) {
            StringBuilder dots = new StringBuilder(message);
            for (int d = 0; d < (i % 4); d++) {
                dots.append(".");
            }
            setText(dots.toString(), "default");
            Thread.sleep(200);
        }
        setText(message + "  [OK]", "default");
    }
    
    /**
     * Mensagem de boot com pontos e [OK]
     */
    public void showBootLoadingMessage(String message) throws InterruptedException {
        for (int i = 0; i < 4; i++) {
            StringBuilder dots = new StringBuilder("  ");
            dots.append(message);
            for (int d = 0; d < i; d++) {
                dots.append(".");
            }
            setText(dots.toString(), "default");
            Thread.sleep(300);
        }
        clearText();
        appendText("  " + message + "  ", "default");
        appendText("[OK]\n", "cyan");
        Thread.sleep(200);
    }
    
    /**
     * Cursor piscante tipo DOS ao final do texto
     */
    public void appendCursor() throws InterruptedException {
        appendText("_ ", "cursor");
        Thread.sleep(300);
        // Remover cursor (simular piscar)
        try {
            int len = doc.getLength();
            if (len >= 2) {
                doc.remove(len - 2, 2);
            }
        } catch (BadLocationException e) {
            // Ignorar
        }
    }
    
    // ==================== BOOT MENU ESTILIZADO ====================
    
    /**
     * Configura estilos adicionais para tela de boot (vermelho, verde água)
     */
    private void setupBootMenuStyles() {
        Style styleRed = doc.addStyle("red", null);
        StyleConstants.setForeground(styleRed, new Color(255, 100, 100)); // Vermelho claro
        
        Style styleAqua = doc.addStyle("aqua", null);
        StyleConstants.setForeground(styleAqua, new Color(0, 255, 200)); // Verde água/ciano
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
     * Centraliza texto em uma coluna de largura fixa
     */
    private String center(String text, int cols) {
        int left = (cols - text.length()) / 2;
        if (left < 0) left = 0;
        return repeat(" ", left) + text;
    }
    
    /**
     * Imprime linha em vermelho
     */
    private void printlnRed(String text) {
        try {
            doc.insertString(doc.getLength(), text + "\n", doc.getStyle("red"));
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Imprime linha em verde água
     */
    private void printlnAqua(String text) {
        try {
            doc.insertString(doc.getLength(), text + "\n", doc.getStyle("aqua"));
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Imprime linha normal (branca)
     */
    private void println(String text) {
        appendText(text + "\n", "default");
    }
    
    private String linhaPisca = "";
    private boolean piscaVisivel = true;
    private Timer timerPisca;
    private static final int COLS_BOOT = 60;
    
    /**
     * Mostra a tela de boot/menu principal estilizada com texto piscante
     */
    public void mostrarBootMenu() {
        clearText();
        setupBootMenuStyles();
        
        String border = "|" + repeat("-", COLS_BOOT - 2) + "|";
        
        // Desenhar moldura e textos em vermelho
        printlnRed(border);
        printlnRed(center("V A R G U E N . O S", COLS_BOOT));
        printlnRed(center("FUNDAÇÃO VARGUÉLIA", COLS_BOOT));
        printlnRed(center("ELLA É DEMAIS [v1.0]", COLS_BOOT));
        printlnRed(border);
        println("");
        
        // Textos informativos em vermelho
        printlnRed(center("Press ENTER to access the memory banks of Varguén...", COLS_BOOT));
        printlnRed(center("(or DIE trying)", COLS_BOOT));
        println("");
        
        // Preparar linha que pisca
        linhaPisca = center("Aperte ENTER para iniciar", COLS_BOOT);
        
        // Iniciar piscar
        iniciarTimerPisca();
        setWaitingForInput(true);
    }
    
    /**
     * Desenha a linha piscante (ou apaga se invisível)
     */
    private void desenharLinhaPisca(boolean visivel) {
        try {
            if (visivel) {
                doc.insertString(doc.getLength(), linhaPisca + "\n", doc.getStyle("aqua"));
            } else {
                // Escreve espaços em branco para "apagar"
                doc.insertString(doc.getLength(), repeat(" ", linhaPisca.length()) + "\n", 
                                doc.getStyle("default"));
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Inicia o timer que controla o piscar
     */
    private void iniciarTimerPisca() {
        if (timerPisca != null && timerPisca.isRunning()) {
            timerPisca.stop();
        }
        
        timerPisca = new Timer(500, e -> {
            try {
                // Limpar apenas a última linha (não redesenhar tudo)
                int len = doc.getLength();
                int ultimaQuebraLinha = doc.getText(0, len).lastIndexOf("\n");
                
                if (ultimaQuebraLinha >= 0) {
                    doc.remove(ultimaQuebraLinha + 1, len - ultimaQuebraLinha - 1);
                }
                
                // Redesenhar a linha com estado alternado
                piscaVisivel = !piscaVisivel;
                desenharLinhaPisca(piscaVisivel);
                
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        });
        timerPisca.start();
    }
    
    /**
     * Para o timer de piscar (quando sair da tela)
     */
    public void pararTimerPisca() {
        if (timerPisca != null && timerPisca.isRunning()) {
            timerPisca.stop();
        }
    }
}
