package bot.view;

import java.awt.Color;
import java.time.LocalDateTime;

import bot.inhouse.event.InHouseStatus;
import bot.utils.BotDateFormat;

public class NormalizedEmbedView extends EmbedView {

    public NormalizedEmbedView(String emoji, String title) {
        this.template.setTitle("%s %s".formatted(emoji, title));
    }

    protected void setStatus(InHouseStatus status) {
        Color color = EmbedView.DARK_BLUE;
        if (status.equals(InHouseStatus.OPEN))
            color = EmbedView.WHITE;
        this.template.setColor(color);
    }

    protected void appendRow(String label, String value){
        this.template.appendDescription("%s: %s\n".formatted(label, value));
    }

    protected void appendRow(String label, LocalDateTime date){
        this.appendRow(label,BotDateFormat.DEFAULT.getFormatter().format(date));
    }

}
