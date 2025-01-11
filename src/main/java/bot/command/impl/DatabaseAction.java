package bot.command.impl;

import java.util.ArrayList;
import java.util.List;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandOption;
import bot.command.autocomplete.CommandAutoCompleters;
import bot.command.core.CommandAction;
import bot.inhouse.event.InHouseEvent;
import bot.player.Player;
import bot.view.DiscordPrintable;
import bot.view.impl.DatabaseListView;
import net.dv8tion.jda.api.Permission;

@CommandModule(name = "database", permission = { Permission.ADMINISTRATOR })
public class DatabaseAction extends CommandAction {

	public static Class<?>[] LIST_TYPE = { Player.class, InHouseEvent.class };

	@CommandDescription("List all")
	public void list(
			@CommandOption(name = "type", description = "Type of the list", required = true, autocomplete = CommandAutoCompleters.LIST_TYPE) String type) {
		List<DiscordPrintable> list = new ArrayList<>();
		if (type.equals(LIST_TYPE[0].getSimpleName())) {
			list.addAll(bot.getPlayerManager().getPlayers());
		} else if (type.equals(LIST_TYPE[1].getSimpleName())) {
			list.addAll(bot.getInHouseManager().getEvents());
		}
		DatabaseListView view = new DatabaseListView(type, list, 1);
		this.interaction.deferReply().queue();
		this.interaction.getHook().sendMessageEmbeds(view.toEmbed()).queue(message -> {
			bot.getPaginationViewManager().subscribe(view, message);
		});
	}

	@CommandDescription("Load all")
	public void load(
			@CommandOption(name = "type", description = "Type of the list", required = true, autocomplete = CommandAutoCompleters.LIST_TYPE) String type) {
		if (type.equals(LIST_TYPE[0].getSimpleName())) {
			interaction.getGuild().loadMembers().onSuccess(members -> {
				members.forEach(member -> {
					bot.getPlayerManager().getPlayer(member, interaction.getGuild());
				});
				interaction.reply("Done !").queue();
			});
		}
	}

}
