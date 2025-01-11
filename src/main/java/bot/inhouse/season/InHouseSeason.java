package bot.inhouse.season;

import java.time.LocalDateTime;
import java.util.List;

import bot.inhouse.event.InHouseEvent;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

public class InHouseSeason {
	
	@Id
	private Long id;
	
	private String guildId;
	
	private LocalDateTime creationDate;
	
	@OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
	private List<InHouseEvent> events;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getGuildId() {
		return guildId;
	}
	
	public void setGuildId(String guildId) {
		this.guildId = guildId;
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


}
