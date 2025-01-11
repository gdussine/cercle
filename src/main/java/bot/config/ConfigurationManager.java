package bot.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bot.core.Bot;
import bot.core.BotManager;
import bot.exceptions.GuildConfigurationException;
import bot.persistence.Repository;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class ConfigurationManager extends BotManager {

    private Map<String, GuildContext> contexts = new HashMap<>();
    private Repository<GuildConfiguration> repository;
    private ConfigurationReflecter reflecter = ConfigurationReflecter.getInstance();

    public ConfigurationManager() {
        super(new ConfigurationListener());
        this.repository = new Repository<>(GuildConfiguration.class);
    }

    public GuildContext saveContext(GuildContext context) {
        contexts.put(context.getGuild().getId(), context);
        return context;
    }

    public void editConfiguration(Guild guild, GuildConfiguration configuration, String label, String value) {
        try {
            reflecter.configurationSet(label, value, configuration);
            configuration = repository.merge(configuration);
        } catch (GuildConfigurationException e) {
            logError(e.getMessage(), guild);
        }
    }

    public List<GuildConfigurationException> checkConfiguration(GuildConfiguration configuration, Guild guild) {
        List<GuildConfigurationException> result = new ArrayList<>();
        for (String label : reflecter.getLabels()) {
            Method configurationGetter = reflecter.getConfigurationGetter(label);
            Method contextGetter = reflecter.getContextGetter(label);
            try {
                Object object = configurationGetter.invoke(configuration);
                if (object == null) {
                    result.add(GuildConfigurationException.nullField(contextGetter));
                    continue;
                }
                String item = object.toString();
                if (contextGetter.getReturnType().equals(TextChannel.class)) {
                    Long id = Long.parseLong(item);
                    if (guild.getTextChannelById(id) == null) {
                        result.add(GuildConfigurationException.textChannelNotFound(label));
                        continue;
                    }
                }
                if (contextGetter.getReturnType().equals(Role.class)) {
                    Long id = Long.parseLong(item);
                    if (guild.getRoleById(id) == null) {
                        result.add(GuildConfigurationException.roleNotFound(label));
                        continue;
                    }
                }
            } catch (Exception e) {
                result.add(GuildConfigurationException.reflectionFailed(label, e));
            }
        }
        return result;
    }

    public GuildContext buildContext(Bot bot, GuildConfiguration configuration) {
        Guild guild = bot.getJDA().getGuildById(configuration.getGuild());
        GuildContext result = new GuildContext(guild);
        for (Method setter : reflecter.getContextSetters()) {
            String label = reflecter.getLabel(setter);
            try {
                String item = reflecter.configurationGet(label, configuration);
                reflecter.contextSet(label, item, result);
            } catch (GuildConfigurationException e) {
                logError(e.getMessage(), guild);
            }
        }
        return result;
    }

    public GuildContext createContext(GuildConfiguration configuration) {
        GuildContext context = buildContext(bot, configuration);
        return saveContext(context);
    }

    public GuildContext getContext(String guild) {
        return contexts.get(guild);
    }

    public GuildConfiguration getConfiguration(Guild guild) {
        GuildConfiguration result = repository.get(guild.getId());
        if (result == null)
            result = repository.persist(GuildConfiguration.createDefault(guild));
        return result;
    }

    public GuildConfiguration getConfiguration(String guildId) {
        Guild guild = bot.getJDA().getGuildById(guildId);
        return getConfiguration(guild);
    }

}
