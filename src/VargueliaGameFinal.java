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
 * Varguelia - Ella é Demais
 * Refatorado com CardLayout, Typewriter effect e narrativa completa
 * Personagens: Niuwë, Taila, Selenna, Jorgenssen, Roger, Dasko
 */
public class VargueliaGameFinal extends JFrame {
    private CardLayout cardLayout;
    private JPanel root;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VargueliaGameFinal::new);
    }

    public VargueliaGameFinal() {
        setTitle("Varguelia - Ella é Demais [v1.0]");
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
        // === TELA DE TÍTULO ===
        root.add(new TitleScreen(() -> gotoScene("intro1")), "title");
        
        // === ATO 1: O SILÊNCIO ===
        // Cena 1: Prólogo - Toque de recolher
        root.add(new DialogueScene(
            Actor.DASKO,
            "Alerta de toque de recolher em todo o território da Varguën! " +
            "Atenção, repito, toque de recolher em toda a área da Varguën!",
            () -> gotoScene("intro2")
        ), "intro1");

        // Cena 2: Niuwë no chuveiro
        root.add(new DialogueScene(
            Actor.NIUWE,
            "Olha só que cara esbelto… Espero que nada de mais interrompa o meu sossego.",
            () -> gotoScene("intro3")
        ), "intro2");

        // Cena 3: O céu vermelho
        root.add(new DialogueScene(
            Actor.NIUWE,
            "Ué? O que tá acontecendo? O céu… não está como antes. " +
            "Antes era azul escuro, agora tá num tom rubro… como se estivesse sangrando.",
            () -> gotoScene("choice1")
        ), "intro3");

        // Cena 4: Escolha - Ligação de Jorgenssen
        LinkedHashMap<String, String> choice1 = new LinkedHashMap<>();
        choice1.put("Atender a ligação", "jorgenssen1");
        choice1.put("Ignorar (não recomendado)", "jorgenssen1");

        root.add(new ChoiceScene(
            Actor.NIUWE,
            "O celular vibra. É Jorgenssen.",
            choice1,
            this::handleChoice
        ), "choice1");

        // Cena 5: Jorgenssen fala sobre Sepharoth
        root.add(new DialogueScene(
            Actor.JORGENSSEN,
            "Sepharoth, Niuwë. Você não acredita, mas aquela criatura… ela voltou.",
            () -> gotoScene("scene5")
        ), "jorgenssen1");

        // Cena 6: Reação de Niuwë
        root.add(new DialogueScene(
            Actor.NIUWE,
            "Visita de Häita? Sozinha? Merda…",
            () -> gotoScene("scene6")
        ), "scene5");

        // Cena 7: Niuwë liga para Taila
        root.add(new DialogueScene(
            Actor.NIUWE,
            "Mensa-Viktör! Levanta! É Sepharoth. Pegue o helicóptero. " +
            "Estação 4. Dez minutos. Agora!",
            () -> gotoScene("scene7")
        ), "scene6");

        // Cena 8: Taila responde sonolenta
        root.add(new DialogueScene(
            Actor.TAILA,
            "Isso tá virando rotina… Tudo bem, tudo bem. Vou indo.",
            () -> gotoScene("scene8")
        ), "scene7");

        // Cena 9: Niuwë na estação
        root.add(new DialogueScene(
            Actor.NIUWE,
            "Cor dia, coisinha linda. Dasko, você veio? Selenna está aí? " +
            "Bom. Vamos decolar. O tempo está se esgotando.",
            () -> gotoScene("choice2")
        ), "scene8");

        // Cena 10: Escolha - Estratégia
        LinkedHashMap<String, String> choice2 = new LinkedHashMap<>();
        choice2.put("Confrontar Sepharoth diretamente", "battle1");
        choice2.put("Fugir e regroupar", "battle1");

        root.add(new ChoiceScene(
            Actor.DASKO,
            "Niuwë, qual é o plano? Enfrentamos essa coisa ou evacuamos?",
            choice2,
            this::handleChoice
        ), "choice2");

        // Cena 11: Revelação de Sepharoth
        root.add(new DialogueScene(
            Actor.NIUWE,
            "Lá está ela… Seharoth. 200 metros de altura. Três cabeças. " +
            "Mas espera… tem mais. Vinte e quatro cabeças auxiliares? " +
            "Isso não era assim antes!",
            () -> gotoScene("battle1")
        ), "battle1");

        // Cena 12: Confronto final
        root.add(new DialogueScene(
            Actor.SELENNA,
            "Vinte e sete cabeças independentes? Que diabos é isso?! " +
            "Como vamos lutar contra essas proporções?!",
            () -> gotoScene("password1")
        ), "battle1");

        // Cena 13: Password Scene
        root.add(new InputScene(
            Actor.NIUWE,
            "Para continuar além deste ponto, você precisa de um código especial. " +
            "Você conhece a Fundação? Então sabe o código. Digite-o:",
            "BRIDGE",
            () -> gotoScene("ending"),
            () -> gotoScene("password1")
        ), "password1");

        // Cena 14: Ending
        root.add(new DialogueScene(
            Actor.ELLA,
            "Bem-vindo à ponte de comando. A verdade sobre Varguelia " +
            "está aqui. Você está pronto para descobri-la?",
            () -> {
                JOptionPane.showMessageDialog(this, 
                    "Fim do prólogo de Varguelia.\n\nO jogo completo virá em breve...");
                System.exit(0);
            }
        ), "ending");
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

    /* ====== ATORES DO JOGO ====== */
    enum Actor {
        NIUWE(new Font("Monospaced", Font.PLAIN, 20), new Color(255, 200, 0)),      // Louro
        TAILA(new Font("Monospaced", Font.PLAIN, 20), new Color(0, 255, 200)),      // Cyan
        SELENNA(new Font("Monospaced", Font.PLAIN, 20), new Color(255, 100, 255)),  // Magenta
        JORGENSSEN(new Font("Monospaced", Font.PLAIN, 20), new Color(255, 100, 100)), // Vermelho
        ROGER(new Font("Monospaced", Font.PLAIN, 20), new Color(100, 200, 255)),     // Azul
        DASKO(new Font("Monospaced", Font.PLAIN, 20), new Color(200, 200, 200)),     // Branco
        ELLA(new Font("Monospaced", Font.PLAIN, 20), new Color(200, 0, 200)),        // Roxo
        SEPHAROTH(new Font("Monospaced", Font.PLAIN, 20), new Color(200, 0, 0));     // Vermelho escuro

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
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);

            stars.forEach(p -> g2.fillRect((int) p.getX(), (int) p.getY(), 2, 2));

            // Título principal
            g2.setFont(new Font("Serif", Font.BOLD, 76));
            drawCentered(g2, "VARGUELIA", 120);

            // Subtítulo
            g2.setFont(new Font("Serif", Font.ITALIC, 36));
            g2.setColor(new Color(255, 100, 100));
            drawCentered(g2, "Ella é Demais", 180);

            // Logo da Fundação
            g2.setFont(new Font("Monospaced", Font.PLAIN, 18));
            g2.setColor(new Color(150, 150, 150));
            drawCentered(g2, "Fundação Varguélia [v1.0]", 240);

            // Instruções piscantes
            if (blink) {
                g2.setFont(new Font("Monospaced", Font.BOLD, 22));
                g2.setColor(new Color(0, 255, 200));
                drawCentered(g2, "Press ENTER to begin", 620);
            }

            // Aviso
            g2.setFont(new Font("Serif", Font.PLAIN, 14));
            g2.setColor(new Color(255, 100, 100));
            drawCentered(g2, "⚠ Contém violência, linguagem forte e conteúdo impróprio", 670);
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
            tw.setBorder(new EmptyBorder(50, 60, 50, 60));
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
            tw.setBorder(new EmptyBorder(40, 60, 200, 60));
            add(tw, BorderLayout.NORTH);

            List<String> labels = new ArrayList<>(options.keySet());
            destMap = new LinkedHashMap<>();
            for (int i = 0; i < labels.size(); i++) {
                destMap.put(i, options.get(labels.get(i)));
            }

            menu = new OptionPanel(who, labels);
            add(menu, BorderLayout.CENTER);

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
                setPreferredSize(new Dimension(1100, 100 + lines.size() * 50));

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
                int totalH = lines.size() * fm.getHeight();
                int baseY = (getHeight() - totalH) / 2 + fm.getAscent();

                for (int i = 0; i < lines.size(); i++) {
                    String txt = lines.get(i);
                    int w = fm.stringWidth(txt);
                    int x = (getWidth() - w) / 2;
                    int y = baseY + i * fm.getHeight();

                    if (i == sel) {
                        g2.setColor(actor.color.brighter());
                        g2.drawString("▶ " + txt, x - 60, y);
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
            tw.setBorder(new EmptyBorder(40, 60, 300, 60));
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
}
