package bot.config;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;

public class GuildContext {

    private Guild guild;

    private TextChannel system;
    private TextChannel log;
    private TextChannel inHouseEvent;
    private TextChannel inHouseGame;
    private TextChannel inHouseSeason;

    private VoiceChannel autoVoice;

    private Role member;
    
    private String autoVoiceName;

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
    
    public TextChannel getInHouseEvent() {
		return inHouseEvent;
	}
    
    public void setInHouseEvent(String id) {
		this.inHouseEvent = guild.getTextChannelById(id);
	}

    public TextChannel getInHouseGame() {
        return inHouseGame;
    }

    public void setInHouseGame(String id) {
        this.inHouseGame = guild.getTextChannelById(id);
    }

    public TextChannel getInHouseSeason() {
        return inHouseSeason;
    }

    public void setInHouseSeason(String id) {
        this.inHouseSeason = guild.getTextChannelById(id);
    }

    public Role getMember() {
        return member;
    }

    public void setMember(String id) {
        this.member = guild.getRoleById(id);
    }
    
    public String getAutoVoiceName() {
		return autoVoiceName;
	}
    
    public void setAutoVoiceName(String autoVoiceName) {
		this.autoVoiceName = autoVoiceName;
	}

}
