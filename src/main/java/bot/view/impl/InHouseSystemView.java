package bot.view.impl;

import bot.inhouse.event.InHouseEvent;
import bot.utils.BotDateFormat;
import bot.view.EmbedView;

public class InHouseSystemView extends EmbedView{
	
    public InHouseSystemView(InHouseEvent event){
        this.template.setTitle(":crossed_swords: InHouse Tasks");
        this.template.setColor(DARK_BLUE);
        this.template.appendDescription("Name: %s\n".formatted(event.getName()));
        this.template.appendDescription("Date: %s\n".formatted(BotDateFormat.DEFAULT.getFormatter().format(event.getScheduleDate())));
    	this.template.appendDescription("Status: %s".formatted(event.getStatus().name()));
    }
    
    public InHouseSystemView disable() {
    	this.template.setColor(DISCORD);
    	return this;
    }

}
