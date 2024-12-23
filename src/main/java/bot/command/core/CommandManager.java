package bot.command.core;

import bot.command.annotations.CommandOption;
import bot.core.BotManager;
import bot.exceptions.CommandCheckException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;

public class CommandManager extends BotManager {

    private final static String COMMAND_PACKAGE = "bot.command.impl";
    private CommandMap map;

    public CommandManager() {
        super(new CommandListener());
        this.map = CommandMap.create(COMMAND_PACKAGE);
    }

    public void init(Guild guild) {
        guild.updateCommands().addCommands(map.getCommands()).queue(x -> {
            this.logInfo("Initialization of commands (%d commands available) ".formatted(x.size()), guild);
        });
    }

    public void autocomplete(CommandAutoCompleteInteraction interaction){
        CommandEntry entry = map.get(interaction.getName(), interaction.getSubcommandName());
        CommandOption option = entry.getOptions().stream().filter(x->x.name().equals(interaction.getFocusedOption().getName())).findAny().orElse(null);
        option.autocomplete().getCompleter().accept(interaction);
        
    }

    public void execute(SlashCommandInteractionEvent event) {
        CommandEntry entry = map.get(event.getName(), event.getSubcommandName());
        CommandAction action = entry.toCommandAction(bot, event);
        Object[] parameters = entry.toCommandParameters(event);
        try {
            action.check();
            entry.getMethod().invoke(action, parameters);
            this.logInfo("%s run %s".formatted(event.getUser().getEffectiveName(), event.getCommandString()),event.getGuild());
        } catch (CommandCheckException e) {
            action.replyException(e);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
