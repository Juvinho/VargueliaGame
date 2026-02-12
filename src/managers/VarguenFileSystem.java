package managers;

import game.GameWindow;
import java.util.*;

/**
 * Simulador de sistema de arquivos DOS.
 * O jogo inventa arquivos e diretórios relacionados à história.
 * O terminal pode "acessar" esses arquivos para simular exploração de dados.
 */
public class VarguenFileSystem {
    private GameWindow window;
    private Set<String> existingFiles;
    private Map<String, String> fileContents;
    
    public VarguenFileSystem(GameWindow window) {
        this.window = window;
        this.existingFiles = new HashSet<>();
        this.fileContents = new HashMap<>();
        initializeDefaultFiles();
    }
    
    /**
     * Criação de arquivos que existem no "PC de Varguén"
     */
    private void initializeDefaultFiles() {
        // Arquivos do Ato 1 (Niuwë)
        existingFiles.add("LOG_NIUWE.TXT");
        fileContents.put("LOG_NIUWE.TXT", 
            "REGISTRO DE NIUWË [INCOMPLETO]\n" +
            "[01:23] Acordei na estação. Algo errado.\n" +
            "[01:47] Diretora não responde. A ponte pisca.\n" +
            "[02:15] Encontrei documentos. Contêm nomes que não deveriam existir.\n" +
            "[ARQUIVO CORROMPIDO - FALTAM SEÇÕES]"
        );
        
        existingFiles.add("ELLA_DATA.BIN");
        fileContents.put("ELLA_DATA.BIN", 
            "ARQUIVO BINÁRIO - NÃO LEGÍVEL\n" +
            "Tamanho: 23,456 bytes\n" +
            "Data: [DESCONHECIDA]\n" +
            "Status: CORRUPTED / ??? / ALIVE?"
        );
        
        existingFiles.add("BRIDGE_EVENTS.DAT");
        fileContents.put("BRIDGE_EVENTS.DAT",
            "[EVENTO]\n" +
            "  Timestamp: INDEFINIDO\n" +
            "  Tipo: INCURSÃO\n" +
            "  Status: ATIVO\n" +
            "  Nível de Influência: ???"
        );
        
        existingFiles.add("MEMORY_BACKUP.001");
        fileContents.put("MEMORY_BACKUP.001",
            "BACKUP DE MEMÓRIA - ATO 1\n" +
            "Integridade: 87%\n" +
            "[Este arquivo contém memórias de Niuwë]\n" +
            "[Acessível apenas com privilégios ELLA_ECB]"
        );
        
        existingFiles.add("SYSTEM.LOG");
        fileContents.put("SYSTEM.LOG",
            "VARGUEN-OS v1.0 SYSTEM LOG\n" +
            "Boot: OK\n" +
            "Hardware Check: OK\n" +
            "Temporal Sync: ??? (desincronizado?)\n" +
            "Anomalias detectadas: 7\n" +
            "Recomendação: EVACUAR"
        );
        
        existingFiles.add("SOMBRA.???");
        fileContents.put("SOMBRA.???",
            "[ARQUIVO NÃO CATALOGADO]\n" +
            "[PERMISSÃO NEGADA]\n" +
            "[MAS ELA ESTÁ AQUI]"
        );
    }
    
    /**
     * Executa comando DIR (lista arquivos)
     */
    public void executeDir(String path) throws InterruptedException {
        String output = "Volume em VARGUEN-OS 1.0\n";
        output += "Diretório de " + path + "\n\n";
        output += "Nome do Arquivo              Tamanho    Data\n";
        output += "───────────────────────────────────────────────\n";
        
        for (String file : existingFiles) {
            String sizeStr = (int)(Math.random() * 50000) + " bytes";
            output += String.format("%-30s %10s\n", file, sizeStr);
        }
        output += "───────────────────────────────────────────────\n";
        output += existingFiles.size() + " arquivo(s)\n";
        
        window.appendText("\n\n", "default");
        window.appendText("C:\\VARGUEN> DIR\n\n", "cyan");
        window.appendText(output, "default");
        Thread.sleep(600);
    }
    
    /**
     * Executa comando TYPE (lê arquivo)
     */
    public void executeType(String filename) throws InterruptedException {
        if (!existingFiles.contains(filename) && !filename.equals("SOMBRA.???")) {
            window.appendText("\n\n", "default");
            window.appendText("C:\\VARGUEN> TYPE " + filename + "\n\n", "cyan");
            window.appendText("Arquivo não encontrado.\n", "error");
            Thread.sleep(300);
            return;
        }
        
        if (filename.equals("SOMBRA.???")) {
            // Acesso negado com glitch
            window.appendText("\n\n", "default");
            window.appendText("C:\\VARGUEN> TYPE SOMBRA.???\n\n", "cyan");
            Thread.sleep(200);
            window.appendText("█████████████████\n", "error");
            Thread.sleep(100);
            window.appendText("█████████████████\n", "error");
            Thread.sleep(100);
            window.appendText("[PERMISSÃO NEGADA]\n", "error");
            Thread.sleep(400);
            return;
        }
        
        window.appendText("\n\n", "default");
        window.appendText("C:\\VARGUEN> TYPE " + filename + "\n\n", "cyan");
        
        String content = fileContents.get(filename);
        if (content != null) {
            // Exibir com pequenos delays (simular "digitação" do sistema)
            for (String line : content.split("\n")) {
                window.appendText(line + "\n", "default");
                Thread.sleep(100);
            }
        }
        
        Thread.sleep(500);
    }
    
    /**
     * Cria novo arquivo (Ella registrando eventos)
     */
    public void createFile(String filename, String content) {
        existingFiles.add(filename);
        fileContents.put(filename, content);
    }
    
    /**
     * Retorna se arquivo existe
     */
    public boolean fileExists(String filename) {
        return existingFiles.contains(filename);
    }
    
    /**
     * Retorna conteúdo de arquivo (para uso em narrativa)
     */
    public String getFileContent(String filename) {
        return fileContents.getOrDefault(filename, "");
    }
    
    /**
     * Lista todos os arquivos
     */
    public Set<String> listFiles() {
        return new HashSet<>(existingFiles);
    }
}
