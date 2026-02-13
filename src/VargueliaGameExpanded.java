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
 * Varguelia - Ella é Demais [EXPANDED]
 * Com SoundManager, Text Wrapping, Branches e +30 cenas
 * Personagens: Niuwë, Taila, Selenna, Jorgenssen, Roger, Dasko, Ella, Sepharoth
 */
public class VargueliaGameExpanded extends JFrame {
    private CardLayout cardLayout;
    private JPanel root;
    private SoundManager soundManager;
    private GameState gameState = new GameState();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VargueliaGameExpanded::new);
    }

    public VargueliaGameExpanded() {
        setTitle("Varguelia - Ella é Demais [EXPANDED v2.0]");
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
        // === TELA DE TÍTULO ===
        root.add(new TitleScreen(() -> gotoScene("narration1")), "title");
        
        // === ATO 1: O SILÊNCIO É UM BOM ALIADO ===
        
        // Cena 1: Narrador - Aviso de toque de recolher
        root.add(new NarratorScene(
            "Alerta de toque de recolher em todo o território da Varguën! " +
            "Atenção, repito, toque de recolher em toda a área da Varguën!",
            new Color(200, 200, 200),
            () -> gotoScene("scene2")
        ), "narration1");

        // Cena 2: Narrador - Caos na escola
        root.add(new NarratorScene(
            "A voz grave e rígida ecoava pelos alto-falantes. O pânico instalou-se " +
            "instantaneamente. Os alunos correram desesperados, empurrando-se, " +
            "tropeçando uns nos outros, buscando refúgio onde pudessem.",
            new Color(150, 100, 100),
            () -> gotoScene("scene3")
        ), "scene2");

        // Cena 3: Narrador - Dasko observando
        root.add(new NarratorScene(
            "Dasko afastou-se lentamente do microfone, os olhos arregalados " +
            "fitando o horizonte além da muralha da Escola. Ela estava vindo. " +
            "Uma criatura colossal, cuja silhueta recortava-se contra o céu escuro, " +
            "movia-se com a inevitabilidade de uma tempestade.",
            new Color(180, 150, 200),
            () -> gotoScene("scene4")
        ), "scene3");

        // Cena 4: Niuwë no chuveiro (autocuidado narcisista)
        root.add(new DialogueScene(
            Actor.NIUWE,
            "Olha só que cara esbelto… Espero que nada de mais interrompa " +
            "o meu sossego tão precioso.",
            () -> gotoScene("scene5")
        ), "scene4");

        // Cena 5: Niuwë vê o céu vermelho
        root.add(new DialogueScene(
            Actor.NIUWE,
            "Ué? O que tá acontecendo? O céu… não está como antes. " +
            "Antes era azul escuro, agora tá num tom rubro… como se estivesse sangrando.",
            () -> gotoScene("scene6")
        ), "scene5");

        // Cena 6: Narrador - Roger e Jorgenssen no laboratório
        root.add(new NarratorScene(
            "Em outro ponto da Escola, Jorgenssen corria pelo pátio principal, " +
            "com Roger logo atrás. Os corredores eram um pandemônio. Alunos " +
            "empurravam-se desesperados, gritos vinham de todas as direções.",
            new Color(150, 150, 150),
            () -> gotoScene("scene7")
        ), "scene6");

        // Cena 7: Jorgenssen e Roger - Estratégia
        root.add(new DialogueScene(
            Actor.JORGENSSEN,
            "Temos que dar a esses alunos um motivo maior para correr. " +
            "Roger, consegue rastrear a posição dela?",
            () -> gotoScene("scene8")
        ), "scene7");

        // Cena 8: Roger analisa dados
        root.add(new DialogueScene(
            Actor.ROGER,
            "Estou entrando no sistema... Processando dados de radar... " +
            "Céu! Ela está exatamente 52 minutos de distância. " +
            "E não está sozinha.",
            () -> gotoScene("scene9")
        ), "scene8");

        // Cena 9: Jorgenssen reage
        root.add(new DialogueScene(
            Actor.JORGENSSEN,
            "52 minutos? Merda… Niuwë precisa saber disso agora. " +
            "Se ele é quem pensamos que é, ele pode nos salvar.",
            () -> gotoScene("scene10")
        ), "scene9");

        // Cena 10: Narrador - Niuwë recebendo a ligação
        root.add(new NarratorScene(
            "Niuwë ainda estava em seu dormitório, observando o céu " +
            "avermelhado pela janela, quando seu celular vibrou. " +
            "Era Jorgenssen. Ele sabia o que aquilo significava.",
            new Color(200, 180, 100),
            () -> gotoScene("scene11")
        ), "scene10");

        // Cena 11: Jorgenssen liga para Niuwë
        root.add(new DialogueScene(
            Actor.JORGENSSEN,
            "Sepharoth, Niuwë. Você não acredita, mas aquela criatura… " +
            "Ela voltou. E desta vez, ela não está sozinha.",
            () -> gotoScene("choice1")
        ), "scene11");

        // Cena 12: Escolha - Como Niuwë reage?
        LinkedHashMap<String, String> choice1 = new LinkedHashMap<>();
        choice1.put("'Merda… ele tem três cabeças?' - Chocar", "react1");
        choice1.put("'Não acredito. Você tem certeza?' - Questionar", "react2");
        choice1.put("'Quanto tempo temos?' - Pragmático", "react3");

        root.add(new ChoiceScene(
            Actor.NIUWE,
            "Como Niuwë reage à notícia de Sepharoth?",
            choice1,
            (choice) -> {
                gameState.niuweReaction = choice;
                handleChoice(choice);
            }
        ), "choice1");

        // Reação 1: Chocar
        root.add(new DialogueScene(
            Actor.NIUWE,
            "Merda… ELE TEM TRÊS CABEÇAS?! Que porra de criatura é essa?!",
            () -> gotoScene("scene12")
        ), "react1");

        // Reação 2: Questionar
        root.add(new DialogueScene(
            Actor.NIUWE,
            "Você tem certeza? Ou vou estar correndo para nada de novo?",
            () -> gotoScene("scene12")
        ), "react2");

        // Reação 3: Pragmático
        root.add(new DialogueScene(
            Actor.NIUWE,
            "Quanto tempo temos até ela chegar? E qual é o tamanho?",
            () -> gotoScene("scene12")
        ), "react3");

        // Cena 13: Niuwë se veste e corre
        root.add(new NarratorScene(
            "Niuwë não hesitou. Com movimentos rápidos, jogou a toalha de lado " +
            "e vestiu-se em tempo recorde. Apagou as luzes, pegou suas armas " +
            "e saiu disparado pelos corredores da Escola.",
            new Color(200, 100, 100),
            () -> gotoScene("scene13")
        ), "scene12");

        // Cena 14: Niuwë organiza os alunos
        root.add(new DialogueScene(
            Actor.NIUWE,
            "Sigam para o setor Sul 5! O bunker está aberto! " +
            "Coordenadores, reúnam seus grupos! RÁPIDO!",
            () -> gotoScene("scene14")
        ), "scene13");

        // Cena 15: Narrador - Niuwë liga para Dasko
        root.add(new NarratorScene(
            "Enquanto corria pelos corredores do caos, Niuwë pegou o celular " +
            "novamente e ligou para o Diretor Dasko. O telefone tocou uma, " +
            "duas vezes. Finalmente, a linha foi atendida.",
            new Color(200, 200, 200),
            () -> gotoScene("scene15")
        ), "scene14");

        // Cena 16: Dasko no telefone
        root.add(new DialogueScene(
            Actor.DASKO,
            "Niuwë! Ainda bem que você está aí! Qual a situação? " +
            "O caos lá fora é indescritível!",
            () -> gotoScene("scene16")
        ), "scene15");

        // Cena 17: Niuwë dá instruções
        root.add(new DialogueScene(
            Actor.NIUWE,
            "Convoque o Conselho Estudantil. Mobilize os líderes de cada setor " +
            "para auxiliar na evacuação. Use os alto-falantes, use os professores. " +
            "E depois venha para a estação 4. Eu te encontro lá.",
            () -> gotoScene("scene17")
        ), "scene16");

        // Cena 18: Narrador - Niuwë correndo
        root.add(new NarratorScene(
            "O vento quente cortava sua pele enquanto corria em direção à estação 4. " +
            "O horizonte estava coberto por sombras que não estavam ali antes. " +
            "O céu rubro parecia arder, como se algo dentro dele estivesse vivo, pulsando.",
            new Color(200, 80, 80),
            () -> gotoScene("scene18")
        ), "scene17");

        // Cena 19: Narrador - Taila acordando
        root.add(new NarratorScene(
            "Em outro dormitório, Taila foi acordada pela ligação de Niuwë. " +
            "Preguiçosa, sonolenta, ela respondeu com tédio.",
            new Color(0, 200, 200),
            () -> gotoScene("scene19")
        ), "scene18");

        // Cena 20: Taila recebe instruções
        root.add(new DialogueScene(
            Actor.TAILA,
            "Pegue o helicóptero e voe até a estação 4. Eu chego em dez minutos. " +
            "AGORA, Taila! Sepharoth tá vindo!",
            () -> gotoScene("scene20")
        ), "scene19");

        // Cena 21: Taila concorda (com reclamações)
        root.add(new DialogueScene(
            Actor.TAILA,
            "Isso tá virando rotina… Tudo bem, tudo bem. Tá bem, Niuwë. " +
            "Vou colocar aquele helicóptero no ar. Espero não bater em nada " +
            "com esse caos todo.",
            () -> gotoScene("scene21")
        ), "scene20");

        // Cena 22: Narrador - A chegada iminente
        root.add(new NarratorScene(
            "A vibração profunda que ressoava pelo solo era o primeiro sinal " +
            "de que o inferno estava prestes a descer sobre eles. O cheiro metálico " +
            "de sangue misturado com poeira enchia o ar.",
            new Color(180, 80, 80),
            () -> gotoScene("scene22")
        ), "scene21");

        // Cena 23: Escolha - Como encarar Sepharoth?
        LinkedHashMap<String, String> choice2 = new LinkedHashMap<>();
        choice2.put("Confrontar diretamente - Precisamos lutar!", "strat1");
        choice2.put("Estratégico - Analisar primeiro", "strat2");
        choice2.put("Fugir e regroupar - Sobrevivência primeiro", "strat3");

        root.add(new ChoiceScene(
            Actor.JORGENSSEN,
            "Niuwë está chegando. Qual é o plano? Como encaramos isso?",
            choice2,
            (choice) -> {
                gameState.strategy = choice;
                handleChoice(choice);
            }
        ), "scene22");

        // Estratégia 1: Confronto
        root.add(new DialogueScene(
            Actor.NIUWE,
            "Confronto direto. Se ela consegue gerar medo assim, " +
            "preciso enfrentar isso de frente. Vamos mostrar que a humanidade " +
            "não cai tão fácil.",
            () -> gotoScene("scene23")
        ), "strat1");

        // Estratégia 2: Análise
        root.add(new DialogueScene(
            Actor.ROGER,
            "Espera, esperamos um minuto. Preciso analisar a estrutura desta coisa. " +
            "Com 27 cabeças independentes, há padrões. Há uma fraqueza.",
            () -> gotoScene("scene23")
        ), "strat2");

        // Estratégia 3: Fuga
        root.add(new DialogueScene(
            Actor.TAILA,
            "Estão loucos? Precisamos de mais tempo. Ela tem 200 metros de altura! " +
            "Levamos os alunos, saímos da zona, e depois pensamos.",
            () -> gotoScene("scene23")
        ), "strat3");

        // Cena 24: Descrição de Sepharoth se aproximando
        root.add(new NarratorScene(
            "Então, como se a própria realidade se contorcesse, Sepharoth " +
            "apareceu no horizonte. Uma criatura colossal de 200 metros de altura, " +
            "com três cabeças principais, e 24 cabeças auxiliares rastejando " +
            "ao seu redor como um enxame. O céu parecia se dividir.",
            new Color(200, 0, 0),
            () -> gotoScene("scene24")
        ), "scene23");

        // Cena 25: Reação de Niuwë
        root.add(new DialogueScene(
            Actor.NIUWE,
            "Lá está ela… Seharoth. 200 metros.. Três cabeças.. " +
            "Mas espera… tem mais. Vinte e quatro cabeças auxiliares? " +
            "Isso não era assim antes!",
            () -> gotoScene("scene25")
        ), "scene24");

        // Cena 26: Reação de Selenna
        root.add(new DialogueScene(
            Actor.SELENNA,
            "Vinte e sete cabeças independentes? Que diabos é isso?! " +
            "Como vamos lutar contra essas proporções?!",
            () -> gotoScene("scene26")
        ), "scene25");

        // Cena 27: Roger - análise científica
        root.add(new DialogueScene(
            Actor.ROGER,
            "Segundo meus cálculos, cada cabeça tem sua própria rede neural. " +
            "É como 27 organismos separados… mas conectados. " +
            "Se conseguirmos desativar a cabeça principal…",
            () -> gotoScene("scene27")
        ), "scene26");

        // Cena 28: Jorgenssen - determinação
        root.add(new DialogueScene(
            Actor.JORGENSSEN,
            "Então é assim que vai ser. Niuwë, seus homens estão prontos? " +
            "Porque em cinco minutos, Sepharoth vai estar do lado de dentro desses muros.",
            () -> gotoScene("scene28")
        ), "scene27");

        // Cena 29: Descrição final antes do código
        root.add(new NarratorScene(
            "A criatura se aproximava cada vez mais. As sombras de suas cabeças " +
            "cobriam toda a paisagem. O solo tremia sob seu peso. " +
            "E então, nas entranhas da Fundação, um sistema despertou. " +
            "Um sistema que poucos conheciam. Um código que poucas pessoas tinham.",
            new Color(80, 80, 200),
            () -> gotoScene("scene29")
        ), "scene28");

        // Cena 30: Dasko no comando
        root.add(new DialogueScene(
            Actor.DASKO,
            "Iniciando Protocolo Genesis. Código de acesso requerido. " +
            "Niuwë, você conhece o código? A Fundação confia em você.",
            () -> gotoScene("password1")
        ), "scene29");

        // Cena 31: Password Scene
        root.add(new InputScene(
            Actor.DASKO,
            "Digite o código de acesso para continuar. " +
            "Você conhece a Fundação. Você sabe o código.",
            "BRIDGE",
            () -> gotoScene("ending"),
            () -> gotoScene("password1")
        ), "password1");

        // Cena 32: Ella aparece (final)
        root.add(new DialogueScene(
            Actor.ELLA,
            "Bem-vindo à ponte de comando do Protocolo Genesis. " +
            "A verdade sobre Varguelia, sobre Sepharoth, sobre tudo… " +
            "está aqui. Você está pronto para descobri-la?",
            () -> {
                JOptionPane.showMessageDialog(this, 
                    "FIM DO PRÓLOGO - VARGUELIA: ELLA É DEMAIS\n\n" +
                    "Reação de Niuwë: " + gameState.niuweReaction + "\n" +
                    "Estratégia: " + gameState.strategy + "\n\n" +
                    "Próximo capítulo em breve...");
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

    /* ====== GAME STATE ====== */
    class GameState {
        String niuweReaction = "";
        String strategy = "";
    }

    /* ====== SOUND MANAGER ====== */
    class SoundManager {
        void playCharacterSound() {
            // Simula um beep do sistema quando personagem fala
            try {
                Toolkit.getDefaultToolkit().beep();
            } catch (Exception e) {
                // Silent fail
            }
        }
    }

    /* ====== NARRATOR SCENE (novo) ====== */
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

    /* ====== ATORES DO JOGO ====== */
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

            g2.setFont(new Font("Serif", Font.BOLD, 76));
            drawCentered(g2, "VARGUELIA", 120);

            g2.setFont(new Font("Serif", Font.ITALIC, 36));
            g2.setColor(new Color(255, 100, 100));
            drawCentered(g2, "Ella é Demais", 180);

            g2.setFont(new Font("Monospaced", Font.PLAIN, 18));
            g2.setColor(new Color(150, 150, 150));
            drawCentered(g2, "Fundação Varguélia [v2.0 EXPANDED]", 240);

            if (blink) {
                g2.setFont(new Font("Monospaced", Font.BOLD, 22));
                g2.setColor(new Color(0, 255, 200));
                drawCentered(g2, "Press ENTER to begin", 620);
            }

            g2.setFont(new Font("Serif", Font.PLAIN, 14));
            g2.setColor(new Color(255, 100, 100));
            drawCentered(g2, "⚠ Contém violência, linguagem forte e conteúdo impróprio", 670);
        }

        private void drawCentered(Graphics2D g2, String txt, int y) {
            int w = g2.getFontMetrics().stringWidth(txt);
            g2.drawString(txt, (getWidth() - w) / 2, y);
        }
    }

    /* ====== DIALOGUE SCENE (melhorado com text wrapping) ====== */
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

            new javax.swing.Timer(80, e -> {
                if (tw.isFinished()) {
                    ((javax.swing.Timer) e.getSource()).stop();
                    menu.setActive(true);
                    menu.requestFocusInWindow();
                }
            }).start();

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

            new javax.swing.Timer(60, e -> {
                if (tw.isFinished()) {
                    ((javax.swing.Timer) e.getSource()).stop();
                    field.setVisible(true);
                    field.requestFocusInWindow();
                }
            }).start();

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

    /* ====== TYPEWRITER EFFECT (com text wrapping) ====== */
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
                this.lines = wrapText(text, 65); // 65 caracteres por linha

                setOpaque(false);
                setFont(new Font("Monospaced", Font.PLAIN, 20));
                setForeground(color);

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

                new javax.swing.Timer(400, e -> {
                    cursor = !cursor;
                    if (!finished) repaint();
                }).start();
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
