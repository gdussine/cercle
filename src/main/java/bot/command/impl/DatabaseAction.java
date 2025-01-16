package bot.command.impl;

import java.util.ArrayList;
import java.util.List;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandOption;
import bot.command.core.CommandAction;
import bot.command.core.CommandAutoCompleters;
import bot.inhouse.event.InHouseEvent;
import bot.inhouse.season.InHouseSeason;
import bot.player.Player;
import bot.view.DiscordPrintable;
import bot.view.impl.DatabaseListView;
import net.dv8tion.jda.api.Permission;

@CommandModule(name = "database", permission = { Permission.ADMINISTRATOR })
public class DatabaseAction extends CommandAction {

	public static Class<?>[] LIST_TYPE = { Player.class, InHouseEvent.class, InHouseSeason.class};

	@CommandDescription("List all")
	public void list(
			@CommandOption(name = "type", description = "Type of the list", required = true, autocomplete = CommandAutoCompleters.LIST_TYPE) String type) {
		List<DiscordPrintable> list = new ArrayList<>();
		if (type.equals(LIST_TYPE[0].getSimpleName()))
			list.addAll(bot.getPlayerManager().getPlayers());
		else if (type.equals(LIST_TYPE[1].getSimpleName())) 
			list.addAll(bot.getInHouseManager().getRepository().getGuildEvents(interaction.getGuild()));
		else if(type.equals(LIST_TYPE[2].getSimpleName()))
			list.addAll(bot.getInHouseManager().getRepository().getGuildSeasons(interaction.getGuild()));
		DatabaseListView view = new DatabaseListView(type, list, 1);
		this.interaction.deferReply().queue();
		this.interaction.getHook().sendMessageEmbeds(view.toEmbed()).queue(message -> {
			bot.getPaginationViewManager().subscribe(view, message);
		});
	}

}
