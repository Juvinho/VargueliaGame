package game;

public class LogsScreen {
    private GameWindow window;
    private int selectedLog = 0;
    private String[] logsAvailable = {
        "LOG_NIUWE.TXT",
        "BRIDGE_EVENTS.DAT",
        "ELLA_DATA.BIN",
        "SYSTEM.LOG"
    };
    
    private String[] logsPreview = {
        "[16/11/2247 04:32] Niuwë acordou na estação confuso...",
        "[Acesso Restrito] Eventos da Ponte detectados...",
        "[Arquivo binário corrompido] Impossível ler...",
        "[VARGUEN-OS] Sistema operacional em funcionamento..."
    };
    
    public LogsScreen(GameWindow window) {
        this.window = window;
    }
    
    public void display() {
        window.clearText();
        
        window.appendText("\n\n", "default");
        window.appendText("════════════════════════════════════════════════════════════════\n", "default");
        window.appendText("║ C:\\VARGUEN\\LOGS                                               ║\n", "default");
        window.appendText("════════════════════════════════════════════════════════════════\n\n", "default");
        
        window.appendText("Arquivo de Processo 26.047\n", "yellow");
        window.appendText("Modo: VISUALIZAÇÃO DE LOGS\n\n", "cyan");
        
        // DIR - lista de arquivos
        window.appendText("C:\\VARGUEN\\LOGS", "cyan");
        window.appendText(">DIR\n\n", "default");
        window.appendText(" Volume em C:\\ é VARGUEN\n", "default");
        window.appendText(" Número de série: 3D7A-9B2F\n\n", "default");
        window.appendText(" Diretório de C:\\VARGUEN\\LOGS\n\n", "default");
        
        for (int i = 0; i < logsAvailable.length; i++) {
            String prefix = (i + 1) + ". ";
            if (i == selectedLog) {
                window.appendText("       ", "default");
                window.appendText("►", "cyan");
                window.appendText(" ");
                window.appendText(prefix, "cyan");
                window.appendText(String.format("%-20s", logsAvailable[i]), "cyan");
                window.appendText("  ");
                window.appendText(logsPreview[i], "yellow");
                window.appendText("\n", "default");
            } else {
                window.appendText("         ");
                window.appendText(prefix);
                window.appendText(String.format("%-20s", logsAvailable[i]));
                window.appendText("  ");
                window.appendText(logsPreview[i] + "\n", "default");
            }
        }
        
        window.appendText("\n");
        window.appendText("════════════════════════════════════════════════════════════════\n", "default");
        window.appendText("Use ", "default");
        window.appendText("↑/↓", "cyan");
        window.appendText(" ou ", "default");
        window.appendText("1-4", "cyan");
        window.appendText(" para selecionar | ", "default");
        window.appendText("ENTER", "cyan");
        window.appendText(" para ler | ", "default");
        window.appendText("ESC", "cyan");
        window.appendText(" para voltar\n", "default");
        
        window.setWaitingForInput(true);
    }
    
    public void handleUpArrow() {
        selectedLog = (selectedLog - 1 + logsAvailable.length) % logsAvailable.length;
        display();
    }
    
    public void handleDownArrow() {
        selectedLog = (selectedLog + 1) % logsAvailable.length;
        display();
    }
    
    public void readSelectedLog() {
        window.clearText();
        window.appendText("\n\n", "default");
        window.appendText("════════════════════════════════════════════════════════════════\n", "default");
        window.appendText("║ TYPE ", "cyan");
        window.appendText(logsAvailable[selectedLog], "yellow");
        window.appendText("                              ║\n", "default");
        window.appendText("════════════════════════════════════════════════════════════════\n\n", "default");
        
        // Conteúdo dos logs
        switch (selectedLog) {
            case 0: // LOG_NIUWE.TXT
                window.appendText("[16/11/2247 04:32:17] Acordei em uma cápsula. Não é a estação.\n", "default");
                window.appendText("[16/11/2247 04:35:42] Dasko estava aqui. Ele desapareceu.\n", "yellow");
                window.appendText("[16/11/2247 04:39:01] Os alunos... alguns foram para Estação 4.\n", "default");
                window.appendText("[16/11/2247 05:12:33] A Ponte...algo está acontecendo lá.\n", "error");
                window.appendText("[16/11/2247 06:00:00] Se você está lendo isso, eu não fui embora.\n", "yellow");
                break;
                
            case 1: // BRIDGE_EVENTS.DAT
                window.appendText("[EVENTO] Anomalia temporal detectada na Ponte.\n", "error");
                window.appendText("[DESCRIÇÃO] Entidade desconhecida presente no setor 7.\n", "default");
                window.appendText("[STATUS] Nível de alerta: CRÍTICO\n", "error");
                window.appendText("[ORIGEM] Desconhecida\n", "yellow");
                window.appendText("[NOTA] A Sombra está acordada.\n", "error");
                break;
                
            case 2: // ELLA_DATA.BIN
                window.appendText("[ERRO] Arquivo corrompido ou em formato binário.\n", "error");
                window.appendText("Tente usar: TYPE /B para leitura de dados brutos\n", "default");
                window.appendText("Acesso negado. Privilégios insuficientes.\n", "error");
                break;
                
            case 3: // SYSTEM.LOG
                window.appendText("VARGUEN-OS (v1.0) iniciado com sucesso.\n", "cyan");
                window.appendText("Carregando drivers de audio: OK\n", "default");
                window.appendText("Carregando sistema de sanidade: OK\n", "default");
                window.appendText("Carregando monitor de inatividade: OK\n", "default");
                window.appendText("Todos os sistemas em funcionamento.\n", "yellow");
                break;
        }
        
        window.appendText("\n════════════════════════════════════════════════════════════════\n", "default");
        window.appendText("Pressione ", "default");
        window.appendText("VOLTAR", "cyan");
        window.appendText(" ou ", "default");
        window.appendText("ESC", "cyan");
        window.appendText(" para retornar à lista\n", "default");
        window.setWaitingForInput(true);
    }
    
    public int getSelectedLog() {
        return selectedLog;
    }
}
