package core;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    public String id;
    public Player player;
    public String text;
    public List<SceneOption> options;
    public boolean isGlitched; // Para efeitos especiais de Melaninne
    
    public Scene(String id, Player player, String text) {
        this.id = id;
        this.player = player;
        this.text = text;
        this.options = new ArrayList<>();
        this.isGlitched = false;
    }
    
    public void addOption(SceneOption option) {
        this.options.add(option);
    }
    
    public boolean hasOptions() {
        return !this.options.isEmpty();
    }
    
    public SceneOption getOption(String key) {
        for (SceneOption opt : this.options) {
            if (opt.key.equals(key)) {
                return opt;
            }
        }
        return null;
    }
}
