package bot.automation.welcome;

import bot.core.BotListener;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class GateListener extends BotListener{

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        bot.getGateManager().welcomeNotify(event);
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        bot.getGateManager().welcomeAction(event);
    }

}
