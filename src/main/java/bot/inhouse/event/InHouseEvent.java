package bot.inhouse.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import bot.inhouse.InHouseEntity;
import bot.inhouse.season.InHouseSeason;
import bot.view.DiscordPrintable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class InHouseEvent extends InHouseEntity {

	@Id
	private String id; 

	private String channelId;

	private String name;

	private int places;

	private LocalDateTime creationDate;

	private LocalDateTime scheduleDate;
	
	@Enumerated(EnumType.STRING)
	private InHouseStatus status;

	@ManyToOne
	@JoinColumn(name = "season", nullable = false)
	private InHouseSeason season;
	
	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
	private List<InHouseEventParticipation> participations;
	
	

	public InHouseEvent() {
	}

	public InHouseEvent(String channelId, String name, int places, LocalDateTime scheduleDate, InHouseSeason season) {
		super();
		this.channelId = channelId;
		this.name = name;
		this.places = places;
		this.creationDate = LocalDateTime.now();
		this.scheduleDate = scheduleDate;
		this.status = InHouseStatus.OPEN;
		this.season = season;
		this.participations = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
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

	public InHouseStatus getStatus() {
		return status;
	}

	public void setStatus(InHouseStatus status) {
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
	public String toString() {
		return "Event %s".formatted(name);
	}

	@Override
	public String getGuildId() {
		return season.getGuildId();
	}

}
