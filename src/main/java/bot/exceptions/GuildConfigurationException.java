package bot.exceptions;

import java.lang.reflect.Method;

public class GuildConfigurationException extends Exception{

    private static final String NULL_FIELD = "Null configuration field %s";
    private static final String REFLECTION_FAILED = "Reflection failed on field %s";
    private static final String TEXTCHANNEL_NOT_FOUND = "TextChannel %s not found";
    private static final String ROLE_NOT_FOUND = "Role %s not found ";

    private String label;

    private GuildConfigurationException(String label, String format){
        super(format.formatted(label));
        this.label = label;
    }

    private GuildConfigurationException(String label, String format, Throwable cause){
        super(format.formatted(label), cause);
        this.label = label;
    }

    private GuildConfigurationException(Method method, String format){
        this(method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4), format);
    }

    private GuildConfigurationException(Method method, String format, Throwable cause){
        this(method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4), format, cause);
    }

    public static GuildConfigurationException nullField(Method getter){
        return new GuildConfigurationException(getter, NULL_FIELD);
    }

    public static GuildConfigurationException reflectionFailed(Method getter, Throwable cause){
        return new GuildConfigurationException(getter, REFLECTION_FAILED, cause);
    }

    public static GuildConfigurationException reflectionFailed(String label, Throwable cause){
        return new GuildConfigurationException(label, REFLECTION_FAILED, cause);
    }

    public static GuildConfigurationException textChannelNotFound(String label){
        return new GuildConfigurationException(label, TEXTCHANNEL_NOT_FOUND);
    }

    public static GuildConfigurationException roleNotFound(String label){
        return new GuildConfigurationException(label, ROLE_NOT_FOUND);
    }

    public String getLabel() {
        return label;
    }

}
