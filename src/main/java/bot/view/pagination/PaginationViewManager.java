package bot.view.pagination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bot.core.BotManager;
import bot.utils.BotEmoji;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.interactions.MessageEditCallbackAction;

public class PaginationViewManager extends BotManager{
	
    private static final String CATEGORY_ID = "page_";
    private static final String NEXT_BTN_ID = CATEGORY_ID + "next";
    private static final String PREVIOUS_BTN_ID = CATEGORY_ID + "prev";
	
	private Map<String, PaginationView> history = new HashMap<>();
	

    public PaginationViewManager(){
        super(new PaginationViewListener());
    }
    
    public void subscribe(PaginationView view, Message message) {
    	history.put(message.getId(), view);
    	List<ItemComponent> btns = new ArrayList<>();
    	if(view.isOnePage())
    		return;
    	if(view.hasPrevious())
    		btns.add(Button.primary(PREVIOUS_BTN_ID, BotEmoji.ARROW_LEFT.asEmoji()));
		
		if(view.hasNext())
			btns.add(Button.primary(NEXT_BTN_ID, BotEmoji.ARROW_RIGHT.asEmoji()));
		message.editMessageComponents(ActionRow.of(btns)).queue();
	}
    
    public void onButtonPress(ButtonInteractionEvent event) {
    	if(!event.getButton().getId().startsWith(CATEGORY_ID))
    		return;
    	PaginationView view = history.get(event.getMessage().getId());
    	if(view == null) {
    		event.reply("This interaction is not available anymore").setEphemeral(true).queue();
    		event.getMessage().editMessageComponents().queue();
    	}	
    	MessageEditCallbackAction callback = null;
    	if(event.getButton().getId().equals(NEXT_BTN_ID))
    		callback = event.editMessageEmbeds(view.next().toEmbed());
    	else
    		callback = event.editMessageEmbeds(view.previous().toEmbed());
    	callback.queue(x->subscribe(view, event.getMessage()));
		
	}
    

    

}
