package bot.inhouse;

import bot.core.BotListener;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class InHouseListener extends BotListener{
	
	@Override
	public void onButtonInteraction(ButtonInteractionEvent event) {
		bot.getInHouseManager().onButton(event);
	}

}
