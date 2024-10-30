package bot.command.core;

import bot.config.GuildContext;
import bot.core.Bot;
import bot.exceptions.CommandCheckException;
import bot.view.CommandCheckExceptionView;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;

public abstract class CommandAction {

    protected SlashCommandInteractionEvent interaction;
    protected Bot bot;
    private GuildContext context;

    public void setInteraction(SlashCommandInteractionEvent interaction) {
        this.interaction = interaction;
    }

    public SlashCommandInteractionEvent getInteraction() {
        return interaction;
    }

    public void setBot(Bot bot){
        this.bot = bot;
    }

    public GuildContext context() {
        if(context == null)
            this.context = bot.getConfigurationManager().getContext(interaction.getGuild());
        return this.context;
    }

    public ReplyCallbackAction replyException(CommandCheckException exception){
        CommandCheckExceptionView  view = new CommandCheckExceptionView(exception);
        return interaction.replyEmbeds(view.toEmbed());
    }

    public ReplyCallbackAction replyException(Throwable exception){
        return replyException(new CommandCheckException(exception, this));
    }

    public ReplyCallbackAction replyDefault(){
        return interaction.reply("Done !").setEphemeral(true);
    }

    public void check() throws CommandCheckException {

    }
}
