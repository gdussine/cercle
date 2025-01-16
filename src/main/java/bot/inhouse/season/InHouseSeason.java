package bot.inhouse.season;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import bot.inhouse.InHouseEntity;
import bot.inhouse.event.InHouseEvent;
import bot.inhouse.event.InHouseStatus;
import bot.view.DiscordPrintable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class InHouseSeason extends InHouseEntity {
	
	@Id
	private String id;
	
	private String guildId;

	private String channelId;

	private int number;

	@Enumerated(EnumType.STRING)
	private InHouseStatus status;
	
	private LocalDateTime creationDate;
	
	@OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
	private List<InHouseEvent> events;

	@OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
	private List<InHouseSeasonElo> elos;

	public InHouseSeason(){

	}

	public InHouseSeason(String guildId,  String channelId, int number) {
		this.guildId = guildId;
		this.channelId = channelId;
		this.number = number;
		this.status = InHouseStatus.OPEN;
		this.creationDate = LocalDateTime.now();
		this.events = new ArrayList<>();
		this.elos = new ArrayList<>();
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGuildId() {
		return guildId;
	}
	
	public void setGuildId(String guildId) {
		this.guildId = guildId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public InHouseStatus getStatus() {
		return status;
	}

	public void setStatus(InHouseStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public List<InHouseEvent> getEvents() {
		return events;
	}

	public void setEvents(List<InHouseEvent> events) {
		this.events = events;
	}

	public List<InHouseSeasonElo> getElos() {
		return elos;
	}

	public void setElos(List<InHouseSeasonElo> elos) {
		this.elos = elos;
	}

	public String toString(){
		return "Season %d".formatted(number);
	}

	@Override
	public String toDiscordString() {
		return DiscordPrintable.templateDiscordLink(this, guildId, channelId, id);
	}


}
