package bot.automation.channel;

import bot.config.GuildContext;
import bot.core.BotListener;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;

public class AutoVoiceChannelListener extends BotListener {

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        GuildContext context = bot.getContext(event.getGuild().getId());
        if (event.getChannelJoined() != null
                && event.getChannelJoined().getId().equals(context.getAutoVoice().getId())) {
            bot.getAutoVoiceChannelManager().autocreate(event);
        }
        if (event.getChannelLeft() != null
                && event.getChannelLeft().getType().equals(ChannelType.VOICE)
                && !event.getChannelLeft().getId().equals(context.getAutoVoice().getId())
                && event.getChannelLeft().getParentCategory().getId().equals(context.getAutoVoice().getParentCategory().getId())
                && event.getChannelLeft().getMembers().size() == 0) {
            bot.getAutoVoiceChannelManager().autoremove(event);

        }
    }

}
