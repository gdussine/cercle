package bot.view.impl;

import java.time.LocalDateTime;

import org.slf4j.event.Level;

import bot.view.EmbedView;

public class LogView extends EmbedView{

    public LogView(Level level, String author, String message){
        this.setLevel(level);
        this.template.setAuthor(author);
        this.template.appendDescription(message);
        this.template.setFooter("%s".formatted( EmbedView.DATETIME_FORMAT.format(LocalDateTime.now())));
        
    }

    public void setLevel(Level level){
        if(level.equals(Level.INFO)){
            this.template.setColor(EmbedView.BLUE);
        } else if (level.equals(Level.WARN)){
            this.template.setColor(EmbedView.YELLOW);
        } else if (level.equals(Level.ERROR)){
            this.template.setColor(EmbedView.RED);
        }

    }

}
