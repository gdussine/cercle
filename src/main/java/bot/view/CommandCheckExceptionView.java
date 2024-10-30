package bot.view;

import bot.exceptions.CommandCheckException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

public class CommandCheckExceptionView extends EmbedView {

    private CommandCheckException exception;

    public CommandCheckExceptionView(CommandCheckException exception) {
        this.exception = exception;
        this.template.setTitle(":warning: Command Exception");
        this.template.setColor(0xEED202);
        this.setMessage();
        this.setDetails();
    }

    private void setMessage() {
        this.template.addField("Message", exception.getMessage(), false);
    }

    private void setDetails() {
        Member member = exception.getAction().getInteraction().getMember();
        StringBuilder sb = new StringBuilder();
        sb.append("```\n");
        sb.append("command: ");
        sb.append(exception.getAction().getInteraction().getCommandString());
        sb.append("\nuser: ");
        sb.append(exception.getAction().getInteraction().getUser().getEffectiveName());
        sb.append("\nPermissions: ");
        sb.append(Permission.getRaw(member == null ? Permission.getPermissions(0) : member.getPermissions()));
        sb.append("\n```");
        this.template.addField("Details", sb.toString(), true);
    }

}
