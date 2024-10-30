package bot.exceptions;

import java.lang.reflect.Method;

public class GuildConfigurationException extends Exception{

    private static final String NULL_FIELD = "Null configuration field %s";
    private static final String REFLECTION_FAILED = "Reflection failed on field %s";
    private static final String NO_CONFIG_CHANNEl = "No config channel found";
    private static final String NO_CONFIG_MESSAGE = "No config message found";
    private static final String INVALID_CONFIG_MESSAGE = "Invalid config message";

    public GuildConfigurationException(String message){
        super(message);
    }

    public GuildConfigurationException(String message, Throwable cause){
        super(message, cause);
    }

    public static GuildConfigurationException nullField(Method getter){
        return new GuildConfigurationException(NULL_FIELD.formatted(getter.getName()));
    }

    public static GuildConfigurationException reflectionFailed(Method getter, Throwable cause){
        return new GuildConfigurationException(REFLECTION_FAILED.formatted(getter.getName().substring(3)), cause);
    }

    public static GuildConfigurationException noChannel(){
        return new GuildConfigurationException(NO_CONFIG_CHANNEl);
    }

    public static GuildConfigurationException noMessage(){
        return new GuildConfigurationException(NO_CONFIG_MESSAGE);
    }

    public static GuildConfigurationException invalidMessage(Throwable cause){
        return new GuildConfigurationException(INVALID_CONFIG_MESSAGE, cause);
    }

}
