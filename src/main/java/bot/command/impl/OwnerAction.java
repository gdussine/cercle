package bot.command.impl;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandOption;
import bot.command.core.CommandAction;
import bot.exceptions.CommandCheckException;
import bot.exceptions.GuildConfigurationException;
import net.dv8tion.jda.api.Permission;

@CommandModule(name = "owner", permission = { Permission.MESSAGE_SEND })
public class OwnerAction extends CommandAction {

    @Override
    public void check() throws CommandCheckException {
        if (!bot.getConfiguration().getOwnerId().equals(interaction.getUser().getId()))
            throw new CommandCheckException("You are not the owner of the bot.", this);
    }

    @CommandDescription("PING request to Discord API")
    public void ping(
            @CommandOption(name = "ephemeral", description = "Visible by all", required = false) Boolean ephemeral) {
        interaction.getJDA().getRestPing().submit().thenAccept(x -> {
            interaction.reply("Discord: %d ms".formatted(x)).setEphemeral(ephemeral == null ? false : ephemeral)
                    .submit();
        });
    }

    @CommandDescription("Shutdown the bot")
    public void stop() {
        interaction.reply("Bye!").submit().thenAccept(x -> {
            bot.stop();
        });
    }

    @CommandDescription("Refresh the guild configuration")
    public void config(){
        try {
            bot.getConfigurationManager().configure(interaction.getGuild());
            interaction.reply("Configuration reloaded").submit();
        } catch (GuildConfigurationException e) {
            replyException(e).submit();
        }

    }
}
