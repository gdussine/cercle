package bot.command.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import bot.command.impl.DatabaseAction;
import bot.config.ConfigurationReflecter;
import bot.core.Bot;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;

public enum CommandAutoCompleters {

    CONFIG_LABEL(interaction->{
        List<String> labels = ConfigurationReflecter.getInstance().getLabels();
        List<Command.Choice> options = labels.stream()
            .filter(name -> name.startsWith(interaction.getFocusedOption().getValue()))
            .limit(20)
            .map(name -> new Command.Choice(name, name))
            .collect(Collectors.toList());
        interaction.replyChoices(options).queue();
    }),
    LIST_TYPE(interaction ->{
    	List<Command.Choice> options = new ArrayList<>();
    	Arrays.stream(DatabaseAction.LIST_TYPE).forEach(type ->{
    		options.add(new Command.Choice(type.getSimpleName(), type.getSimpleName()));
    	});
    	interaction.replyChoices(options).queue();
    }),
    OPEN_EVENT(interaction ->{
    	List<Command.Choice> options = new ArrayList<>();
    	Bot.getInstance().getInHouseManager().getRepository().getOpenGuildEvents(interaction.getGuild()).forEach(event ->{
    		options.add(new Command.Choice(event.toString(), event.getId()));
    	});
    	interaction.replyChoices(options).queue();
    }),
    
    NONE(null);

    private Consumer<CommandAutoCompleteInteraction> completer;
    
        private CommandAutoCompleters(Consumer<CommandAutoCompleteInteraction> completer ){
            this.completer = completer;
    }

    public Consumer<CommandAutoCompleteInteraction> getCompleter() {
    	
        return completer;
    }

}
