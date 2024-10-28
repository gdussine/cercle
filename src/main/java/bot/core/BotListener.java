package bot.core;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

public abstract class BotListener extends ListenerAdapter{

    protected Bot bot;

    public BotListener listen(Bot bot){
        this.bot = bot;
        this.bot.listen(this);
        return this;
    }

}
