package bot.config;

import java.lang.reflect.Method;

import bot.core.Bot;
import bot.exceptions.GuildConfigurationException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class GuildContext {

    private Guild guild;

    private TextChannel system;
    private TextChannel log;

    private GuildContext(Guild guild) {
        this.guild = guild;
    }

    public Guild getGuild() {
        return guild;
    }

    public TextChannel getSystem() {
        return system;
    }

    public void setSystem(String id) {
        this.system = guild.getTextChannelById(id);
    }

    public TextChannel getLog() {
        return log;
    }

    public void setLog(String id) {
        this.log = guild.getTextChannelById(id);
    }

    public static GuildContext of(Bot bot, GuildConfiguration configuration) throws GuildConfigurationException {
        GuildContext result = new GuildContext(bot.getJDA().getGuildById(configuration.getGuild()));
        for (Method setter : GuildContext.class.getMethods()) {
            try {
                if (!setter.getName().startsWith("set"))
                    continue;
                Method getter = GuildConfiguration.class.getMethod("g" + setter.getName().substring(1));
                Object o = getter.invoke(configuration);
                if (o == null)
                    throw GuildConfigurationException.nullField(setter);
                setter.invoke(result, o);
            } catch (GuildConfigurationException e){
                throw e;
            } catch (Exception e) {
                throw GuildConfigurationException.reflectionFailed(setter, e);
            }
        }
        return result;
    }

}
