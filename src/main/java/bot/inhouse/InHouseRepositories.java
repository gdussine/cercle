package bot.inhouse;

import bot.inhouse.event.InHouseEvent;
import bot.inhouse.event.InHouseEventParticipation;
import bot.persistence.Repository;

public class InHouseRepositories {

    private Repository<InHouseEvent> event;
    private Repository<InHouseEventParticipation> participation;

    public InHouseRepositories() {
        this.event = new Repository<>(InHouseEvent.class);
        this.participation = new Repository<>(InHouseEventParticipation.class);
    }

    public Repository<InHouseEvent> event() {
        return event;
    }

    public Repository<InHouseEventParticipation> participation() {
        return participation;
    }



}
