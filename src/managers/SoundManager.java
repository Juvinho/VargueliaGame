package managers;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class SoundManager {
    private static final float VOLUME = -10.0f; // dB
    
    public static void playSound(String resourcePath) {
        new Thread(() -> {
            try {
                InputStream audioSrc = SoundManager.class.getResourceAsStream(resourcePath);
                if (audioSrc == null) {
                    System.err.println("Som não encontrado: " + resourcePath);
                    return;
                }
                
                InputStream bufferedIn = new BufferedInputStream(audioSrc);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
                
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                
                // Ajustar volume
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(VOLUME);
                
                clip.start();
                
                // Aguardar conclusão
                Thread.sleep(clip.getMicrosecondLength() / 1000);
                clip.close();
                audioStream.close();
            } catch (Exception e) {
                System.err.println("Erro ao tocar som: " + e.getMessage());
            }
        }).start();
    }
    
    public static void playSoundBlocking(String resourcePath) {
        try {
            InputStream audioSrc = SoundManager.class.getResourceAsStream(resourcePath);
            if (audioSrc == null) {
                System.err.println("Som não encontrado: " + resourcePath);
                return;
            }
            
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(VOLUME);
            
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000);
            clip.close();
            audioStream.close();
        } catch (Exception e) {
            System.err.println("Erro ao tocar som: " + e.getMessage());
        }
    }
}
