package bot.view.impl;

import bot.inhouse.InHouseGame;
import bot.inhouse.InHouseGamePick;
import bot.inhouse.InHouseGameSide;
import bot.utils.BotDateFormat;
import bot.view.EmbedView;

public class InHouseGameView extends EmbedView{

    public InHouseGameView(InHouseGame game){
        this.template.setTitle(":shield: InHouse Game");
		this.template.setDescription("Event: %s\nDate: %s\nPlaces: %d/%d\nStatus: %s".formatted(
            game.getEvent().getName(),
			BotDateFormat.DEFAULT.getFormatter().format(game.getCreationDate()),
            game.getPicks().size(),
            game.getEvent().getPlaces(),
			game.getStatus().name()));
        this.template.setColor(DARK_BLUE);
        this.setTeam(game, InHouseGameSide.BLUE);
        this.setTeam(game, InHouseGameSide.RED);
    }

    public void setTeam(InHouseGame game, InHouseGameSide side){
        StringBuilder sb = new StringBuilder();
        for(InHouseGamePick pick : game.getTeam(side)){
            sb.append(side.discordSquare()).append(pick.getPlayer().toDiscordString());
        }
        this.template.addField(side.name(), sb.toString(), true);
    }
}
