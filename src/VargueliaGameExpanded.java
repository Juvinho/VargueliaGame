import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Varguelia - Ella é Demais [EXPANDED v2.1 FIXED]
 * Corrigido: Memory leak de Timers resolvido
 * Personagens: Niuwë, Taila, Selenna, Jorgenssen, Roger, Dasko, Ella, Sepharoth
 */
public class VargueliaGameExpanded extends JFrame {
    private CardLayout cardLayout;
    private JPanel root;
    private SoundManager soundManager;
    private GameState gameState = new GameState();
    private List<javax.swing.Timer> activeTimers = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VargueliaGameExpanded::new);
    }

    public VargueliaGameExpanded() {
        setTitle("Varguelia - Ella é Demais [EXPANDED v2.1]");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        soundManager = new SoundManager();
        cardLayout = new CardLayout();
        root = new JPanel(cardLayout);
        
        setupScenes();
        
        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        gotoScene("title");
    }

    private void setupScenes() {
        root.add(new TitleScreen(() -> gotoScene("narration1")), "title");
        
        root.add(new NarratorScene(
            "Alerta de toque de recolher em todo o território da Varguën! " +
            "Atenção, repito, toque de recolher em toda a área da Varguën!",
            new Color(200, 200, 200),
            () -> gotoScene("scene2")
        ), "narration1");

        root.add(new NarratorScene(
            "A voz grave e rígida ecoava pelos alto-falantes. O pânico instalou-se " +
            "instantaneamente. Os alunos correram desesperados.",
            new Color(150, 100, 100),
            () -> gotoScene("scene3")
        ), "scene2");

        root.add(new NarratorScene(
            "Dasko observava o horizonte além da muralha. Ela estava vindo.",
            new Color(180, 150, 200),
            () -> gotoScene("scene4")
        ), "scene3");

        root.add(new DialogueScene(
            Actor.NIUWE,
            "Olha só que cara esbelto… " +
            "Espero que nada de mais interrompa o meu sossego.",
            () -> gotoScene("scene5")
        ), "scene4");

        root.add(new DialogueScene(
            Actor.NIUWE,
            "Ué? O céu… não está como antes. " +
            "Agora tá num tom rubro, como sangue.",
            () -> gotoScene("scene6")
        ), "scene5");

        root.add(new NarratorScene(
            "Jorgenssen corria pelo pátio, com Roger logo atrás.",
            new Color(150, 150, 150),
            () -> gotoScene("scene7")
        ), "scene6");

        root.add(new DialogueScene(
            Actor.JORGENSSEN,
            "Temos que dar aos alunos um motivo maior para correr. " +
            "Roger, consegue rastrear a posição dela?",
            () -> gotoScene("scene8")
        ), "scene7");

        root.add(new DialogueScene(
            Actor.ROGER,
            "Estou entrando no sistema... " +
            "Ela está exatamente 52 minutos de distância.",
            () -> gotoScene("scene9")
        ), "scene8");

        root.add(new DialogueScene(
            Actor.JORGENSSEN,
            "52 minutos? Niuwë precisa saber disso agora.",
            () -> gotoScene("choice1")
        ), "scene9");

        LinkedHashMap<String, String> choice1 = new LinkedHashMap<>();
        choice1.put("Chocar - Merda… três cabeças?!", "react1");
        choice1.put("Questionar - Você tem certeza?", "react2");
        choice1.put("Pragmático - Quanto tempo temos?", "react3");

        root.add(new ChoiceScene(
            Actor.NIUWE,
            "Como Niuwë reage à notícia de Sepharoth?",
            choice1,
            (choice) -> {
                gameState.niuweReaction = choice;
                handleChoice(choice);
            }
        ), "choice1");

        root.add(new DialogueScene(
            Actor.NIUWE,
            "Merda… ELE TEM TRÊS CABEÇAS?!",
            () -> gotoScene("scene10")
        ), "react1");

        root.add(new DialogueScene(
            Actor.NIUWE,
            "Você tem certeza? Ou vou estar correndo para nada?",
            () -> gotoScene("scene10")
        ), "react2");

        root.add(new DialogueScene(
            Actor.NIUWE,
            "Quanto tempo temos? E qual é o tamanho?",
            () -> gotoScene("scene10")
        ), "react3");

        root.add(new NarratorScene(
            "Niuwë correu pelos corredores. Pegou suas armas.",
            new Color(200, 100, 100),
            () -> gotoScene("choice2")
        ), "scene10");

        LinkedHashMap<String, String> choice2 = new LinkedHashMap<>();
        choice2.put("Confrontar diretamente", "end1");
        choice2.put("Estratégico - Analisar", "end1");
        choice2.put("Fugir e regroupar", "end1");

        root.add(new ChoiceScene(
            Actor.JORGENSSEN,
            "Qual é o plano? Como operamos?",
            choice2,
            (choice) -> {
                gameState.strategy = choice;
                handleChoice(choice);
            }
        ), "choice2");

        root.add(new DialogueScene(
            Actor.DASKO,
            "Iniciando Protocolo Genesis. " +
            "Código de acesso requerido, Niuwë.",
            () -> gotoScene("password1")
        ), "end1");

        root.add(new InputScene(
            Actor.DASKO,
            "Digite o código de acesso para continuar.",
            "BRIDGE",
            () -> gotoScene("ending"),
            () -> gotoScene("password1")
        ), "password1");

        root.add(new DialogueScene(
            Actor.ELLA,
            "Bem-vindo à ponte de comando. " +
            "A verdade sobre Varguelia está aqui.",
            () -> {
                JOptionPane.showMessageDialog(this, 
                    "FIM DO PRÓLOGO\n\n" +
                    "Reação: " + gameState.niuweReaction + "\n" +
                    "Estratégia: " + gameState.strategy);
                System.exit(0);
            }
        ), "ending");
    }

    private void handleChoice(String nextScene) {
        gotoScene(nextScene);
    }

    private void gotoScene(String id) {
        // Parar todos os timers antes de trocar de cena
        stopAllActiveTimers();
        
        cardLayout.show(root, id);
        for (Component c : root.getComponents()) {
            if (c.isVisible()) {
                c.requestFocusInWindow();
                break;
            }
        }
    }
    
    void registerTimer(javax.swing.Timer t) {
        if (t != null) activeTimers.add(t);
    }
    
    void stopAllActiveTimers() {
        for (javax.swing.Timer t : activeTimers) {
            if (t != null && t.isRunning()) {
                t.stop();
            }
        }
        activeTimers.clear();
    }

    /* ====== GAME STATE ====== */
    class GameState {
        String niuweReaction = "";
        String strategy = "";
    }

    /* ====== SOUND MANAGER ====== */
    class SoundManager {
        void playCharacterSound() {
            SwingUtilities.invokeLater(() -> {
                try {
                    Toolkit.getDefaultToolkit().beep();
                } catch (Exception e) {
                    // Silent
                }
            });
        }
    }

    /* ====== NARRATOR SCENE ====== */
    class NarratorScene extends JPanel {
        private Typewriter.Writer tw;

        NarratorScene(String text, Color color, Runnable onFinish) {
            setPreferredSize(new Dimension(1100, 700));
            setBackground(Color.BLACK);
            setLayout(new BorderLayout());

            tw = Typewriter.create(null, text, color);
            tw.setBorder(new EmptyBorder(100, 80, 100, 80));
            add(tw, BorderLayout.CENTER);

            JLabel label = new JLabel("[ NARRADOR ]");
            label.setOpaque(false);
            label.setFont(new Font("Monospaced", Font.ITALIC, 18));
            label.setForeground(color);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBorder(new EmptyBorder(20, 0, 0, 0));
            add(label, BorderLayout.NORTH);

            setFocusable(true);
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER && tw.isFinished()) {
                        onFinish.run();
                    }
                }
            });
        }
    }

    /* ====== ATORES ====== */
    enum Actor {
        NIUWE(new Font("Monospaced", Font.PLAIN, 20), new Color(255, 200, 0)),      
        TAILA(new Font("Monospaced", Font.PLAIN, 20), new Color(0, 255, 200)),      
        SELENNA(new Font("Monospaced", Font.PLAIN, 20), new Color(255, 100, 255)),  
        JORGENSSEN(new Font("Monospaced", Font.PLAIN, 20), new Color(255, 100, 100)),
        ROGER(new Font("Monospaced", Font.PLAIN, 20), new Color(100, 200, 255)),     
        DASKO(new Font("Monospaced", Font.PLAIN, 20), new Color(200, 200, 200)),     
        ELLA(new Font("Monospaced", Font.PLAIN, 20), new Color(200, 0, 200)),        
        SEPHAROTH(new Font("Monospaced", Font.PLAIN, 20), new Color(200, 0, 0));     

        final Font font;
        final Color color;

        Actor(Font f, Color c) {
            font = f;
            color = c;
        }

        String label() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }

    /* ====== TITLE SCREEN ====== */
    class TitleScreen extends JPanel {
        private List<Point2D> stars = new ArrayList<>();
        private boolean blink = true;

        TitleScreen(Runnable onStart) {
            setPreferredSize(new Dimension(1100, 700));
            setBackground(Color.BLACK);

            Random r = new Random();
            for (int i = 0; i < 150; i++) {
                stars.add(new Point2D.Double(r.nextInt(1100), r.nextInt(700)));
            }

            javax.swing.Timer t = new javax.swing.Timer(500, e -> {
                blink = !blink;
                repaint();
            });
            t.start();
            registerTimer(t);

            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    onStart.run();
                }
            });

            setFocusable(true);
            addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        onStart.run();
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);

            stars.forEach(p -> g2.fillRect((int) p.getX(), (int) p.getY(), 2, 2));

            g2.setFont(new Font("Serif", Font.BOLD, 76));
            drawCentered(g2, "VARGUELIA", 120);

            g2.setFont(new Font("Serif", Font.ITALIC, 36));
            g2.setColor(new Color(255, 100, 100));
            drawCentered(g2, "Ella é Demais", 180);

            g2.setFont(new Font("Monospaced", Font.PLAIN, 18));
            g2.setColor(new Color(150, 150, 150));
            drawCentered(g2, "Fundação Varguélia [v2.1 FIXED]", 240);

            if (blink) {
                g2.setFont(new Font("Monospaced", Font.BOLD, 22));
                g2.setColor(new Color(0, 255, 200));
                drawCentered(g2, "Press ENTER to begin", 620);
            }

            g2.setFont(new Font("Serif", Font.PLAIN, 14));
            g2.setColor(new Color(255, 100, 100));
            drawCentered(g2, "⚠ Contém violência e linguagem forte", 670);
        }

        private void drawCentered(Graphics2D g2, String txt, int y) {
            int w = g2.getFontMetrics().stringWidth(txt);
            g2.drawString(txt, (getWidth() - w) / 2, y);
        }
    }

    /* ====== DIALOGUE SCENE ====== */
    class DialogueScene extends JPanel {
        private Typewriter.Writer tw;

        DialogueScene(Actor who, String text, Runnable onFinish) {
            setPreferredSize(new Dimension(1100, 700));
            setBackground(Color.BLACK);
            setLayout(new BorderLayout());

            tw = Typewriter.create(who, text);
            tw.setBorder(new EmptyBorder(50, 80, 50, 80));
            add(tw, BorderLayout.CENTER);

            JLabel actorName = new JLabel(who.label(), SwingConstants.CENTER);
            actorName.setOpaque(false);
            actorName.setFont(who.font.deriveFont(Font.BOLD, 32f));
            actorName.setForeground(who.color);
            actorName.setBorder(new EmptyBorder(20, 0, 0, 0));
            add(actorName, BorderLayout.NORTH);

            setFocusable(true);
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER && tw.isFinished()) {
                        soundManager.playCharacterSound();
                        onFinish.run();
                    }
                }
            });
        }
    }

    /* ====== CHOICE SCENE ====== */
    class ChoiceScene extends JPanel {
        private OptionPanel menu;
        private Map<Integer, String> destMap;

        ChoiceScene(Actor who, String prompt, LinkedHashMap<String, String> options,
                   java.util.function.Consumer<String> onSelect) {
            setPreferredSize(new Dimension(1100, 700));
            setBackground(Color.BLACK);
            setLayout(new BorderLayout());

            Typewriter.Writer tw = Typewriter.create(who, prompt);
            tw.setBorder(new EmptyBorder(40, 80, 200, 80));
            add(tw, BorderLayout.NORTH);

            List<String> labels = new ArrayList<>(options.keySet());
            destMap = new LinkedHashMap<>();
            for (int i = 0; i < labels.size(); i++) {
                destMap.put(i, options.get(labels.get(i)));
            }

            menu = new OptionPanel(who, labels);
            add(menu, BorderLayout.CENTER);

            javax.swing.Timer t = new javax.swing.Timer(80, e -> {
                if (tw.isFinished()) {
                    ((javax.swing.Timer) e.getSource()).stop();
                    menu.setActive(true);
                    menu.requestFocusInWindow();
                }
            });
            t.start();
            registerTimer(t);

            menu.setOnSelect((idx) -> {
                soundManager.playCharacterSound();
                onSelect.accept(destMap.get(idx));
            });
        }

        private class OptionPanel extends JPanel {
            private Actor actor;
            private List<String> lines;
            private boolean active = false;
            private int sel = 0;
            private Runnable onSelect;

            OptionPanel(Actor who, List<String> lines) {
                this.actor = who;
                this.lines = lines;
                setOpaque(false);
                setFont(who.font.deriveFont(Font.BOLD, 20f));
                setForeground(who.color);
                setFocusable(true);
                setPreferredSize(new Dimension(1100, 100 + lines.size() * 60));

                InputMap im = getInputMap(WHEN_FOCUSED);
                ActionMap am = getActionMap();
                im.put(KeyStroke.getKeyStroke("UP"), "up");
                im.put(KeyStroke.getKeyStroke("W"), "up");
                im.put(KeyStroke.getKeyStroke("DOWN"), "down");
                im.put(KeyStroke.getKeyStroke("S"), "down");
                im.put(KeyStroke.getKeyStroke("ENTER"), "sel");

                am.put("up", new AbstractAction() {
                    public void actionPerformed(ActionEvent e) { up(); }
                });
                am.put("down", new AbstractAction() {
                    public void actionPerformed(ActionEvent e) { down(); }
                });
                am.put("sel", new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        if (onSelect != null) onSelect.run();
                    }
                });
            }

            void setActive(boolean a) {
                active = a;
                repaint();
            }

            void up() {
                sel = (sel + lines.size() - 1) % lines.size();
                repaint();
            }

            void down() {
                sel = (sel + 1) % lines.size();
                repaint();
            }

            void setOnSelect(java.util.function.Consumer<Integer> cb) {
                onSelect = () -> cb.accept(sel);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (!active) return;

                Graphics2D g2 = (Graphics2D) g;
                g2.setFont(getFont());
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                FontMetrics fm = g2.getFontMetrics();
                int totalH = lines.size() * fm.getHeight() * 2;
                int baseY = (getHeight() - totalH) / 2 + fm.getAscent();

                for (int i = 0; i < lines.size(); i++) {
                    String txt = lines.get(i);
                    int w = fm.stringWidth(txt);
                    int x = (getWidth() - w) / 2;
                    int y = baseY + i * fm.getHeight() * 2;

                    if (i == sel) {
                        g2.setColor(actor.color.brighter());
                        g2.drawString("► " + txt, x - 60, y);
                    } else {
                        g2.setColor(getForeground());
                        g2.drawString(txt, x, y);
                    }
                }
            }
        }
    }

    /* ====== INPUT SCENE ====== */
    class InputScene extends JPanel {
        InputScene(Actor who, String prompt, String correctAnswer,
                  Runnable onSuccess, Runnable onRetry) {
            setPreferredSize(new Dimension(1100, 700));
            setBackground(Color.BLACK);
            setLayout(new BorderLayout());

            Typewriter.Writer tw = Typewriter.create(who, prompt);
            tw.setBorder(new EmptyBorder(40, 80, 300, 80));
            add(tw, BorderLayout.NORTH);

            JPanel inputPanel = new JPanel();
            inputPanel.setOpaque(false);
            inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

            JTextField field = new JTextField(20);
            field.setFont(who.font.deriveFont(24f));
            field.setHorizontalAlignment(JTextField.CENTER);
            field.setForeground(who.color);
            field.setBackground(Color.BLACK);
            field.setCaretColor(who.color);
            field.setBorder(BorderFactory.createLineBorder(who.color, 2));
            field.setMaximumSize(new Dimension(400, 50));
            field.setVisible(false);
            inputPanel.add(field);

            JLabel error = new JLabel("Código incorreto. Tente novamente.");
            error.setFont(who.font.deriveFont(18f));
            error.setForeground(new Color(255, 100, 100));
            error.setAlignmentX(Component.CENTER_ALIGNMENT);
            error.setVisible(false);
            inputPanel.add(Box.createVerticalStrut(20));
            inputPanel.add(error);

            add(inputPanel, BorderLayout.CENTER);

            javax.swing.Timer t = new javax.swing.Timer(60, e -> {
                if (tw.isFinished()) {
                    ((javax.swing.Timer) e.getSource()).stop();
                    field.setVisible(true);
                    field.requestFocusInWindow();
                }
            });
            t.start();
            registerTimer(t);

            field.addActionListener(ev -> {
                if (field.getText().trim().equalsIgnoreCase(correctAnswer)) {
                    soundManager.playCharacterSound();
                    onSuccess.run();
                } else {
                    error.setVisible(true);
                    field.setText("");
                    field.requestFocusInWindow();
                }
            });

            setFocusable(true);
        }
    }

    /* ====== TYPEWRITER EFFECT ====== */
    final class Typewriter {
        private Typewriter() {}

        static Writer create(Actor who, String text) {
            return new Writer(who, text, who != null ? who.color : new Color(150, 150, 150));
        }

        static Writer create(Actor who, String text, Color color) {
            return new Writer(who, text, color);
        }

        static class Writer extends JComponent {
            private String full;
            private List<String> lines;
            private int chars = 0;
            private boolean cursor = true;
            private boolean finished = false;
            private Color textColor;

            Writer(Actor who, String text, Color color) {
                this.full = text;
                this.textColor = color;
                this.lines = wrapText(text, 65);

                setOpaque(false);
                setFont(new Font("Monospaced", Font.PLAIN, 20));
                setForeground(color);

                javax.swing.Timer typeTimer = new javax.swing.Timer(50, e -> {
                    if (chars < full.length()) {
                        chars++;
                        repaint();
                    } else {
                        ((javax.swing.Timer) e.getSource()).stop();
                        finished = true;
                        repaint();
                    }
                });
                typeTimer.start();

                javax.swing.Timer cursorTimer = new javax.swing.Timer(400, e -> {
                    cursor = !cursor;
                    if (!finished) repaint();
                });
                cursorTimer.start();
            }

            private List<String> wrapText(String text, int maxChars) {
                List<String> result = new ArrayList<>();
                String[] words = text.split(" ");
                StringBuilder line = new StringBuilder();

                for (String word : words) {
                    if ((line.length() + word.length() + 1) > maxChars) {
                        if (line.length() > 0) {
                            result.add(line.toString());
                            line = new StringBuilder();
                        }
                        line.append(word);
                    } else {
                        if (line.length() > 0) line.append(" ");
                        line.append(word);
                    }
                }
                if (line.length() > 0) {
                    result.add(line.toString());
                }
                return result;
            }

            boolean isFinished() {
                return finished;
            }

            @Override
            public Dimension getPreferredSize() {
                FontMetrics fm = getFontMetrics(getFont());
                int maxWidth = 0;
                for (String line : lines) {
                    maxWidth = Math.max(maxWidth, fm.stringWidth(line));
                }
                return new Dimension(maxWidth + 40, fm.getHeight() * lines.size() + 20);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(textColor);
                g2.setFont(getFont());

                FontMetrics fm = g2.getFontMetrics();
                int currentChar = 0;
                int y = fm.getAscent();

                for (String line : lines) {
                    int charsToShow = Math.min(chars - currentChar, line.length());
                    String visible = line.substring(0, charsToShow);
                    
                    if (chars >= currentChar + line.length() && 
                        finished && lines.indexOf(line) == lines.size() - 1) {
                        visible += (cursor ? "|" : " ");
                    } else if (charsToShow < line.length()) {
                        visible += (cursor ? "|" : " ");
                    }

                    g2.drawString(visible, 20, y);
                    y += fm.getHeight();
                    currentChar += line.length() + 1;
                }
            }
        }
    }
}
