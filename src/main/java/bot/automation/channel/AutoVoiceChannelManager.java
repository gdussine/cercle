package bot.automation.channel;

import bot.config.GuildContext;
import bot.core.BotManager;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;

public class AutoVoiceChannelManager extends BotManager {

    public AutoVoiceChannelManager() {
        super(new AutoVoiceChannelListener());
    }

    public void autocreate(GuildVoiceUpdateEvent event) {
    	GuildContext context = bot.getContext(event.getGuild().getId());
        event.getChannelJoined().createCopy()
                .setName(context.getAutoVoiceName().replace("{user}", event.getMember().getEffectiveName()))
                .submit().thenCompose(channel -> {
                    VoiceChannel voiceChannel = (VoiceChannel) channel;
                    return event.getGuild().moveVoiceMember(event.getMember(), voiceChannel).submit();
                });
    }

    public void autoremove(GuildVoiceUpdateEvent event) {
        event.getChannelLeft().delete().submit();
    }

}
