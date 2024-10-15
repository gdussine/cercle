package core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BotConfigurationLoader {

    private ObjectMapper mapper;

    private static final Path CONFIG_DIRECTORY_PATH = Path.of("config");
    private static final Path CONFIG_DEFAULT_PATH = CONFIG_DIRECTORY_PATH.resolve("default.json");


    public BotConfigurationLoader(){
        this.mapper = new ObjectMapper();
        this.init();
    }

    public void init(){
        CONFIG_DIRECTORY_PATH.toFile().mkdirs();
    }

    public BotConfiguration load(Path path){
        File file = path.toFile();
        try {
            BotConfiguration result = mapper.readValue( file, BotConfiguration.class);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BotConfiguration loadDefault(){
        return load(CONFIG_DEFAULT_PATH);
    }

    public void writeDefault(){
        try{
            mapper.writerWithDefaultPrettyPrinter().writeValue(CONFIG_DEFAULT_PATH.toFile(), BotConfiguration.getDefault());
        } catch (IOException e){
            e.printStackTrace();
        }
    }


}
