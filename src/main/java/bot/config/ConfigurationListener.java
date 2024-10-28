package bot.config;

import bot.core.BotListener;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;

public class ConfigurationListener extends BotListener {

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        bot.getConfigurationManager().loadGuild(event.getGuild());
    }
    

}
