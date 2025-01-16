package bot.view.impl;

import bot.inhouse.InHouseEntity;
import bot.inhouse.event.InHouseEvent;
import bot.view.InHouseEntityView;

public class InHouseEventView extends InHouseEntityView<InHouseEvent> {

	public InHouseEventView() {
		super(":crossed_swords:", "InHouse Event");
	}

	private void setParticipations(InHouseEvent event) {
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
    @Override
    public InHouseEntityView<InHouseEvent> hydrate(InHouseEvent event) {
		this.appendRow("Name", event.getName());
		this.appendRow("Date", event.getScheduleDate());
		this.appendRow("Places", "%d/%d".formatted(event.getParticipations().size(), event.getPlaces()));
		this.setParticipations(event);
		return this;
	}

}
