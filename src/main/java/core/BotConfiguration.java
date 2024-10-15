package core;

public class BotConfiguration {

    private String guildId;
    
    private String token;

    private String ownerId;


    public BotConfiguration(){

    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
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
        return "BotConfiguration [guildId=" + guildId + ", token=" + token + ", ownerId=" + ownerId + "]";
    }

    public static BotConfiguration getDefault(){
        var result = new BotConfiguration();
        result.setGuildId("123456789");
        result.setOwnerId("123456789");
        result.setToken("DISCORD_API_KEY");
        return result;
    }

    
}
