package bot.command.impl;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandOption;
import bot.command.core.CommandAction;
import net.dv8tion.jda.api.Permission;

@CommandModule(name = "autorole", permission = Permission.ADMINISTRATOR)
public class AutoRoleAction extends CommandAction {

    @CommandDescription("Print default template")
    public void template(@CommandOption(name = "id", description = "Autorole message id", required = false) String id){
        if(id == null)
            bot.getAutoroleManager().defaultTemplate(interaction);
        else
            interaction.getChannel().retrieveMessageById(id).submit().thenAccept(msg ->{
                bot.getAutoroleManager().template(interaction, msg);
            });
    }

    @CommandDescription("Transform a text message into autorole menu")
    public void transform(@CommandOption(name = "id", description = "Autorole message id") String id){
        interaction.getChannel().retrieveMessageById(id).submit().thenAccept(msg ->{
            bot.getAutoroleManager().transform(interaction, msg);
        });
    }

}
