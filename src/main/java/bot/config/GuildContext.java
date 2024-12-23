package bot.config;

import java.lang.reflect.Method;

import bot.core.Bot;
import bot.exceptions.GuildConfigurationException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;

public class GuildContext {

    private Guild guild;

    private TextChannel system;
    private TextChannel log;

    private VoiceChannel autoVoice;

    private Role member;

    public GuildContext(Guild guild) {
        this.guild = guild;
    }

    public Guild getGuild() {
        return guild;
    }

    public TextChannel getSystem() {
        return system;
    }

    public void setSystem(String id) {
        this.system = guild.getTextChannelById(id);
    }

    public TextChannel getLog() {
        return log;
    }

    public void setLog(String id) {
        this.log = guild.getTextChannelById(id);
    }

    public VoiceChannel getAutoVoice() {
        return autoVoice;
    }

    public void setAutoVoice(String id) {
        this.autoVoice = guild.getVoiceChannelById(id);
    }

    public Role getMember() {
        return member;
    }

    public void setMember(String id) {
        this.member = guild.getRoleById(id);
    }

}
