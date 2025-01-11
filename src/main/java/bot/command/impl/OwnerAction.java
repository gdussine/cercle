package bot.command.impl;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.core.CommandAction;
import bot.exceptions.CommandCheckException;
import net.dv8tion.jda.api.Permission;

@CommandModule(name = "owner", permission = {Permission.ADMINISTRATOR})
public class OwnerAction extends CommandAction {

    @Override
    public void check() throws CommandCheckException {
        if (!bot.getConfiguration().getOwnerId().equals(interaction.getUser().getId()))
            throw new CommandCheckException("You are not the owner of the bot.", this);
    }

    @CommandDescription("Shutdown the bot")
    public void stop() {
        interaction.reply("Bye!").submit().thenAccept(x -> {
            bot.stop();
        });
    }
}
