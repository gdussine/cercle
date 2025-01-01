package bot.view.impl;

import bot.view.EmbedView;
import net.dv8tion.jda.api.entities.Member;

public class WelcomeAdminView extends EmbedView{;

    public WelcomeAdminView(Member member){
        this.template.setTitle(":wave: Welcome Info".formatted(member.getEffectiveName()));
        this.template.setColor(BLUE);
        this.template.appendDescription(":id: <@%s>\n".formatted(member.getId()));
        this.template.appendDescription(":calendar: %s".formatted(DATETIME_FORMAT.format(member.getUser().getTimeCreated())));
    }

}
