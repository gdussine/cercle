package bot.config;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class BotConfiguration {

    @Id
    private String name;

    private String discordToken;
    private String riotToken;
    private String ownerId;

    public BotConfiguration() {

    }

    public BotConfiguration(String name, String discordToken, String riotToken, String ownerId) {
        this.name = name;
        this.discordToken = discordToken;
        this.riotToken = riotToken;
        this.ownerId = ownerId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscordToken() {
        return discordToken;
    }

    public void setDiscordToken(String token) {
        this.discordToken = token;
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

    public String getRiotToken() {
        return riotToken;
    }

    public void setRiotToken(String riotToken) {
        this.riotToken = riotToken;
    }


}
