package bot.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bot.core.BotManager;
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

    public GuildConfiguration loadGuild(Guild guild) {
        try {
            List<TextChannel> temp = guild.getTextChannelsByName(CONFIG_CHANNEL_NAME, false);
            TextChannel configChannel = temp.isEmpty() ? guild.createTextChannel(CONFIG_CHANNEL_NAME).complete()
                    : temp.getFirst();
            Message message = configChannel.getLatestMessageId().equals("0")
                    ? configChannel
                            .sendMessage(
                                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(GuildConfiguration.of()))
                            .complete()
                    : configChannel.retrieveMessageById(configChannel.getLatestMessageId()).complete();
            GuildConfiguration result = mapper.readValue(message.getContentRaw(), GuildConfiguration.class);
            contexts.put(guild.getId(), new GuildContext(result, bot));
            return result;
        } catch (JsonProcessingException e) {
            return null;
        }
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
