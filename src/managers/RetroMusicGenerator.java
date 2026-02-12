package managers;

public class RetroMusicGenerator {
    
    // Notas musicais em Hz (frequências)
    private static final int C4 = 262;
    private static final int D4 = 294;
    private static final int E4 = 330;
    private static final int F4 = 349;
    private static final int G4 = 392;
    private static final int A4 = 440;
    private static final int B4 = 494;
    private static final int C5 = 523;
    private static final int D5 = 587;
    private static final int E5 = 659;
    private static final int F5 = 698;
    private static final int G5 = 784;
    private static final int A5 = 880;
    
    // Estrutura para uma nota
    public static class Note {
        public int frequency;
        public int duration;
        
        public Note(int freq, int dur) {
            this.frequency = freq;
            this.duration = dur;
        }
    }
    
    // Música intro do jogo (1986 style)
    public static void playIntroMusic() {
        new Thread(() -> {
            Note[] melody = {
                new Note(E4, 200),
                new Note(G4, 200),
                new Note(A4, 200),
                new Note(B4, 200),
                new Note(C5, 300),
                
                new Note(0, 100),  // Pausa
                
                new Note(C5, 150),
                new Note(B4, 150),
                new Note(A4, 200),
                new Note(G4, 200),
                new Note(A4, 200),
                
                new Note(0, 100),
                
                new Note(E4, 200),
                new Note(G4, 200),
                new Note(A4, 200),
                new Note(B4, 200),
                new Note(C5, 300),
                
                new Note(0, 100),
                
                new Note(E5, 250),
                new Note(D5, 250),
                new Note(C5, 500),
            };
            
            playMelody(melody);
        }).start();
    }
    
    // Música de menu (loopável)
    public static void playMenuMusic() {
        new Thread(() -> {
            Note[] melody = {
                new Note(A4, 150),
                new Note(G4, 150),
                new Note(F4, 150),
                new Note(G4, 150),
                new Note(A4, 300),
                
                new Note(0, 100),
                
                new Note(A4, 150),
                new Note(G4, 150),
                new Note(F4, 150),
                new Note(G4, 150),
                new Note(A4, 300),
                
                new Note(0, 100),
                
                new Note(B4, 150),
                new Note(C5, 150),
                new Note(C5, 300),
                new Note(B4, 150),
                new Note(A4, 450),
            };
            
            // Tocar 2x
            playMelody(melody);
        }).start();
    }
    
    // Bip de seleção com pich mais alto
    public static void playSelectSound() {
        new Thread(() -> {
            Note[] melody = {
                new Note(800, 75),
                new Note(0, 25),
                new Note(1000, 75),
            };
            playMelody(melody);
        }).start();
    }
    
    // Bip de confirmar (som satisfatório)
    public static void playConfirmSound() {
        new Thread(() -> {
            Note[] melody = {
                new Note(523, 100),
                new Note(659, 100),
                new Note(784, 200),
            };
            playMelody(melody);
        }).start();
    }
    
    // Som de erro (3 bips descendentes)
    public static void playErrorSound() {
        new Thread(() -> {
            Note[] melody = {
                new Note(600, 100),
                new Note(0, 50),
                new Note(500, 100),
                new Note(0, 50),
                new Note(400, 150),
            };
            playMelody(melody);
        }).start();
    }
    
    // Tocar sequência de notas
    private static void playMelody(Note[] notes) {
        for (Note note : notes) {
            if (note.frequency == 0) {
                // Pausa
                try {
                    Thread.sleep(note.duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                // Tocar nota
                RetroSoundGenerator.playBeepBlocking(note.frequency, note.duration);
            }
        }
    }
}
