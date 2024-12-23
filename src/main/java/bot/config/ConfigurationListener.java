package bot.config;

import java.util.List;

import bot.core.BotListener;
import bot.exceptions.GuildConfigurationException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;

public class ConfigurationListener extends BotListener {


    @Override
    public void onGuildReady(GuildReadyEvent event) {
        ConfigurationManager manager = bot.getConfigurationManager();
        Guild guild = event.getGuild();
        GuildConfiguration configuration = manager.getConfiguration(guild);
        List<GuildConfigurationException> exceptions = manager.checkConfiguration(configuration, guild);
        if (exceptions.size() == 0) {
            manager.createContext(configuration);
            manager.logInfo("Guild %s configured".formatted(event.getGuild().getName()), guild);
        } else {
            manager.logWarn("Guild %s not configured : %d errors".formatted(event.getGuild().getName(),
                    exceptions.size()), guild);
        }
    }

}
