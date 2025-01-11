package bot.command.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandOption;
import bot.command.autocomplete.CommandAutoCompleters;
import bot.command.core.CommandAction;
import bot.inhouse.event.InHouseEvent;
import bot.player.Player;
import bot.utils.BotDateFormat;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;

@CommandModule(name = "inhouse", permission = { Permission.ADMINISTRATOR })
public class InHouseAction extends CommandAction {

	@CommandDescription(value = "Create an InHouse Event")
	public void create(
			@CommandOption(name = "name", description = "Name") String name,
			@CommandOption(name = "time", description = "Start time") String time,
			@CommandOption(name = "date", description = "Start date", required = false) String date,
			@CommandOption(name = "places", description = "Places number", required = false) Integer places) {
		TemporalAccessor dateAccessor = date == null ? LocalDate.now() : BotDateFormat.INPUT_DATE.getFormatter().parse(date);
		TemporalAccessor timeAccessor = BotDateFormat.INPUT_TIME.getFormatter().parse(time);
		int placeNumber = places == null ? 10 : places;
		LocalDateTime dateTime = LocalDateTime.of(LocalDate.from(dateAccessor), LocalTime.from(timeAccessor));
		bot.getInHouseManager().createEvent(interaction.getGuild(), name, dateTime, placeNumber);
		interaction.reply("Done !").setEphemeral(true).queue();
	}

	@CommandDescription(value = "Join player to InHouse Event")
	public void join(
			@CommandOption(name = "event", description = "Event", autocomplete = CommandAutoCompleters.OPEN_EVENT) String eventId,
			@CommandOption(name = "player", description = "Player") User userPlayer) {
		InHouseEvent event = bot.getInHouseManager().getEvent(eventId);
		Player player = bot.getPlayerManager().getPlayer(userPlayer, interaction.getGuild());
		bot.getInHouseManager().join(interaction.getGuild(), event, player);
		bot.getInHouseManager().onCommandUpdate(interaction, event);
	}
	
	@CommandDescription(value = "Quit player from InHouse Event")
	public void quit(
			@CommandOption(name = "event", description = "Event", autocomplete = CommandAutoCompleters.OPEN_EVENT) String eventId,
			@CommandOption(name = "player", description = "Player") User userPlayer) {
		InHouseEvent event = bot.getInHouseManager().getEvent(eventId);
		Player player = bot.getPlayerManager().getPlayer(userPlayer, interaction.getGuild());
		bot.getInHouseManager().quit(interaction.getGuild(), event, player);
		bot.getInHouseManager().onCommandUpdate(interaction, event);
	}
	
	@CommandDescription(value = "Delete an InHouse Event")
	public void delete(
			@CommandOption(name = "event", description = "Event", autocomplete = CommandAutoCompleters.OPEN_EVENT) String eventId) {
		InHouseEvent event = bot.getInHouseManager().getEvent(eventId);
		bot.getInHouseManager().delete(interaction.getGuild(), event);
		interaction.reply("Done !").setEphemeral(true).queue();
	}

}
