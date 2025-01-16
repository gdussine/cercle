package bot.command.impl;

import java.util.List;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandOption;
import bot.command.core.CommandAction;
import bot.command.core.CommandAutoCompleters;
import bot.config.GuildConfiguration;
import bot.exceptions.GuildConfigurationException;
import bot.view.impl.GuildConfigurationCheckView;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

@CommandModule(name = "admin", permission = { Permission.ADMINISTRATOR })
public class AdminAction extends CommandAction {

    @CommandDescription("Refresh the guild configuration")
    public void config(
        @CommandOption(name="label", description = "Configuration label", autocomplete = CommandAutoCompleters.CONFIG_LABEL) String label, 
        @CommandOption(name="value", description = "Configuration value") String value )
     {
        Guild guild = interaction.getGuild();
        GuildConfiguration configuration = bot.getConfigurationManager().getConfiguration(guild);
        bot.getConfigurationManager().editConfiguration(guild, configuration, label, value);
        List<GuildConfigurationException> exceptions = bot.getConfigurationManager().checkConfiguration(configuration, guild);
        GuildConfigurationCheckView view = new GuildConfigurationCheckView(guild, exceptions);
        if(exceptions.size() == 0){
            bot.getConfigurationManager().createContext(configuration);
        }
        interaction.replyEmbeds(view.toEmbed()).submit();
    }

    @CommandDescription("PING request to Discord API")
    public void ping(
            @CommandOption(name = "ephemeral", description = "Visible by all", required = false) Boolean ephemeral) {
        interaction.getJDA().getRestPing().submit().thenAccept(x -> {
            interaction.reply("Discord: %d ms".formatted(x)).setEphemeral(ephemeral == null ? false : ephemeral)
                    .submit();
        });
    }

    @CommandDescription("Clean amount of message from channel")
    public void clean(@CommandOption(name = "amount", description = "Amount of message") Integer amount) {
        TextChannel channel = interaction.getChannel().asTextChannel();
        channel.getHistory().retrievePast(amount).submit().thenAccept(msgs -> channel.purgeMessages(msgs));
    }

}
