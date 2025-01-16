package bot.inhouse;

import bot.inhouse.event.InHouseStatus;
import bot.view.DiscordPrintable;

public abstract class InHouseEntity implements DiscordPrintable{

    public abstract String getId();
	public abstract void setId(String id);

    public abstract String getGuildId();
    public abstract String getChannelId();

	public abstract InHouseStatus getStatus();
	public abstract InHouseStatus setStatus(InHouseStatus status);

	@Override
	public String toDiscordString() {
		return DiscordPrintable.templateDiscordLink(this, getGuildId(), getChannelId(), getId());
	}


}
