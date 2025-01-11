package bot.player;

import java.time.LocalDateTime;
import java.util.List;

import bot.inhouse.event.InHouseEventParticipation;
import bot.view.DiscordPrintable;
import irelia.data.account.Account;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import net.dv8tion.jda.api.entities.UserSnowflake;

@Entity
public class Player implements DiscordPrintable{

    @Id
    private String id;

    private String riotPuuid;

    private String riotId;
    
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<InHouseEventParticipation> participations;

    private LocalDateTime creationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getRiotPuuid() {
        return riotPuuid;
    }

    public void setRiotPuuid(String riotPuuid) {
        this.riotPuuid = riotPuuid;
    }

    public String getRiotId() {
        return riotId;
    }

    public void setRiotId(String riotId) {
        this.riotId = riotId;
    }

    public void setRiot(Account account){
        this.setRiotPuuid(account.getPuuid());
        this.setRiotId(account.getRiotId());
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }    

    public List<InHouseEventParticipation> getParticipations() {
		return participations;
	}
    
    public void setParticipations(List<InHouseEventParticipation> participations) {
		this.participations = participations;
	}
    
    public static Player createDefault(UserSnowflake user){
        Player p = new Player();
        p.setId(user.getId());
        p.setCreationDate(LocalDateTime.now());
        return p;
    }

    @Override
    public String toDiscordString() {
        return "<@%s>".formatted(this.id);
    }


}
