package bot.inhouse;

import bot.player.Player;
import irelia.data.dragon.Champions;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class InHouseGamePick {

	@Id
	private Long id;
	
	@ManyToOne
	private Player player;
	
	@Enumerated(EnumType.STRING)
	private Champions champion;
	
	@ManyToOne
	private InHouseGame game;
}
