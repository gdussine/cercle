package bot.view.impl;

import java.util.List;

import bot.exceptions.GuildConfigurationException;
import bot.view.EmbedView;
import net.dv8tion.jda.api.entities.Guild;

public class GuildConfigurationCheckView extends EmbedView{

    public GuildConfigurationCheckView(Guild guild, List<GuildConfigurationException> exceptions){
        this.template.setTitle(":gear: Configuration Checks");
        this.setExceptions(exceptions);

    }

    private void setExceptions(List<GuildConfigurationException> exceptions){
        if(exceptions.size() == 0){
            this.template.setDescription("Configuration is complete");
            this.template.setColor(EmbedView.GREEN);
            return;
        }
        this.template.setDescription("There is %d error(s)".formatted(exceptions.size()));
        this.template.setColor(EmbedView.RED);
        for(GuildConfigurationException exception : exceptions){
            this.template.addField(exception.getLabel(), exception.getMessage(), true);
        }
    }


}
