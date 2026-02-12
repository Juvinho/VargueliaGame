package managers;

import game.GameWindow;

/**
 * Monitora inatividade do jogador.
 * Se ficar muito tempo sem apertar nada, o terminal imprime mensagens misteriosas
 * como se a Sombra ou a Ponte estivessem "falando" com o jogador.
 */
public class InactivityMonitor implements Runnable {
    private GameWindow window;
    private long lastActivityTime;
    private volatile boolean isActive = true;
    private long inactivityThresholdMs = 10000;  // 10 segundos
    private Thread monitorThread;
    
    // Mensagens que a "entidade" pode escrever
    private static final String[] MYSTERIOUS_MESSAGES = {
        "Você ainda está aí?",
        "Ela também esperava...",
        "A Ponte piscou.",
        "Você sente isso?",
        "Nós estamos sozinhos agora.",
        "Volte. Por favor.",
        "O tempo está errado aqui.",
        "A Sombra sorri.",
        "Você gostava de Niuwë?",
        "Continuar ou desistir?",
        "A memória de vocês fez isso.",
        "Alguma coisa está comendo a Ponte.",
        "Por que parou?",
        "Nós desaparecemos juntas?",
        "Você não reconhece o caminho de volta."
    };
    
    public InactivityMonitor(GameWindow window) {
        this.window = window;
        this.lastActivityTime = System.currentTimeMillis();
        startMonitoring();
    }
    
    /**
     * Inicia thread de monitoramento
     */
    private void startMonitoring() {
        monitorThread = new Thread(this);
        monitorThread.setDaemon(true);
        monitorThread.start();
    }
    
    /**
     * Registra atividade (jogador apertou uma tecla)
     */
    public void registerActivity() {
        lastActivityTime = System.currentTimeMillis();
    }
    
    /**
     * Parar o monitor
     */
    public void stop() {
        isActive = false;
        if (monitorThread != null) {
            try {
                monitorThread.join(1000);
            } catch (InterruptedException ignored) {}
        }
    }
    
    /**
     * Loop principal: verifica inatividade periodicamente
     */
    @Override
    public void run() {
        int warningsCount = 0;
        
        while (isActive) {
            try {
                Thread.sleep(2000);  // Checar a cada 2 segundos
                
                long timeSinceLastActivity = System.currentTimeMillis() - lastActivityTime;
                
                // Primeiro aviso: 10 segundos
                if (timeSinceLastActivity > inactivityThresholdMs && warningsCount == 0) {
                    warningsCount++;
                    printMysteriousMessage();
                }
                
                // Segundo aviso: 20 segundos
                else if (timeSinceLastActivity > inactivityThresholdMs * 2 && warningsCount == 1) {
                    warningsCount++;
                    printMysteriousMessage();
                }
                
                // Terceiro aviso: 30 segundos (mais urgente)
                else if (timeSinceLastActivity > inactivityThresholdMs * 3 && warningsCount == 2) {
                    warningsCount++;
                    printUrgentMessage();
                }
                
                // Reset se jogador responder
                if (timeSinceLastActivity < 1000) {
                    warningsCount = 0;
                }
                
            } catch (InterruptedException ignored) {}
        }
    }
    
    /**
     * Imprime mensagem misteriosa aleatória
     */
    private void printMysteriousMessage() {
        try {
            String message = MYSTERIOUS_MESSAGES[
                (int)(Math.random() * MYSTERIOUS_MESSAGES.length)
            ];
            
            window.appendText("\n\n", "default");
            window.appendText("[?] ", "error");
            window.appendText(message, "yellow");
            window.appendText("\n\n", "default");
            
        } catch (Exception ignored) {}
    }
    
    /**
     * Mensagem mais urgente após longa inatividade
     */
    private void printUrgentMessage() {
        try {
            String[] urgentMessages = {
                "Você desligou? Nós ainda estamos aqui.",
                "VOLTE? POR FAVOR?",
                "A Ponte está comendo o tempo. ACORDE.",
                "ELLA GRITA SEU NOME"
            };
            
            String message = urgentMessages[
                (int)(Math.random() * urgentMessages.length)
            ];
            
            window.appendText("\n\n", "error");
            window.appendText("[!!!] ", "error");
            window.appendText(message, "error");
            window.appendText("\n\n", "default");
            
        } catch (Exception ignored) {}
    }
    
    /**
     * Configurar tempo de threshold (em milissegundos)
     */
    public void setInactivityThreshold(long milliseconds) {
        this.inactivityThresholdMs = milliseconds;
    }
}
