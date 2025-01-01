package bot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import bot.config.GuildContext;
import bot.view.impl.LogView;
import net.dv8tion.jda.api.entities.Guild;

public abstract class BotManager {

    protected BotListener listener;
    protected Bot bot;
    private Logger log;

    public BotManager(BotListener listener) {
        this.log = LoggerFactory.getLogger(getClass());
        this.listener = listener;
    }

    public void register(Bot bot) {
        this.bot = bot;
        this.listener.listen(bot);
    }

    public Bot getBot() {
        return bot;
    }

    public BotListener getListener() {
        return listener;
    }

    private void logGuild(Level level, String msg, Guild guild) {
        if (guild == null || bot == null)
            return;
        GuildContext context = bot.getConfigurationManager().getContext(guild);
        if(context == null)
            return;
        if(context.getLog() == null)
            return;
        LogView view = new LogView(level, this.getClass().getSimpleName(), msg);
        context.getLog().sendMessageEmbeds(view.toEmbed()).queue();
    }

    public void logInfo(String msg, Guild guild) {
        log.info(msg);
        logGuild(Level.INFO, msg, guild);
    }

    public void logWarn(String msg, Guild guild) {
        log.warn(msg);
        logGuild(Level.WARN, msg, guild);
    }

    public void logError(String msg, Guild guild) {
        log.error(msg);
        logGuild(Level.ERROR, msg, guild);
    }

    public void logError(String msg, Throwable cause, Guild guild) {
        log.error(msg, cause);
        logGuild(Level.ERROR, msg, guild);
    }

}
