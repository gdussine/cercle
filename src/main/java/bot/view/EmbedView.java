package bot.view;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class EmbedView {

    protected EmbedBuilder template;

    public EmbedView() {
        this.template = new EmbedBuilder();
    }

    public MessageEmbed toEmbed() {
        return template.build();
    }
}
