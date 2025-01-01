package bot.view;

import java.awt.Color;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class EmbedView {

    protected static final Color 
            RED = new Color(0xd10023),
            YELLOW = new Color(0xd1bc00),
            GREEN = new Color(0x00d154),
            BLUE = new Color(0x0054d1),
            DARK_BLUE = new Color(0x000a2d),
            BLACK = new Color(0x001429);

    protected static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    protected ZoneId defaultZone = ZoneId.of("Europe/Paris");

    protected EmbedBuilder template;

    public EmbedView() {
        this.template = new EmbedBuilder();
    }

    public MessageEmbed toEmbed() {
        return template.build();
    }
}
