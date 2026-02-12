package managers;

import javax.sound.sampled.LineUnavailableException;

/**
 * Gerenciador de música em loop com temas dinâmicos.
 * Trabalha em conjunto com RetroSoundGenerator para síntese de áudio.
 */
public class MusicManager {
    public enum MusicTheme {
        EXPLORATION("Exploração"),
        TENSION("Tensão"),
        DRAMATIC("Dramático"),
        MENU("Menu");
        
        public final String description;
        MusicTheme(String desc) {
            this.description = desc;
        }
    }
    
    private static MusicManager instance;
    private MusicTheme currentTheme = MusicTheme.MENU;
    private Thread musicThread;
    private volatile boolean stopMusic = false;
    private volatile boolean audioEnabled = true;
    
    private MusicManager() {}
    
    public static MusicManager getInstance() {
        if (instance == null) {
            instance = new MusicManager();
        }
        return instance;
    }
    
    /**
     * Inicia tema musical em loop contínuo
     */
    public void startTheme(MusicTheme theme) {
        if (!audioEnabled) return;
        
        // Se já toca um tema, parar antes de começar outro
        if (musicThread != null && musicThread.isAlive()) {
            stopMusic = true;
            try {
                musicThread.join(1000);
            } catch (InterruptedException ignored) {}
        }
        
        currentTheme = theme;
        stopMusic = false;
        
        musicThread = new Thread(() -> {
            try {
                while (!stopMusic) {
                    playThemeOnce(theme);
                }
            } catch (Exception e) {
                System.err.println("Erro ao tocar tema: " + e.getMessage());
            }
        });
        musicThread.setDaemon(true);
        musicThread.start();
    }
    
    /**
     * Para a música de fundo
     */
    public void stopTheme() {
        stopMusic = true;
        if (musicThread != null) {
            try {
                musicThread.join(500);
            } catch (InterruptedException ignored) {}
        }
    }
    
    /**
     * Toca um tema uma única vez (chamado internamente em loop)
     */
    private void playThemeOnce(MusicTheme theme) throws InterruptedException, LineUnavailableException {
        RetroMusicGenerator.Note[] melody = switch(theme) {
            case EXPLORATION -> createExplorationMelody();
            case TENSION -> createTensionMelody();
            case DRAMATIC -> createDramaticMelody();
            case MENU -> createMenuMelody();
        };
        
        for (RetroMusicGenerator.Note note : melody) {
            if (stopMusic) break;
            RetroSoundGenerator.playBeepBlocking(note.frequency, note.duration);
        }
    }
    
    /**
     * Tema de exploração: misterioso, tom menor, pausas
     */
    private RetroMusicGenerator.Note[] createExplorationMelody() {
        return new RetroMusicGenerator.Note[] {
            new RetroMusicGenerator.Note(329, 400),  // E4
            new RetroMusicGenerator.Note(0, 100),
            new RetroMusicGenerator.Note(246, 400),  // B3
            new RetroMusicGenerator.Note(0, 100),
            new RetroMusicGenerator.Note(196, 500),  // G3
            new RetroMusicGenerator.Note(0, 150),
            new RetroMusicGenerator.Note(164, 400),  // E3
            new RetroMusicGenerator.Note(0, 200),
            new RetroMusicGenerator.Note(440, 300),  // A4
            new RetroMusicGenerator.Note(0, 100),
            new RetroMusicGenerator.Note(392, 300),  // G4
            new RetroMusicGenerator.Note(0, 100),
            new RetroMusicGenerator.Note(329, 400),  // E4
            new RetroMusicGenerator.Note(0, 300)
        };
    }
    
    /**
     * Tema de tensão: notas altas, repetitivas, alerta
     */
    private RetroMusicGenerator.Note[] createTensionMelody() {
        return new RetroMusicGenerator.Note[] {
            new RetroMusicGenerator.Note(800, 150),
            new RetroMusicGenerator.Note(0, 50),
            new RetroMusicGenerator.Note(900, 150),
            new RetroMusicGenerator.Note(0, 50),
            new RetroMusicGenerator.Note(750, 200),
            new RetroMusicGenerator.Note(0, 100),
            new RetroMusicGenerator.Note(850, 150),
            new RetroMusicGenerator.Note(0, 50),
            new RetroMusicGenerator.Note(950, 150),
            new RetroMusicGenerator.Note(0, 50),
            new RetroMusicGenerator.Note(700, 250),
            new RetroMusicGenerator.Note(0, 150),
            new RetroMusicGenerator.Note(600, 300),
            new RetroMusicGenerator.Note(0, 200)
        };
    }
    
    /**
     * Tema dramático: progressão ascendente com climax
     */
    private RetroMusicGenerator.Note[] createDramaticMelody() {
        return new RetroMusicGenerator.Note[] {
            new RetroMusicGenerator.Note(261, 300),  // C4
            new RetroMusicGenerator.Note(0, 100),
            new RetroMusicGenerator.Note(293, 300),  // D4
            new RetroMusicGenerator.Note(0, 100),
            new RetroMusicGenerator.Note(329, 300),  // E4
            new RetroMusicGenerator.Note(0, 100),
            new RetroMusicGenerator.Note(392, 300),  // G4
            new RetroMusicGenerator.Note(0, 100),
            new RetroMusicGenerator.Note(523, 500),  // C5 (climax)
            new RetroMusicGenerator.Note(0, 200),
            new RetroMusicGenerator.Note(466, 400),  // A#4
            new RetroMusicGenerator.Note(0, 150),
            new RetroMusicGenerator.Note(392, 500),  // G4
            new RetroMusicGenerator.Note(0, 300)
        };
    }
    
    /**
     * Tema de menu: amigável e melódico
     */
    private RetroMusicGenerator.Note[] createMenuMelody() {
        return new RetroMusicGenerator.Note[] {
            new RetroMusicGenerator.Note(440, 250),  // A4
            new RetroMusicGenerator.Note(0, 100),
            new RetroMusicGenerator.Note(523, 250),  // C5
            new RetroMusicGenerator.Note(0, 100),
            new RetroMusicGenerator.Note(587, 250),  // D5
            new RetroMusicGenerator.Note(0, 100),
            new RetroMusicGenerator.Note(523, 250),  // C5
            new RetroMusicGenerator.Note(0, 150),
            new RetroMusicGenerator.Note(440, 300),  // A4
            new RetroMusicGenerator.Note(0, 200)
        };
    }
    
    /**
     * Efeito sonoro: tecla pressionada
     */
    public void playSoundKeyPress() {
        if (!audioEnabled) return;
        new Thread(() -> {
            try {
                RetroSoundGenerator.playBeepBlocking(800, 50);
            } catch (Exception e) {
                System.err.println("Erro ao tocar som de tecla: " + e.getMessage());
            }
        }).start();
    }
    
    /**
     * Efeito sonoro: erro/glitch
     */
    public void playSoundError() {
        if (!audioEnabled) return;
        new Thread(() -> {
            try {
                RetroSoundGenerator.playBeepBlocking(400, 150);
                RetroSoundGenerator.playBeepBlocking(300, 150);
            } catch (Exception e) {
                System.err.println("Erro ao tocar som de erro: " + e.getMessage());
            }
        }).start();
    }
    
    /**
     * Efeito sonoro: confirmação
     */
    public void playSoundConfirm() {
        if (!audioEnabled) return;
        new Thread(() -> {
            try {
                RetroSoundGenerator.playBeepBlocking(523, 100);
                RetroSoundGenerator.playBeepBlocking(659, 100);
                RetroSoundGenerator.playBeepBlocking(784, 100);
            } catch (Exception e) {
                System.err.println("Erro ao tocar som de confirmação: " + e.getMessage());
            }
        }).start();
    }
    
    /**
     * Efeito sonoro: boot do sistema
     */
    public void playSoundBoot() {
        if (!audioEnabled) return;
        new Thread(() -> {
            try {
                RetroSoundGenerator.playBeepBlocking(1000, 100);
                Thread.sleep(50);
                RetroSoundGenerator.playBeepBlocking(800, 100);
                Thread.sleep(50);
                RetroSoundGenerator.playBeepBlocking(600, 150);
            } catch (Exception e) {
                System.err.println("Erro ao tocar som de boot: " + e.getMessage());
            }
        }).start();
    }
    
    /**
     * Configura áudio globalmente
     */
    public void setAudioEnabled(boolean enabled) {
        this.audioEnabled = enabled;
        if (!enabled) {
            stopTheme();
        }
    }
    
    public boolean isAudioEnabled() {
        return audioEnabled;
    }
    
    public MusicTheme getCurrentTheme() {
        return currentTheme;
    }
}
