package game;

public class OptionsScreen {
    private GameWindow window;
    private int selectedOption = 0;
    
    // Estados das opções
    private int musicVolume = 2;      // 0=mudo, 1=baixo, 2=médio, 3=alto
    private int sfxVolume = 2;        // 0=mudo, 1=baixo, 2=médio, 3=alto
    private int textSpeed = 1;        // 0=instantâneo, 1=rápido, 2=lento
    private boolean glitchesEnabled = true;
    private int brightness = 2;       // 0=escuro, 1=normal, 2=brilho máximo
    
    private String[] volumeLabels = {"MUDO", "BAIXO", "MÉDIO", "ALTO"};
    private String[] speedLabels = {"INSTANTÂNEO", "RÁPIDO", "LENTO"};
    private String[] brightLabels = {"ESCURO", "NORMAL", "BRILHO MÁX"};
    
    private String[] optionNames = {
        "VOLUME DA MÚSICA",
        "VOLUME DE EFEITOS",
        "VELOCIDADE DO TEXTO",
        "GLITCHES VISUAIS",
        "BRILHO DO TERMINAL"
    };
    
    public OptionsScreen(GameWindow window) {
        this.window = window;
    }
    
    public void display() {
        window.clearText();
        
        window.appendText("\n\n", "default");
        window.appendText("════════════════════════════════════════════════════════════════\n", "default");
        window.appendText("║ CONFIGURAÇÕES                                                 ║\n", "default");
        window.appendText("════════════════════════════════════════════════════════════════\n\n", "default");
        
        // Opção 1: Volume de Música
        displayOption(0, "VOLUME DA MÚSICA", getVolumeLabel(musicVolume));
        
        // Opção 2: Volume de Efeitos
        displayOption(1, "VOLUME DE EFEITOS", getVolumeLabel(sfxVolume));
        
        // Opção 3: Velocidade do Texto
        displayOption(2, "VELOCIDADE DO TEXTO", speedLabels[textSpeed]);
        
        // Opção 4: Glitches Visuais
        displayOption(3, "GLITCHES VISUAIS", glitchesEnabled ? "LIGADO" : "DESLIGADO");
        
        // Opção 5: Brilho
        displayOption(4, "BRILHO DO TERMINAL", brightLabels[brightness]);
        
        window.appendText("\n", "default");
        window.appendText("════════════════════════════════════════════════════════════════\n", "default");
        window.appendText("Use ", "default");
        window.appendText("↑/↓", "cyan");
        window.appendText(" para navegar | ", "default");
        window.appendText("← →", "cyan");
        window.appendText(" para ajustar | ", "default");
        window.appendText("ESC", "cyan");
        window.appendText(" para voltar\n", "default");
        
        window.setWaitingForInput(true);
    }
    
    private void displayOption(int index, String name, String value) {
        if (index == selectedOption) {
            window.appendText("  ", "default");
            window.appendText("►", "cyan");
            window.appendText(" ");
            window.appendText(name, "cyan");
            window.appendText(" ".repeat(Math.max(0, 35 - name.length())), "default");
            window.appendText("[", "yellow");
            window.appendText(value, "yellow");
            window.appendText("] ", "yellow");
            window.appendText("◄\n", "cyan");
        } else {
            window.appendText("    ");
            window.appendText(name);
            window.appendText(" ".repeat(Math.max(0, 35 - name.length())));
            window.appendText("[", "default");
            window.appendText(value, "default");
            window.appendText("]\n", "default");
        }
        window.appendText("\n", "default");
    }
    
    public void handleUpArrow() {
        selectedOption = (selectedOption - 1 + optionNames.length) % optionNames.length;
        display();
    }
    
    public void handleDownArrow() {
        selectedOption = (selectedOption + 1) % optionNames.length;
        display();
    }
    
    public void handleLeftArrow() {
        switch (selectedOption) {
            case 0:  // Música
                musicVolume = Math.max(0, musicVolume - 1);
                break;
            case 1:  // Efeitos
                sfxVolume = Math.max(0, sfxVolume - 1);
                break;
            case 2:  // Velocidade
                textSpeed = Math.max(0, textSpeed - 1);
                break;
            case 3:  // Glitches
                glitchesEnabled = !glitchesEnabled;
                break;
            case 4:  // Brilho
                brightness = Math.max(0, brightness - 1);
                break;
        }
        display();
    }
    
    public void handleRightArrow() {
        switch (selectedOption) {
            case 0:  // Música
                musicVolume = Math.min(3, musicVolume + 1);
                break;
            case 1:  // Efeitos
                sfxVolume = Math.min(3, sfxVolume + 1);
                break;
            case 2:  // Velocidade
                textSpeed = Math.min(2, textSpeed + 1);
                break;
            case 3:  // Glitches
                glitchesEnabled = !glitchesEnabled;
                break;
            case 4:  // Brilho
                brightness = Math.min(2, brightness + 1);
                break;
        }
        display();
    }
    
    private String getVolumeLabel(int vol) {
        return volumeLabels[Math.min(vol, 3)];
    }
    
    public int getMusicVolume() { return musicVolume; }
    public int getSfxVolume() { return sfxVolume; }
    public int getTextSpeed() { return textSpeed; }
    public boolean isGlitchesEnabled() { return glitchesEnabled; }
    public int getBrightness() { return brightness; }
}
