package bot.command.autocomplete;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import bot.config.ConfigurationReflecter;
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
    NONE(null);

    private Consumer<CommandAutoCompleteInteraction> completer;
    
        private CommandAutoCompleters(Consumer<CommandAutoCompleteInteraction> completer ){
            this.completer = completer;
    }

    public Consumer<CommandAutoCompleteInteraction> getCompleter() {
        return completer;
    }

}
