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
 * Varguelia - Refactored com CardLayout, Typewriter effect e cenas elegantes
 * Padrão inspirado em UI avançada para 1983
 */
public class VargueliaRefactored extends JFrame {
    private CardLayout cardLayout;
    private JPanel root;
    private SoundManager soundManager;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VargueliaRefactored::new);
    }

    public VargueliaRefactored() {
        soundManager = SoundManager.getInstance();
        
        setTitle("Varguelia - Ella é Demais");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
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
        // Cena 1: Tela de título
        root.add(new TitleScreen(() -> gotoScene("intro1")), "title");
        
        // Cena 2-4: Diálogos iniciais (trio com seleção)
        root.add(new DialogueScene(
            Actor.MAYA,
            "Bem...",
            () -> gotoScene("intro2")
        ), "intro1");

        root.add(new DialogueScene(
            Actor.MAYA,
            "Eu acho que...",
            () -> gotoScene("intro3")
        ), "intro2");

        root.add(new DialogueScene(
            Actor.MAYA,
            "É...",
            () -> gotoScene("choice1")
        ), "intro3");

        // Cena 5: Menu de escolha
        LinkedHashMap<String, String> opts1 = new LinkedHashMap<>();
        opts1.put("Conta logo, Sayedinne", "accel1");
        opts1.put("*Esperar ela falar*", "scene6");

        root.add(new ChoiceScene(
            Actor.MAYA,
            "Como eu posso falar isso?",
            opts1,
            this::handleChoice
        ), "choice1");

        // Branch acelerado
        root.add(new DialogueScene(
            Actor.MAYA,
            "Calma, calma, eu só estou tentando formular algo",
            () -> gotoScene("accel2")
        ), "accel1");

        root.add(new DialogueScene(
            Actor.MAYA,
            "É que… tem algo me fazendo mal…",
            () -> gotoScene("accel3")
        ), "accel2");

        root.add(new DialogueScene(
            Actor.TAYRONE,
            "Custe o que custar, se você não está bem, estou aqui",
            () -> gotoScene("scene6")
        ), "accel3");

        // Cena 6: Revelação principal
        root.add(new DialogueScene(
            Actor.MAYA,
            "Existe algo que anda me causando algo muito ruim",
            () -> gotoScene("scene7")
        ), "scene6");

        root.add(new DialogueScene(
            Actor.MAYA,
            "Algo muito estranho, uma força anafilatica",
            () -> gotoScene("password1")
        ), "scene7");

        // Cena 8: Password input
        root.add(new InputScene(
            Actor.ELLA,
            "Enter the code to proceed:",
            "BRIDGE",
            () -> gotoScene("end"),
            () -> gotoScene("password1")
        ), "password1");

        // Fim
        root.add(new DialogueScene(
            Actor.ELLA,
            "Access granted. Welcome to the bridge...",
            () -> {
                JOptionPane.showMessageDialog(this, "Fim da demo.");
                System.exit(0);
            }
        ), "end");
    }

    private void handleChoice(String nextScene) {
        gotoScene(nextScene);
    }

    private void gotoScene(String id) {
        cardLayout.show(root, id);
        for (Component c : root.getComponents()) {
            if (c.isVisible()) {
                c.requestFocusInWindow();
                break;
            }
        }
    }

    /* ====== ATORES ====== */
    enum Actor {
        MAYA(new Font("Monospaced", Font.PLAIN, 20), new Color(0, 255, 200)),
        TAYRONE(new Font("Monospaced", Font.PLAIN, 20), new Color(255, 165, 0)),
        ELLA(new Font("Monospaced", Font.PLAIN, 20), new Color(255, 0, 200)),
        NIUWE(new Font("Monospaced", Font.PLAIN, 20), new Color(0, 255, 100));

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
            setPreferredSize(new Dimension(1000, 700));
            setBackground(Color.BLACK);

            Random r = new Random();
            for (int i = 0; i < 150; i++) {
                stars.add(new Point2D.Double(r.nextInt(1000), r.nextInt(700)));
            }

            new javax.swing.Timer(500, e -> {
                blink = !blink;
                repaint();
            }).start();

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
            g2.setColor(Color.WHITE);

            // Desenhar stars
            stars.forEach(p -> g2.fillRect((int) p.getX(), (int) p.getY(), 2, 2));

            // Título
            g2.setFont(new Font("Serif", Font.BOLD, 72));
            drawCentered(g2, "VARGUELIA", 150);

            // Subtítulo
            g2.setFont(new Font("Serif", Font.PLAIN, 32));
            drawCentered(g2, "Ella é Demais", 230);

            // Instruções piscantes
            if (blink) {
                g2.setFont(new Font("Monospaced", Font.BOLD, 24));
                g2.setColor(new Color(0, 255, 200));
                drawCentered(g2, "Press ENTER to continue", 600);
            }
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
            setPreferredSize(new Dimension(1000, 700));
            setBackground(Color.BLACK);
            setLayout(new BorderLayout());

            // Typewriter
            tw = Typewriter.create(who, text);
            tw.setBorder(new EmptyBorder(40, 40, 40, 40));
            add(tw, BorderLayout.CENTER);

            // Nome do ator (topo)
            JLabel actorName = new JLabel(who.label(), SwingConstants.CENTER);
            actorName.setOpaque(false);
            actorName.setFont(who.font.deriveFont(Font.BOLD, 28f));
            actorName.setForeground(who.color);
            actorName.setBorder(new EmptyBorder(20, 0, 0, 0));
            add(actorName, BorderLayout.NORTH);

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

    /* ====== CHOICE SCENE ====== */
    class ChoiceScene extends JPanel {
        private OptionPanel menu;
        private Map<Integer, String> destMap;

        ChoiceScene(Actor who, String prompt, LinkedHashMap<String, String> options,
                   java.util.function.Consumer<String> onSelect) {
            setPreferredSize(new Dimension(1000, 700));
            setBackground(Color.BLACK);
            setLayout(new BorderLayout());

            // Prompt
            Typewriter.Writer tw = Typewriter.create(who, prompt);
            tw.setBorder(new EmptyBorder(40, 40, 200, 40));
            add(tw, BorderLayout.NORTH);

            // Menu
            List<String> labels = new ArrayList<>(options.keySet());
            destMap = new LinkedHashMap<>();
            for (int i = 0; i < labels.size(); i++) {
                destMap.put(i, options.get(labels.get(i)));
            }

            menu = new OptionPanel(who, labels);
            add(menu, BorderLayout.CENTER);

            // Ativar menu após typewriter terminar
            new javax.swing.Timer(80, e -> {
                if (tw.isFinished()) {
                    ((javax.swing.Timer) e.getSource()).stop();
                    menu.setActive(true);
                    menu.requestFocusInWindow();
                }
            }).start();

            menu.setOnSelect((idx) -> {
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
                setFont(who.font.deriveFont(Font.BOLD, 26f));
                setForeground(who.color);
                setFocusable(true);
                setPreferredSize(new Dimension(1000, 100 + lines.size() * 40));

                InputMap im = getInputMap(WHEN_FOCUSED);
                ActionMap am = getActionMap();
                im.put(KeyStroke.getKeyStroke("UP"), "up");
                im.put(KeyStroke.getKeyStroke("W"), "up");
                im.put(KeyStroke.getKeyStroke("DOWN"), "down");
                im.put(KeyStroke.getKeyStroke("S"), "down");
                im.put(KeyStroke.getKeyStroke("ENTER"), "sel");

                am.put("up", new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        up();
                    }
                });
                am.put("down", new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        down();
                    }
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
                int totalH = lines.size() * fm.getHeight();
                int baseY = (getHeight() - totalH) / 2 + fm.getAscent();

                for (int i = 0; i < lines.size(); i++) {
                    String txt = lines.get(i);
                    int w = fm.stringWidth(txt);
                    int x = (getWidth() - w) / 2;
                    int y = baseY + i * fm.getHeight();

                    if (i == sel) {
                        g2.setColor(actor.color.brighter());
                        g2.drawString("▶ " + txt, x - 50, y);
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
            setPreferredSize(new Dimension(1000, 700));
            setBackground(Color.BLACK);
            setLayout(new BorderLayout());

            // Prompt
            Typewriter.Writer tw = Typewriter.create(who, prompt);
            tw.setBorder(new EmptyBorder(40, 40, 300, 40));
            add(tw, BorderLayout.NORTH);

            // Input field
            JPanel inputPanel = new JPanel();
            inputPanel.setOpaque(false);
            inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
            
            JTextField field = new JTextField(20);
            field.setFont(who.font.deriveFont(24f));
            field.setHorizontalAlignment(JTextField.CENTER);
            field.setForeground(who.color);
            field.setBackground(Color.BLACK);
            field.setCaretColor(who.color);
            field.setBorder(BorderFactory.createLineBorder(who.color));
            field.setMaximumSize(new Dimension(400, 50));
            field.setVisible(false);
            inputPanel.add(field);

            JLabel error = new JLabel("Incorrect code. Try again.");
            error.setFont(who.font.deriveFont(20f));
            error.setForeground(new Color(255, 100, 100));
            error.setAlignmentX(Component.CENTER_ALIGNMENT);
            error.setVisible(false);
            inputPanel.add(Box.createVerticalStrut(20));
            inputPanel.add(error);

            add(inputPanel, BorderLayout.CENTER);

            new javax.swing.Timer(60, e -> {
                if (tw.isFinished()) {
                    ((javax.swing.Timer) e.getSource()).stop();
                    field.setVisible(true);
                    field.requestFocusInWindow();
                }
            }).start();

            field.addActionListener(ev -> {
                if (field.getText().trim().equalsIgnoreCase(correctAnswer)) {
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
            return new Writer(who, text);
        }

        static class Writer extends JComponent {
            private String full;
            private int chars = 0;
            private boolean cursor = true;
            private boolean finished = false;

            Writer(Actor who, String text) {
                this.full = text;
                setOpaque(false);
                setFont(who.font.deriveFont(24f));
                setForeground(who.color);

                // Timer de digitação
                new javax.swing.Timer(50, e -> {
                    if (chars < full.length()) {
                        chars++;
                        repaint();
                    } else {
                        ((javax.swing.Timer) e.getSource()).stop();
                        finished = true;
                        repaint();
                    }
                }).start();

                // Timer de cursor
                new javax.swing.Timer(400, e -> {
                    cursor = !cursor;
                    if (!finished) repaint();
                }).start();
            }

            boolean isFinished() {
                return finished;
            }

            @Override
            public Dimension getPreferredSize() {
                FontMetrics fm = getFontMetrics(getFont());
                return new Dimension(fm.stringWidth(full) + 40, fm.getHeight() + 20);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                String vis = full.substring(0, chars) +
                        (finished ? "" : (cursor ? "|" : " "));

                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(vis)) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 10;
                g2.drawString(vis, x, y);
            }
        }
    }

    /* ====== SOUND MANAGER ====== */
    static class SoundManager {
        private static SoundManager instance;

        static SoundManager getInstance() {
            if (instance == null) {
                instance = new SoundManager();
            }
            return instance;
        }

        void playEffect(String name) {
            // Placeholder para efeitos sonoros
        }

        void playMusic(String theme) {
            // Placeholder para música
        }
    }
}
