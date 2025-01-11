package bot.view.impl;

import bot.view.EmbedView;
import net.dv8tion.jda.api.entities.UserSnowflake;

public class WelcomeSystemView extends EmbedView{;

    public WelcomeSystemView(UserSnowflake user){
        this.template.setTitle(":wave: Welcome Tasks");
        this.template.setColor(DARK_BLUE);
        this.template.appendDescription("Name: %s\n".formatted(user.getAsMention()));
        this.template.appendDescription("Birth: %s".formatted(DATETIME_FORMAT.format(user.getTimeCreated())));
    }
    
    public WelcomeSystemView disable(String verdict) {
    	this.template.appendDescription("Verdict: %s".formatted(verdict));
    	this.template.setColor(DISCORD);
    	return this;
    }
    
}
