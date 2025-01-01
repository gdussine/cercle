package bot.command.impl;

import java.util.List;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.core.CommandAction;
import bot.player.Player;
import bot.view.impl.DatabaseListView;
import net.dv8tion.jda.api.Permission;

@CommandModule(name = "database", permission = { Permission.ADMINISTRATOR })
public class DatabaseAction extends CommandAction{

    @CommandDescription("List all players")
    public void players() {
        List<Player> players = bot.getPlayerManager().getPlayers();
        DatabaseListView<Player> view = new DatabaseListView<>(players, 1);
        this.interaction.replyEmbeds(view.toEmbed()).queue();
    }

}
