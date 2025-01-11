package bot.inhouse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import bot.config.GuildContext;
import bot.core.BotManager;
import bot.inhouse.event.InHouseEvent;
import bot.inhouse.event.InHouseEventStatus;
import bot.inhouse.season.InHouseSeason;
import bot.inhouse.event.InHouseEventParticipation;
import bot.persistence.Repository;
import bot.player.Player;
import bot.view.impl.InHouseParticipationView;
import bot.view.impl.InHouseSystemView;
import jakarta.persistence.criteria.Predicate;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.MessageEditAction;

public class InHouseManager extends BotManager {

	private static final String CATEGORY_ID = "inhouse_";
	private static final String JOIN_BTN_ID = CATEGORY_ID + "join";
	private static final String QUIT_BTN_ID = CATEGORY_ID + "quit";
	private static final String CLOSE_BTN_ID = CATEGORY_ID + "close";

	private static final Button JOIN_BTN = Button.success(JOIN_BTN_ID, "Join");
	private static final Button QUIT_BTN = Button.danger(QUIT_BTN_ID, "Quit");

	private Repository<InHouseEvent> eventRepository;
	private Repository<InHouseEventParticipation> participationRepository;

	public InHouseManager() {
		super(new InHouseListener());
		this.eventRepository = new Repository<>(InHouseEvent.class);
		this.participationRepository = new Repository<>(InHouseEventParticipation.class);
	}

	public CompletableFuture<InHouseEvent> createEvent(Guild guild, String name, LocalDateTime schedule, int places) {
		GuildContext context = bot.getContext(guild.getId());
		InHouseEvent event = null;// new InHouseEvent(guild.getId(), name, places, schedule);
		InHouseParticipationView view = new InHouseParticipationView(event);
		CompletableFuture<InHouseEvent> result = context.getInHouseEvent().sendMessageEmbeds(view.toEmbed())
				.addActionRow(JOIN_BTN, QUIT_BTN).submit().thenApply(x -> {
					event.setId(x.getId());
					logInfo("%s saved as inHouse event".formatted(event.toDiscordString()), guild);
					return eventRepository.persist(event);
				});
		result.thenCompose(x -> {
			final Button closeBtn = Button.danger(CLOSE_BTN_ID + "_" + x.getId(), "Close");
			InHouseSystemView systemView = new InHouseSystemView(x);
			return context.getSystem().sendMessageEmbeds(systemView.toEmbed()).addActionRow(closeBtn).submit();
		});
		return result;
	}
	
	public InHouseSeason createSeason(Guild guild, Long id) {
		
	}

	public InHouseEventParticipation getParticipations(InHouseEvent event, Player player) {
		List<InHouseEventParticipation> list = participationRepository.query(x -> {
			Predicate[] predicate = new Predicate[2];
			predicate[0] = x.getCriteriaBuilder().equal(x.getRoot().get("event"), event);
			predicate[1] = x.getCriteriaBuilder().equal(x.getRoot().get("player"), player);
			return predicate;
		});
		if (list.size() == 0)
			return null;
		return list.get(0);
	}

	public List<InHouseEvent> getOpenGuildEvents(Guild guild) {
		List<InHouseEvent> list = eventRepository.query(x -> {
			Predicate[] predicate = new Predicate[2];
			predicate[0] = x.getCriteriaBuilder().equal(x.getRoot().get("status"), InHouseEventStatus.OPEN);
			predicate[1] = x.getCriteriaBuilder().equal(x.getRoot().get("guildId"), guild.getId());
			return predicate;
		});
		return list;
	}

	public InHouseEvent getEvent(String id) {
		return eventRepository.get(id);
	}
	
    public List<InHouseEvent> getEvents(){
        return eventRepository.all();
    }


	public void onButton(ButtonInteractionEvent btnEvent) {
		if (!btnEvent.getComponentId().startsWith(CATEGORY_ID))
			return;
		else if (btnEvent.getComponentId().startsWith(CLOSE_BTN_ID))
			this.onSystemButton(btnEvent);
		else
			this.onParticipationButton(btnEvent);
	}

	public void onParticipationButton(ButtonInteractionEvent btnEvent) {
		InHouseEvent event = eventRepository.get(btnEvent.getMessage().getId());
		Player player = bot.getPlayerManager().getPlayer(btnEvent.getUser(), btnEvent.getGuild());
		if (btnEvent.getComponentId().equals(JOIN_BTN_ID)) {
			this.join(btnEvent.getGuild(), event, player);
		}
		if (btnEvent.getComponentId().equals(QUIT_BTN_ID)) {
			this.quit(btnEvent.getGuild(), event, player);
		}
		btnEvent.editMessageEmbeds(show(event)).submit();
	}

	public void onSystemButton(ButtonInteractionEvent btnEvent) {
		GuildContext context = bot.getContext(btnEvent.getGuild().getId());
		String eventId = btnEvent.getComponentId().split("_")[2];
		InHouseEvent event = eventRepository.get(eventId);
		this.close(btnEvent.getGuild(), event);
		context.getInHouseEvent().retrieveMessageById(eventId).submit().thenCompose(msg -> {
			return msg.editMessageEmbeds(show(event)).setComponents().submit();
		}).thenCompose(msg->{
			InHouseSystemView view = new InHouseSystemView(event).disable();
			return btnEvent.editMessageEmbeds(view.toEmbed()).setComponents().submit();
		});
	}

	public void onCommandUpdate(SlashCommandInteractionEvent interaction, InHouseEvent event) {
		GuildContext context = bot.getContext(interaction.getGuild().getId());
		context.getInHouseEvent().retrieveMessageById(event.getId()).submit().thenCompose(msg -> {
			MessageEditAction result = msg.editMessageEmbeds(show(event));
			if (event.getStatus().equals(InHouseEventStatus.CLOSE))
				result = result.setComponents();
			return result.submit();
		}).thenCompose(msg -> {
			return interaction.reply("Done !").setEphemeral(true).submit();
		});
	}

	public void join(Guild guild, InHouseEvent event, Player player) {
		long nbParticipation = event.getParticipations().stream()
				.filter(x -> x.getPlayer().getId().equals(player.getId())).count();
		if (nbParticipation == 0) {
			InHouseEventParticipation participation = new InHouseEventParticipation(event, player, LocalDateTime.now());
			participationRepository.persist(participation);
			logInfo("%s join the inHouse %s".formatted(player.toDiscordString(), event.toDiscordString()), guild);
		}
	}

	public void quit(Guild guild, InHouseEvent event, Player player) {
		long nbParticipation = event.getParticipations().stream()
				.filter(x -> x.getPlayer().getId().equals(player.getId())).count();
		if (nbParticipation > 0) {
			InHouseEventParticipation participation = getParticipations(event, player);
			event.getParticipations().remove(participation);
			player.getParticipations().remove(participation);
			participationRepository.delete(participation);
			logInfo("%s quit the inHouse %s".formatted(player.toDiscordString(), event.toDiscordString()), guild);
		}
	}

	public void close(Guild guild, InHouseEvent event) {
		event.setStatus(InHouseEventStatus.CLOSE);
		eventRepository.merge(event);
		logInfo("InHouse %s has been closed".formatted(event.toDiscordString()), guild);
	}

	public void delete(Guild guild, InHouseEvent event) {
		GuildContext context = bot.getContext(guild.getId());
		context.getInHouseEvent().retrieveMessageById(event.getId()).submit().thenCompose(msg -> {
			eventRepository.delete(event);
			logInfo("InHouse %s has been deleted".formatted(event.toDiscordString()), guild);
			return msg.delete().submit();
		});
	}

	public MessageEmbed show(InHouseEvent event) {
		eventRepository.refresh(event);
		return new InHouseParticipationView(event).toEmbed();
	}

}
