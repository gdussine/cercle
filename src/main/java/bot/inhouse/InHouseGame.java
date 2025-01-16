package bot.inhouse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import bot.inhouse.event.InHouseEvent;
import bot.inhouse.event.InHouseStatus;
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
public class InHouseGame extends InHouseEntity{
	
	@Id
	private String id;

	private String channelId;
	
	private LocalDateTime creationDate;

	private int number;
	
	@ManyToOne
	@JoinColumn(name = "event", nullable = false)
	private InHouseEvent event;
	
	@Enumerated(EnumType.STRING)
	private InHouseGameSide winner;

	@Enumerated(EnumType.STRING)
	private InHouseStatus status;
	
	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
	private List<InHouseGamePick> picks;


	public InHouseGame(){

	}

	public InHouseGame(InHouseEvent event){
		this.event = event;
		creationDate = LocalDateTime.now();
		status = InHouseStatus.OPEN;
		picks = new ArrayList<>();
		winner = null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getGuildId() {
		return this.getEvent().getSeason().getGuildId();
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

	public InHouseGameSide getWinner() {
		return winner;
	}

	public void setWinner(InHouseGameSide winner) {
		this.winner = winner;
	}

	public InHouseStatus getStatus() {
		return status;
	}

	public void setStatus(InHouseStatus status) {
		this.status = status;
	}

	public List<InHouseGamePick> getPicks() {
		return picks;
	}

	public void setPicks(List<InHouseGamePick> picks) {
		this.picks = picks;
	}

	public List<InHouseGamePick> getTeam(InHouseGameSide side){
		return this.getPicks().stream().filter(x->x.getSide().equals(side)).collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return "Game %d".formatted(number);
	}
	
}
