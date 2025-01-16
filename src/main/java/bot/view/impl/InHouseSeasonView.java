package bot.view.impl;

import bot.inhouse.InHouseEntity;
import bot.inhouse.event.InHouseEvent;
import bot.inhouse.season.InHouseSeason;
import bot.view.InHouseEntityView;

public class InHouseSeasonView extends InHouseEntityView<InHouseSeason>{

    public InHouseSeasonView(){
        super(":european_castle:", "InHouse Season");
    }

    private void setEventField(InHouseSeason season){
        int eventsCount = season.getEvents().size();
        if(eventsCount <= 0)
            return;
        StringBuilder sb = new StringBuilder();
        for(InHouseEvent event : season.getEvents().subList(Math.max(0, eventsCount -5), eventsCount)){
            sb.insert(0,"\n").insert(0,event.toDiscordString());
        }
        this.template.addField("Last Events", sb.toString(), true);
    }


    @Override
    public InHouseEntityView<InHouseSeason> hydrate(InHouseSeason season) {
        this.setStatus(season.getStatus());
        this.appendRow("Name", season.toString());;
        this.appendRow("Date", season.getCreationDate());
        this.setEventField(season);
        return this;
    }


}
