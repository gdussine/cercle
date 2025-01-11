package bot.view.impl;

import bot.inhouse.event.InHouseEvent;
import bot.inhouse.event.InHouseEventStatus;
import bot.utils.BotDateFormat;
import bot.view.EmbedView;

public class InHouseParticipationView extends EmbedView {

	public InHouseParticipationView(InHouseEvent event) {
		this.template.setTitle(":crossed_swords: InHouse Event");
		this.template.setDescription("Name: %s\nDate: %s\nPlaces: %d/%d\nStatus: %s".formatted(event.getName(),
				BotDateFormat.DEFAULT.getFormatter().format(event.getScheduleDate()), event.getParticipations().size(),
				event.getPlaces(), event.getStatus().name()));
		if (event.getStatus().equals(InHouseEventStatus.OPEN))
			open(event);
		else
			close(event);
	}

	public void close(InHouseEvent event) {
		this.template.setColor(DISCORD);
	}

	public void open(InHouseEvent event) {
		this.template.setColor(DARK_BLUE);
		if (event.getParticipations().isEmpty())
			return;
		StringBuilder sbParticipant = new StringBuilder();
		StringBuilder sbSubstitute = new StringBuilder();
		for (int i = 0; i < event.getParticipations().size(); i++) {
			if (i < event.getPlaces())
				sbParticipant.append(" ").append(event.getParticipations().get(i).getPlayer().toDiscordString())
						.append("\n");
			else
				sbSubstitute.append(" ").append(event.getParticipations().get(i).getPlayer().toDiscordString())
						.append("\n");
		}
		this.template.addField("Participants:", sbParticipant.toString(), true);
		this.template.addField("Substitutes:", sbSubstitute.toString(), true);
	}

}
