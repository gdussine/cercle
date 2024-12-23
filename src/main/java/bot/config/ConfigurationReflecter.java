package bot.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class ConfigurationReflecter {

    private static ConfigurationReflecter instance;

    public static ConfigurationReflecter getInstance() {
        if (instance == null)
            instance = new ConfigurationReflecter();
        return instance;
    }

    private List<Method> contextGetters, contextSetters, configurationGetters, configurationSetters;
    private List<String> labels;

    private ConfigurationReflecter() {

        this.contextGetters = new ArrayList<>();
        this.contextSetters = new ArrayList<>();
        this.configurationGetters = new ArrayList<>();
        this.configurationSetters = new ArrayList<>();
        this.labels = Stream.of(GuildConfiguration.class.getDeclaredFields()).map(x -> x.getName())
                .collect(Collectors.toList());
        init(GuildConfiguration.class, configurationGetters, configurationSetters);
        init(GuildContext.class, contextGetters, contextSetters);
    }

    private void init(Class<?> clazz, List<Method> getters, List<Method> setters) {
        for (Method m : clazz.getDeclaredMethods())
            if (m.getName().startsWith("get"))
                getters.add(m);
            else if (m.getName().startsWith("set"))
                setters.add(m);
    }

    public List<String> getLabels() {
        return labels;
    }

    public String getLabel(Method method) {
        String temp = method.getName().substring(3);
        return temp.substring(0, 1).toLowerCase() + temp.substring(1);
    }

    private Method getMethod(List<Method> methods, String label) {
        return methods.stream()
                .filter(method -> method.getName().substring(1).toLowerCase().equals("et" + label.toLowerCase()))
                .findAny().orElse(null);
    }

    public Method getContextGetter(String label){
        return getMethod(contextGetters, label);
    }

    public Method getContextSetter(String label){
        return getMethod(contextSetters, label);
    }

    public Method getConfigurationGetter(String label){
        return getMethod(configurationGetters, label);
    }

    public Method getConfigurationSetter(String label){
        return getMethod(configurationSetters, label);
    }

    public List<Method> getConfigurationGetters() {
        return configurationGetters;
    }

    public List<Method> getConfigurationSetters() {
        return configurationSetters;
    }

    public List<Method> getContextGetters() {
        return contextGetters;
    }

    public List<Method> getContextSetters() {
        return contextSetters;
    }

    public String configurationGet(String label, GuildConfiguration configuration){
        Method configurationGetter = this.getConfigurationGetter(label);
        try {
            Object object = configurationGetter.invoke(configuration);
            return object.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void configurationSet(String label, String value, GuildConfiguration configuration){
        Method configurationSetter = this.getConfigurationSetter(label);
        try {
            configurationSetter.invoke(configuration, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object contextGet(String label, GuildContext xContext){
        Method contextGetter = this.getContextGetter(label);
        try{
            return contextGetter.invoke(contextGetter);
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void contextSet(String label, String value, GuildContext context){
        Method contextSetter = this.getContextSetter(label);
        try {
            contextSetter.invoke(context, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
