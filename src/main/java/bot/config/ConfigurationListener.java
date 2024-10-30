package bot.config;

import bot.core.BotListener;
import bot.exceptions.GuildConfigurationException;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;

public class ConfigurationListener extends BotListener {

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        try {
            bot.getConfigurationManager().configure(event.getGuild());
            bot.getConfigurationManager().getLog().info("Guild %s configurated".formatted(event.getGuild().getName()));
        } catch (GuildConfigurationException e){
            bot.getConfigurationManager().getLog().error("Guild %s not configurated : %s".formatted(event.getGuild().getName(), e.getCause().getMessage()));
        }
    }
    

}
