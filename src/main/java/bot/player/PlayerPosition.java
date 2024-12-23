package bot.player;

public enum PlayerPosition {

    TOP("Top Lane"), JNG("Jungle"), MID("Mid Lane"), BOT("Bot Lane"), SUP("Support"), FIL("Fill");

    private String label;

    
    public String getLabel() {
        return label;
    }

    private PlayerPosition(String label) {
        this.label = label;
    }

}
