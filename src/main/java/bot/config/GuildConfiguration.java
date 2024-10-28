package bot.config;

public class GuildConfiguration {

    private String guild;
    private String system;
    private String log;

    public GuildConfiguration() {

    }

    public String getGuild() {
        return guild;
    }

    public void setGuild(String guild) {
        this.guild = guild;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public static GuildConfiguration of(){
        GuildConfiguration result = new GuildConfiguration();
        return result;
    }

}
