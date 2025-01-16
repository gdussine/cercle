package bot.inhouse.season;

import bot.player.Player;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class InHouseSeasonElo {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "season", nullable = false)
    private InHouseSeason season;

    @ManyToOne
    @JoinColumn(name = "player", nullable = false)
    private Player player;

    private int elo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InHouseSeason getSeason() {
        return season;
    }

    public void setSeason(InHouseSeason season) {
        this.season = season;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    


}
