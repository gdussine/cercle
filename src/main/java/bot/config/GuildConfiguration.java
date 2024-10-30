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

    public void setSystem(String systemMention) {
        this.system = mentionToId(systemMention);
    }

    public String getLog() {
        return log;
    }

    public void setLog(String logMention) {
        this.log = mentionToId(logMention);
    }

    public static String mentionToId(String mention){
        return mention.substring(2).replace(">", "");
    }

    public static GuildConfiguration of(){
        GuildConfiguration result = new GuildConfiguration();
        return result;
    }

}
