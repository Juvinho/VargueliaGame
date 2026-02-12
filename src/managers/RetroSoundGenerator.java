package managers;

import javax.sound.sampled.*;

public class RetroSoundGenerator {
    
    // Gerar bip simples (sine wave) - estilo 8-bit/retro
    public static void playBeep(int frequency, int duration) {
        new Thread(() -> {
            try {
                byte[] buf = generateBeep(frequency, duration);
                AudioFormat af = new AudioFormat(44100, 16, 1, true, false);
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
                SourceDataLine sdl = (SourceDataLine) AudioSystem.getLine(info);
                sdl.open(af);
                sdl.start();
                sdl.write(buf, 0, buf.length);
                sdl.stop();
                sdl.close();
            } catch (Exception e) {
                System.err.println("Erro ao gerar bip: " + e.getMessage());
            }
        }).start();
    }
    
    // Bloquear (aguarda bip terminar)
    public static void playBeepBlocking(int frequency, int duration) {
        try {
            byte[] buf = generateBeep(frequency, duration);
            AudioFormat af = new AudioFormat(44100, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
            SourceDataLine sdl = (SourceDataLine) AudioSystem.getLine(info);
            sdl.open(af);
            sdl.start();
            sdl.write(buf, 0, buf.length);
            sdl.drain();
            sdl.stop();
            sdl.close();
        } catch (Exception e) {
            System.err.println("Erro ao gerar bip: " + e.getMessage());
        }
    }
    
    // Gerar sequência de bips (padrão "startup" dos anos 80)
    public static void playStartupSound() {
        new Thread(() -> {
            try {
                // Sequência clássica de startup
                playBeepBlocking(440, 150);  // Lá
                Thread.sleep(100);
                playBeepBlocking(550, 150);  // Dó#
                Thread.sleep(100);
                playBeepBlocking(660, 300);  // Mi
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    // Bip de seleção (menu)
    public static void playSelectBeep() {
        playBeep(800, 100);
    }
    
    // Bip de erro
    public static void playErrorBeep() {
        playBeep(300, 200);
    }
    
    private static byte[] generateBeep(int frequency, int duration) {
        int sampleRate = 44100;
        int samples = sampleRate * duration / 1000;
        byte[] buf = new byte[samples * 2]; // 16-bit = 2 bytes por sample
        
        for (int i = 0; i < samples; i++) {
            double angle = 2.0 * Math.PI * frequency * i / sampleRate;
            double sample = Math.sin(angle) * 0.3; // Volume: 30%
            
            // Converter para 16-bit PCM (little-endian)
            short value = (short) (sample * 32767);
            int idx = i * 2;
            buf[idx] = (byte) (value & 0xFF);
            buf[idx + 1] = (byte) ((value >> 8) & 0xFF);
        }
        
        return buf;
    }
}
