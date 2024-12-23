package bot.view.impl;

import bot.view.EmbedView;
import net.dv8tion.jda.api.entities.Member;

public class WelcomeAdminView extends EmbedView{;

    public WelcomeAdminView(Member member){
        this.template.setTitle("SYSTEM.WELCOME : %s just join!".formatted(member.getEffectiveName()));
        this.template.setColor(BLUE);
        this.template.appendDescription(":id: <@%s>\n".formatted(member.getId()));
        this.template.appendDescription(":clock1: %s\n".formatted(DATETIME_FORMAT.format(member.getTimeJoined().atZoneSameInstant(defaultZone))));
    }

}
