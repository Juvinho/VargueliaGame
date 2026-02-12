package core;

public class GameState {
    // Jogador atual
    public Player currentPlayer;
    
    // Cena atual
    public String sceneId;
    
    // Flags do Ato 1 (Niuwë)
    public boolean niuweFoiImpulsivo;
    public boolean niuweSalvouMaisAlunos;
    public boolean niuweExplorouEstacao4;
    public boolean niuweDialogouComDasko;
    public boolean niuweLutouDiretoComSepharoth;
    public boolean niuweAjudouJorgenssenRoger;
    
    // Sistema de influência Melaninne para Ato 2 (Ella)
    public int melaninneInfluencia; // 0-100
    
    // Status final do Ato 1
    public int niuweStatusFinal; // 0 = colapso, 1 = normal, 2 = heroico
    
    // Novo: Sistema de Sanidade (Ato 2)
    public int ellaSanity = 100;  // Começa no máximo (Ella ainda intacta)
    
    // Novo: Meta-informações para sistemas avançados
    public int totalPlaythroughs = 0;  // Detecta replays
    public long totalGameplayTime = 0;  // Tempo total em milissegundos
    public int choicesMade = 0;  // Numero de decisões importantes feitas
    
    // Novo: Rastreamento de replay da memória de Niuwë
    public boolean ellaViewedNiuweMemories = false;
    public boolean ellaAccessedFileSystem = false;
    
    public GameState() {
        this.currentPlayer = Player.NIUWE;
        this.sceneId = "NIUWE_INTRO";
        
        // Flags iniciais
        this.niuweFoiImpulsivo = false;
        this.niuweSalvouMaisAlunos = false;
        this.niuweExplorouEstacao4 = false;
        this.niuweDialogouComDasko = false;
        this.niuweLutouDiretoComSepharoth = false;
        this.niuweAjudouJorgenssenRoger = false;
        
        this.melaninneInfluencia = 0;
        this.niuweStatusFinal = 1;
    }
    
    // Método para gerar password com base nas flags
    public String generatePassword() {
        StringBuilder pw = new StringBuilder();
        pw.append("NW_");
        
        if (niuweSalvouMaisAlunos) pw.append("EVA_");
        else pw.append("NoEVA_");
        
        if (niuweExplorouEstacao4) pw.append("S4_");
        else pw.append("NoS4_");
        
        if (niuweDialogouComDasko) pw.append("DLG_");
        else pw.append("NoDLG_");
        
        if (niuweLutouDiretoComSepharoth) pw.append("LUT_");
        else pw.append("NoLUT_");
        
        pw.append("ST" + niuweStatusFinal);
        
        return pw.toString();
    }
    
    // Método para restore a partir do password
    public void restoreFromPassword(String password) {
        if (password == null || !password.startsWith("NW_")) {
            return;
        }
        
        String[] parts = password.split("_");
        
        for (String part : parts) {
            if (part.equals("EVA")) niuweSalvouMaisAlunos = true;
            if (part.equals("S4")) niuweExplorouEstacao4 = true;
            if (part.equals("DLG")) niuweDialogouComDasko = true;
            if (part.equals("LUT")) niuweLutouDiretoComSepharoth = true;
            
            if (part.startsWith("ST")) {
                niuweStatusFinal = Integer.parseInt(part.substring(2));
            }
        }
    }
}
