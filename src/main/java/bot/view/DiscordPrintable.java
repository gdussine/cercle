package bot.view;

public interface DiscordPrintable {

    public String toDiscordString();

    public static String templateDiscordLink(DiscordPrintable o, String guildId, String channelId, String id){
        return "[%s](https://discord.com/channels/%s/%s/%s)".formatted(o.toString(), guildId, channelId, id);
    }
    
}
