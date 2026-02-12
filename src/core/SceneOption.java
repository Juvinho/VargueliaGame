package core;

public class SceneOption {
    public String key;      // "1", "2", "A", etc.
    public String text;     // Texto exibido para o jogador
    public String nextId;   // ID da próxima cena
    public String consequence; // Descrição da consequência (para logging)
    
    public SceneOption(String key, String text, String nextId) {
        this.key = key;
        this.text = text;
        this.nextId = nextId;
        this.consequence = null;
    }
    
    public SceneOption(String key, String text, String nextId, String consequence) {
        this.key = key;
        this.text = text;
        this.nextId = nextId;
        this.consequence = consequence;
    }
    
    @Override
    public String toString() {
        return key + ") " + text;
    }
}
