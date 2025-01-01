package bot.automation.welcome;

import java.util.concurrent.CompletableFuture;

import bot.config.GuildContext;
import bot.core.BotManager;
import bot.view.impl.WelcomeAdminView;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class GateManager extends BotManager {

    private static final String CATEGORY_ID = "gate_";
    private static final String ACCEPT_BTN_ID = CATEGORY_ID + "accept_";
    private static final String REFUSE_BTN_ID = CATEGORY_ID + "refuse_";

    public GateManager() {
        super(new GateListener());
    }

    public void welcomeAction(ButtonInteractionEvent event) {
        if (!event.getButton().getId().startsWith(CATEGORY_ID))
            return;
        String id = event.getButton().getId().split("_")[2];
        GuildContext context = bot.getContext(event.getGuild());
        CompletableFuture<Void> future = null;
        if (event.getButton().getId().startsWith(ACCEPT_BTN_ID))
            future = event.getGuild().addRoleToMember(event.getMember(), context.getMember()).submit();
        else
            future = event.getGuild().kick(UserSnowflake.fromId(id)).submit();
        future.thenCompose(x -> event.editComponents().submit())
                .thenCompose(x -> event.getMessage().delete().submit());
    }

    public void welcomeNotify(GuildMemberJoinEvent event) {
        Button btnAccept = Button.success(ACCEPT_BTN_ID + event.getMember().getId(), "Accept");
        Button btnRefuse = Button.danger(REFUSE_BTN_ID + event.getMember().getId(), "Refuse");
        GuildContext context = bot.getContext(event.getGuild());
        WelcomeAdminView v = new WelcomeAdminView(event.getMember());
        context.getSystem().sendMessageEmbeds(v.toEmbed()).addActionRow(btnAccept, btnRefuse).queue();
    }

    public void goodbyeNotify(GuildMemberRemoveEvent event) {

    }

}
