package bot.command.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandOption;
import bot.command.core.CommandAction;
import bot.command.core.CommandAutoCompleters;
import bot.inhouse.InHouseGame;
import bot.inhouse.event.InHouseEvent;
import bot.inhouse.season.InHouseSeason;
import bot.player.Player;
import bot.utils.BotDateFormat;
import bot.view.impl.InHouseGameView;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;

@CommandModule(name = "inhouse", permission = { Permission.ADMINISTRATOR })
public class InHouseAction extends CommandAction {

	@CommandDescription(value = "Create an InHouse Event")
	public void event(
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
		InHouseEvent event = bot.getInHouseManager().getRepository().getEvent(eventId);
		Player player = bot.getPlayerManager().getPlayer(userPlayer, interaction.getGuild());
		bot.getInHouseManager().join(interaction.getGuild(), event, player);
		bot.getInHouseManager().onCommandUpdate(interaction, event);
	}
	
	@CommandDescription(value = "Quit player from InHouse Event")
	public void quit(
			@CommandOption(name = "event", description = "Event", autocomplete = CommandAutoCompleters.OPEN_EVENT) String eventId,
			@CommandOption(name = "player", description = "Player") User userPlayer) {
		InHouseEvent event = bot.getInHouseManager().getRepository().getEvent(eventId);
		Player player = bot.getPlayerManager().getPlayer(userPlayer, interaction.getGuild());
		bot.getInHouseManager().quit(interaction.getGuild(), event, player);
		bot.getInHouseManager().onCommandUpdate(interaction, event);
	}
	
	@CommandDescription(value = "Delete an InHouse Event")
	public void delete(
			@CommandOption(name = "event", description = "Event", autocomplete = CommandAutoCompleters.OPEN_EVENT) String eventId) {
		InHouseEvent event = bot.getInHouseManager().getRepository().getEvent(eventId);
		bot.getInHouseManager().delete(interaction.getGuild(), event);
		interaction.reply("Done !").setEphemeral(true).queue();
	}

	@CommandDescription(value = "Reset and start the next season")
	public void season() {
		InHouseSeason season = bot.getInHouseManager().getRepository().getLastSeason(interaction.getGuild());
		int number = 1;
		if(season != null){
			bot.getInHouseManager().closeSeason(interaction.getGuild(), season);
			number = season.getNumber() +1;
		}
		bot.getInHouseManager().createSeason(interaction.getGuild(), number);
		interaction.reply("Done !").setEphemeral(true).queue();
	}

	@CommandDescription(value = "Start an InHouse Game ")
	public void game(
		@CommandOption(name = "event", description = "Event", autocomplete = CommandAutoCompleters.OPEN_EVENT) String eventId,
		@CommandOption(name = "blue", description = "Blue Captain") User blueCaptainUser,
		@CommandOption(name = "red", description = "Red Captain") User redCaptainUser){
		InHouseEvent event = bot.getInHouseManager().getRepository().getEvent(eventId);
		Player blueCaptain = bot.getPlayerManager().getPlayer(blueCaptainUser, interaction.getGuild());
		Player redCaptain = bot.getPlayerManager().getPlayer(redCaptainUser, interaction.getGuild());
		InHouseGame game = bot.getInHouseManager().createGame(event, blueCaptain, redCaptain);
		InHouseGameView view = new InHouseGameView(game);
		interaction.replyEmbeds(view.toEmbed()).queue();
	}

}
