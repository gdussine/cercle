package bot.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bot.core.BotManager;
import bot.exceptions.GuildConfigurationException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class ConfigurationManager extends BotManager {

    private ObjectMapper mapper;
    private Map<String, GuildContext> contexts = new HashMap<>();

    private static final Path CONFIG_DIRECTORY_PATH = Path.of("config");
    private static final Path CONFIG_DEFAULT_PATH = CONFIG_DIRECTORY_PATH.resolve("default.json");
    private static final String CONFIG_CHANNEL_NAME = "config";

    public ConfigurationManager() {
        super(new ConfigurationListener());
        this.mapper = new ObjectMapper();
    }

    public BotConfiguration load(String configName) {
        Path path = configName == null ? CONFIG_DEFAULT_PATH : CONFIG_DIRECTORY_PATH.resolve(configName + ".json");
        File file = path.toFile();
        try {
            BotConfiguration result = mapper.readValue(file, BotConfiguration.class);
            log.info("Configuration \"%s\" successfuly loaded from %s".formatted(result.getName(), path));
            return result;
        } catch (IOException e) {
            log.error("Configuration can't be read from %s".formatted(path), e);
        }
        return null;
    }

    public Message retrieveGuildConfigurationMessage(Guild guild) throws GuildConfigurationException{
        List<TextChannel> temp = guild.getTextChannelsByName(CONFIG_CHANNEL_NAME, false);
        if(temp.isEmpty())
            throw GuildConfigurationException.noChannel();
        TextChannel channel = temp.getFirst();
        if(channel.getLatestMessageIdLong() == 0)
            throw GuildConfigurationException.noMessage();
        return channel.retrieveMessageById(channel.getLatestMessageId()).complete();
    }

    public GuildConfiguration readGuildConfiguration(Message message) throws GuildConfigurationException{
        try {
            GuildConfiguration result = mapper.readValue(message.getContentRaw(), GuildConfiguration.class);
            result.setGuild(message.getGuildId());
            return result;
        } catch (JsonProcessingException e) {
            throw GuildConfigurationException.invalidMessage(e);
        }
    }

    public void configure(Guild guild) throws GuildConfigurationException {
            Message msg = retrieveGuildConfigurationMessage(guild);
            GuildConfiguration configuration = readGuildConfiguration(msg);
            contexts.put(guild.getId(), GuildContext.of(bot, configuration));
            
    }

    public GuildContext getContext(Guild guild){
        return getContext(guild.getId());
    }

    public GuildContext getContext(String guild){
        return contexts.get(guild);
    }


    public void writeDefault() {
        if (CONFIG_DIRECTORY_PATH.toFile().mkdirs())
            this.log.info("Configuration Folder created in %s".formatted(CONFIG_DIRECTORY_PATH));
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(CONFIG_DEFAULT_PATH.toFile(),
                    BotConfiguration.getDefault());
            log.info("Configuration created in %s.".formatted(CONFIG_DEFAULT_PATH));
        } catch (IOException e) {
            log.error("Configuration can't be created in %s.", e);
        }
    }

}
