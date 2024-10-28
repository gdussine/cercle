package bot.config;

import bot.core.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class GuildContext {


    private GuildConfiguration configuration;
    private Bot bot;

    private Guild guild;

    private TextChannel system;
    private TextChannel log;


    public GuildContext(GuildConfiguration configuration, Bot bot){
        this.bot = bot;
        this.configuration = configuration;
        this.load();
    }

    private void load(){
        this.guild = bot.getJDA().getGuildById(configuration.getGuild());
        this.system = guild.getTextChannelById(configuration.getSystem());
        this.log = guild.getTextChannelById(configuration.getLog());
    }

    public Guild getGuild() {
        return guild;
    }

    public TextChannel getSystem() {
        return system;
    }

    public TextChannel getLog() {
        return log;
    }
}
