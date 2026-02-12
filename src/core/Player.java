package core;

public enum Player {
    NIUWE("NIUWË"),
    ELLA("ELLA");

    private final String displayName;

    Player(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
