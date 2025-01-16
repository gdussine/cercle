package bot.inhouse;

import bot.player.Player;
import irelia.data.dragon.Champions;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class InHouseGamePick {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private int number;
	
	@ManyToOne
	@JoinColumn(name = "player", nullable = false)
	private Player player;
	
	@Enumerated(EnumType.STRING)
	private Champions champion;

	@Enumerated(EnumType.STRING)
	private InHouseGameSide side;
	
	@ManyToOne
	@JoinColumn(name = "game", nullable = false)
	private InHouseGame game;

	public InHouseGamePick(int number, Player player, InHouseGameSide side, InHouseGame game) {
		this.number = number;
		this.player = player;
		this.side = side;
		this.game = game;
	}

	public InHouseGamePick() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int order) {
		this.number = order;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Champions getChampion() {
		return champion;
	}

	public void setChampion(Champions champion) {
		this.champion = champion;
	}

	public InHouseGameSide getSide() {
		return side;
	}

	public void setSide(InHouseGameSide side) {
		this.side = side;
	}

	public InHouseGame getGame() {
		return game;
	}

	public void setGame(InHouseGame game) {
		this.game = game;
	}

	
}
