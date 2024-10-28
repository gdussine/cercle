package bot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BotManager {

    protected BotListener listener;
    protected Bot bot;
    protected Logger log;

    public BotManager(BotListener listener){
        this.log = LoggerFactory.getLogger(getClass());
        this.listener = listener;
    }

    public void register(Bot bot){
        this.bot = bot;
        this.listener.listen(bot);
    }

    public Bot getBot() {
        return bot;
    }

    public BotListener getListener() {
        return listener;
    }

    public Logger getLog() {
        return log;
    }

}
