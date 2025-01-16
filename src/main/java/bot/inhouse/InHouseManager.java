package bot.inhouse;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import bot.config.GuildContext;
import bot.core.BotManager;
import bot.inhouse.event.InHouseEvent;
import bot.inhouse.event.InHouseEventParticipation;
import bot.inhouse.event.InHouseStatus;
import bot.inhouse.season.InHouseSeason;
import bot.persistence.Repository;
import bot.player.Player;
import bot.view.InHouseEntityView;
import bot.view.impl.InHouseEventView;
import bot.view.impl.InHouseSeasonView;
import bot.view.impl.InHouseSystemView;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;
import net.dv8tion.jda.api.requests.restaction.MessageEditAction;
import net.dv8tion.jda.api.requests.restaction.interactions.MessageEditCallbackAction;

public class InHouseManager extends BotManager {

	private static final String CATEGORY_ID = "inhouse_";
	private static final String JOIN_BTN_ID = CATEGORY_ID + "join";
	private static final String QUIT_BTN_ID = CATEGORY_ID + "quit";
	private static final String CLOSE_BTN_ID = CATEGORY_ID + "close";

	private static final Button JOIN_BTN = Button.success(JOIN_BTN_ID, "Join");
	private static final Button QUIT_BTN = Button.danger(QUIT_BTN_ID, "Quit");

	private InHouseRepositories proxy;

	public InHouseManager() {
		super(new InHouseListener());
		this.proxy = new InHouseRepositories();
	}

	public InHouseRepositories getRepository() {
		return proxy;
	}

	public <T extends InHouseEntity> CompletableFuture<T> createEntity(TextChannel channel, Repository<T> rep,
			InHouseEntityView<T> view, T t, ItemComponent... components) {
		CompletableFuture<T> result = channel.sendMessageEmbeds(view.hydrate(t).toEmbed()).addActionRow(components)
				.submit()
				.thenApply(msg -> {
					t.setId(msg.getId());
					return rep.persist(t);
				});
		result.thenAcceptAsync(x -> this.log.info("%s %s created.".formatted(t.getClass().getSimpleName(), t)));
		return result;
	}

	public CompletableFuture<InHouseSeason> createSeason(Guild guild, int number) {
		GuildContext context = bot.getContext(guild.getId());
		InHouseSeason season = new InHouseSeason(guild.getId(), context.getInHouseSeason().getId(), number);
		return createEntity(context.getInHouseSeason(), proxy.season(), new InHouseSeasonView(), season);
	}

	public CompletableFuture<InHouseEvent> createEvent(Guild guild, String name, LocalDateTime schedule, int places) {
		GuildContext context = bot.getContext(guild.getId());
		InHouseSeason season = proxy.getLastSeason(guild);
		InHouseEvent event = new InHouseEvent(context.getInHouseEvent().getId(), name, places, schedule, season);
		CompletableFuture<InHouseEvent> result = createEntity(context.getInHouseEvent(), proxy.event(),
				new InHouseEventView(), event, JOIN_BTN, QUIT_BTN);
		result.thenCompose(x -> {
			Button closeBtn = Button.danger(CLOSE_BTN_ID + "_" + x.getId(), "Close");
			InHouseSystemView systemView = new InHouseSystemView(x);
			return context.getSystem().sendMessageEmbeds(systemView.toEmbed()).addActionRow(closeBtn).submit();
		}).thenCompose(x -> {
			return refreshView(context.getInHouseSeason(), proxy.season(), new InHouseSeasonView(), season);
		});
		return result;
	}

	public <T extends InHouseEntity> CompletableFuture<Message> closeEntity(TextChannel channel, Repository<T> rep,
			InHouseEntityView<T> view,
			T t) {
		t.setStatus(InHouseStatus.CLOSE);
		rep.merge(t);
		CompletableFuture<Message> result = refreshView(channel, rep, view, t);
		result.thenAcceptAsync(x -> this.log.info("%s %s closed.".formatted(t.getClass().getSimpleName(), t)));
		return result;
	}

	public <T extends InHouseEntity> CompletableFuture<Message> refreshView(Message hook, Repository<T> rep,
			InHouseEntityView<T> view, T t) {
		rep.refresh(t);
		MessageEditAction action = hook.editMessageEmbeds(view.hydrate(t).toEmbed());
		if (t.getStatus().equals(InHouseStatus.CLOSE))
			action = action.setActionRow();
		return action.submit();
	}

	public <T extends InHouseEntity> CompletableFuture<InteractionHook> refreshView(ButtonInteraction hook,
			Repository<T> rep,
			InHouseEntityView<T> view, T t) {
		rep.refresh(t);
		MessageEditCallbackAction action = hook.editMessageEmbeds(view.hydrate(t).toEmbed());
		if (t.getStatus().equals(InHouseStatus.CLOSE))
			action = action.setActionRow();
		return action.submit();
	}

	public <T extends InHouseEntity> CompletableFuture<Message> refreshView(TextChannel channel, Repository<T> rep,
			InHouseEntityView<T> view, T t) {
		return channel.retrieveMessageById(t.getId()).submit().thenCompose(msg -> {
			return this.refreshView(msg, rep, view, t);
		});
	}

	public InHouseGame createGame(InHouseEvent event, Player blueCaptain, Player redCaptain) {
		InHouseGame game = new InHouseGame(event);
		proxy.game().persist(game);
		return game;
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
		InHouseEvent event = proxy.event().get(btnEvent.getMessage().getId());
		Player player = bot.getPlayerManager().getPlayer(btnEvent.getUser(), btnEvent.getGuild());
		if (btnEvent.getComponentId().equals(JOIN_BTN_ID)) {
			this.join(btnEvent.getGuild(), event, player);
		}
		if (btnEvent.getComponentId().equals(QUIT_BTN_ID)) {
			this.quit(btnEvent.getGuild(), event, player);
		}
		this.refreshView(btnEvent, proxy.event(), new InHouseEventView(), event);
	}

	public void onSystemButton(ButtonInteractionEvent btnEvent) {
		GuildContext context = bot.getContext(btnEvent.getGuild().getId());
		String eventId = btnEvent.getComponentId().split("_")[2];
		InHouseEvent event = proxy.event().get(eventId);
		this.closeEntity(context.getInHouseEvent(), proxy.event(), new InHouseEventView(), event).thenCompose(msg -> {
			InHouseSystemView view = new InHouseSystemView(event).disable();
			return btnEvent.editMessageEmbeds(view.toEmbed()).setComponents().submit();
		});
	}

	public void onCommandUpdate(SlashCommandInteractionEvent interaction, InHouseEvent event) {
		GuildContext context = bot.getContext(interaction.getGuild().getId());
		this.refreshView(context.getInHouseEvent(), proxy.event(), new InHouseEventView(), event).thenCompose(msg -> {
			return interaction.reply("Done !").setEphemeral(true).submit();
		});
	}

	public void join(Guild guild, InHouseEvent event, Player player) {
		long nbParticipation = event.getParticipations().stream()
				.filter(x -> x.getPlayer().getId().equals(player.getId())).count();
		if (nbParticipation == 0) {
			InHouseEventParticipation participation = new InHouseEventParticipation(event, player, LocalDateTime.now());
			proxy.participation().persist(participation);
			logInfo("%s join the inHouse %s".formatted(player.toDiscordString(), event.toDiscordString()), guild);
		}
	}

	public void quit(Guild guild, InHouseEvent event, Player player) {
		long nbParticipation = event.getParticipations().stream()
				.filter(x -> x.getPlayer().getId().equals(player.getId())).count();
		if (nbParticipation > 0) {
			InHouseEventParticipation participation = proxy.getParticipation(event, player);
			event.getParticipations().remove(participation);
			player.getParticipations().remove(participation);
			proxy.participation().delete(participation);
			logInfo("%s quit the inHouse %s".formatted(player.toDiscordString(), event.toDiscordString()), guild);
		}
	}

	public void delete(Guild guild, InHouseEvent event) {
		GuildContext context = bot.getContext(guild.getId());
		context.getInHouseEvent().retrieveMessageById(event.getId()).submit().thenCompose(msg -> {
			proxy.event().delete(event);
			logInfo("InHouse %s has been deleted".formatted(event.toDiscordString()), guild);
			return msg.delete().submit();
		});
	}

}
