package bot.inhouse.event;

import java.time.LocalDateTime;

import bot.player.Player;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class InHouseEventParticipation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "player", nullable = false)
	private Player player;

	@ManyToOne
	@JoinColumn(name = "event", nullable = false)
	private InHouseEvent event;

	private LocalDateTime creationDate;

	public InHouseEventParticipation() {

	}

	public InHouseEventParticipation(InHouseEvent event, Player player, LocalDateTime creationDate) {
		super();
		this.player = player;
		this.event = event;
		this.creationDate = creationDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public InHouseEvent getEvent() {
		return event;
	}

	public void setEvent(InHouseEvent event) {
		this.event = event;
	}

}
