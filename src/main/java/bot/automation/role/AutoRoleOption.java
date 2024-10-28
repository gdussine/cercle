package bot.automation.role;

public class AutoRoleOption {

    private String emoji;
    private String label;
    private String value;
    

    public AutoRoleOption(String emoji, String label, String value) {
        this.emoji = emoji;
        this.label = label;
        this.value = value;
    }

    public AutoRoleOption(){

    }

    public String getEmoji() {
        return emoji;
    }
    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    

}
