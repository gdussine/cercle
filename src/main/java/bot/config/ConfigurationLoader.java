package bot.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigurationLoader {

    private ObjectMapper mapper;

    private Logger log;

    private static final Path CONFIG_DIRECTORY_PATH = Path.of("config");
    private static final Path CONFIG_DEFAULT_PATH = CONFIG_DIRECTORY_PATH.resolve("default.json");


    public ConfigurationLoader(){
        this.mapper = new ObjectMapper();
        this.log = LoggerFactory.getLogger(ConfigurationLoader.class);
    }

    public Configuration load(String configName){
        Path path = configName == null ? CONFIG_DEFAULT_PATH : CONFIG_DIRECTORY_PATH.resolve(configName+".json");
        File file = path.toFile();
        try {
            Configuration result = mapper.readValue( file, Configuration.class);
            return result;
        } catch (IOException e) {
            log.error("Configuration can't be read in %s".formatted(path), e);
        }
        return null;
    }

    public void writeDefault(){
        if(CONFIG_DIRECTORY_PATH.toFile().mkdirs())
            this.log.info("Configuration Folder created in %s".formatted(CONFIG_DIRECTORY_PATH));
        try{
            mapper.writerWithDefaultPrettyPrinter().writeValue(CONFIG_DEFAULT_PATH.toFile(), Configuration.getDefault());
            log.info("Configuration created in %s.".formatted(CONFIG_DEFAULT_PATH));
        } catch (IOException e){
            log.error("Configuration can't be created in %s.", e);
        }
    }


}
