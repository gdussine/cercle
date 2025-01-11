package bot.view.pagination;

import bot.core.BotListener;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class PaginationViewListener extends BotListener{
	
	@Override
	public void onButtonInteraction(ButtonInteractionEvent event) {
		bot.getPaginationViewManager().onButtonPress(event);
	}

}
