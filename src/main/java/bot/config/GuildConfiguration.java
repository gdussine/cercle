package bot.config;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import net.dv8tion.jda.api.entities.Guild;

@Entity
public class GuildConfiguration {

    @Id
    private String guild;

    private String system;
    private String log;
    private String autoVoice;

    private String member;

    public GuildConfiguration() {

    }

    public String getGuild() {
        return guild;
    }

    public void setGuild(String guild) {
        this.guild = guild;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getAutoVoice() {
        return autoVoice;
    }

    public void setAutoVoice(String autoVoice) {
        this.autoVoice = autoVoice;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public static GuildConfiguration createDefault(Guild guild) {
        GuildConfiguration result = new GuildConfiguration();
        result.setGuild(guild.getId());
        result.setLog(guild.getDefaultChannel().getId());
        result.setAutoVoice(guild.getDefaultChannel().getId());
        result.setSystem(guild.getDefaultChannel().getId());
        result.setMember(guild.getPublicRole().getId());
        return result;
    }

}
