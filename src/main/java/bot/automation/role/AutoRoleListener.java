package bot.automation.role;

import bot.core.BotListener;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

public class AutoRoleListener extends BotListener {

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (!event.getComponent().getId().equals("autorole"))
            return;
        bot.getAutoroleManager().autorole(event);
    }

}
