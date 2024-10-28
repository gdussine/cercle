package bot.config;

public class BotConfiguration {

    private String name;
    private String token;
    private String ownerId;

    public BotConfiguration() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "BotConfiguration:" + name;
    }

    public static BotConfiguration getDefault() {
        var result = new BotConfiguration();
        result.setName("default");
        return result;
    }

}
