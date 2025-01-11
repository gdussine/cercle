package bot.inhouse.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import bot.inhouse.season.InHouseSeason;
import bot.utils.BotDateFormat;
import bot.view.DiscordPrintable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class InHouseEvent implements DiscordPrintable {

	@Id
	private String id; 

	private String guildId;

	private String name;

	private int places;

	private LocalDateTime creationDate;

	private LocalDateTime scheduleDate;
	
	@Enumerated(EnumType.STRING)
	private InHouseEventStatus status;

	@ManyToOne
	private InHouseSeason season;
	
	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
	private List<InHouseEventParticipation> participations;
	
	

	public InHouseEvent() {
	}

	public InHouseEvent(String guildId, String name, int places, LocalDateTime scheduleDate, InHouseSeason season) {
		super();
		this.guildId = guildId;
		this.name = name;
		this.places = places;
		this.creationDate = LocalDateTime.now();
		this.scheduleDate = scheduleDate;
		this.status = InHouseEventStatus.OPEN;
		this.season = season;
		this.participations = new ArrayList<>();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPlaces() {
		return places;
	}

	public void setPlaces(int places) {
		this.places = places;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(LocalDateTime scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public InHouseEventStatus getStatus() {
		return status;
	}

	public void setStatus(InHouseEventStatus status) {
		this.status = status;
	}
	
	public InHouseSeason getSeason() {
		return season;
	}
	
	public void setSeason(InHouseSeason season) {
		this.season = season;
	}

	public List<InHouseEventParticipation> getParticipations() {
		return participations;
	}

	public void setParticipations(List<InHouseEventParticipation> participations) {
		this.participations = participations;
	}

	@Override
	public String toDiscordString() {
		String scheduleString = BotDateFormat.DEFAULT.getFormatter().format(scheduleDate);
		return "Event %s - %s".formatted(name, scheduleString);
	}

}
