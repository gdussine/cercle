package bot.inhouse;

import java.time.LocalDateTime;
import java.util.List;

import bot.inhouse.event.InHouseEvent;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class InHouseGame {
	
	@Id
	private Long id;
	
	private LocalDateTime creationDate;
	
	@ManyToOne
	private InHouseEvent event;
	
	@Enumerated(EnumType.STRING)
	private InHouseGameSide winner;
	
	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
	private List<InHouseGamePick> picks;
	
	
	

}
