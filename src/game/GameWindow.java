package game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameWindow extends JFrame implements KeyListener {
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private GameEngine engine;
    private boolean isWaitingForInput;
    private InputHandler inputHandler;
    
    public interface InputHandler {
        void onEnter();
        void onChoice(String key);
        void onArrowUp();
        void onArrowDown();
    }
    
    public GameWindow() {
        setTitle("Fundação Varguélia - Terminal 1983");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Configurar painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Configurar área de texto (terminal)
        textArea = new JTextArea();
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(new Color(0, 255, 70)); // Verde fosforescente
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setCaretColor(new Color(0, 255, 70));
        textArea.setMargin(new Insets(10, 10, 10, 10));
        
        // Scroll
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 70), 2));
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel);
        
        // Listeners - Adicionar ao JFrame, não apenas ao painel
        this.addKeyListener(this);
        mainPanel.addKeyListener(this);
        textArea.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        
        this.isWaitingForInput = false;
        
        // Garantir foco ao abrir a janela
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                GameWindow.this.setFocusable(true);
                GameWindow.this.requestFocus();
            }
        });
    }
    
    public void setEngine(GameEngine engine) {
        this.engine = engine;
    }
    
    public void setInputHandler(InputHandler handler) {
        this.inputHandler = handler;
        // Garantir foco quando coloca novo handler
        this.requestFocus();
    }
    
    public void appendText(String text) {
        textArea.append(text);
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    
    public void clearText() {
        textArea.setText("");
    }
    
    public void setText(String text) {
        textArea.setText(text);
        textArea.setCaretPosition(0);
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
    
    // Efeito de escrita gradual (digitação)
    public void typeText(String text, int delayMs) throws InterruptedException {
        clearText();
        for (char c : text.toCharArray()) {
            appendText(String.valueOf(c));
            Thread.sleep(delayMs);
        }
    }
    
    // Efeito de glitch (corrompe texto temporariamente)
    public void glitchEffect(String originalText, int glitchDurationMs) throws InterruptedException {
        String glitchChars = "#@*&%$!?><[]{}()";
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
            setText(glitched.toString());
            Thread.sleep(100);
        }
        
        // Mostrar texto final
        setText(originalText);
    }
}
