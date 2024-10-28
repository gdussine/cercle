package bot.command.core;

import bot.core.BotManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class CommandManager extends BotManager {

    private final static String COMMAND_PACKAGE = "bot.command.impl";
    private CommandMap map;

    public CommandManager() {
        super(new CommandListener());
        this.map = CommandMap.create(COMMAND_PACKAGE);
    }

    public void init(Guild guild) {
        guild.updateCommands().addCommands(map.getCommands()).queue(x -> {
            this.log.info("Initialization of commands (%d commands available) ".formatted(x.size()));
        });
    }

    public void execute(SlashCommandInteractionEvent event) {
        CommandEntry entry = map.get(event.getName(), event.getSubcommandName());
        CommandAction action = entry.toCommandAction(bot, event);
        Object[] parameters = entry.toCommandParameters(event);
        try {
            action.check();
            entry.getMethod().invoke(action, parameters);
        } catch (CommandCheckException e) {
            action.replyException(e);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
