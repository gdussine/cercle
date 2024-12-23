package bot.automation.welcome;

import bot.config.GuildContext;
import bot.core.BotManager;
import bot.view.impl.WelcomeAdminView;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

public class WelcomeManager extends BotManager {

    public WelcomeManager(){
        super(new WelcomeListener());
    }


    public void notifyAdmin(GuildMemberJoinEvent event){
        GuildContext context = bot.getContext(event.getGuild());
        WelcomeAdminView v = new WelcomeAdminView(event.getMember());
        context.getSystem().sendMessageEmbeds(v.toEmbed()).queue();
        
    }

}
