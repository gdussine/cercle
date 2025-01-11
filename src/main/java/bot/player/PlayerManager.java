package bot.player;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import bot.core.BotManager;
import bot.persistence.Repository;
import irelia.data.account.Account;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.UserSnowflake;

public class PlayerManager extends BotManager{

    private Repository<Player> repository;
    
        public PlayerManager(){
            super(new PlayerListener());
            this.repository = new Repository<>(Player.class);
    }


    public Player getPlayer(UserSnowflake user, Guild guild){
        Player result = repository.get(user.getId());
        if(result == null){
            result = repository.persist(Player.createDefault(user));
            this.logInfo("%s saved as a player".formatted(user.getAsMention()), guild);
        }
        return result;
    }

    public List<Player> getPlayers(){
        return repository.all();
    }

    public CompletableFuture<Account> getRiotAccount(String accountId){
        return bot.getIrelia().account().byId(accountId);
    }

    public CompletableFuture<Player> retrieveRiotAccounFuture(String name, String tag, Player player){
        return bot.getIrelia().account().byRiotId(name, tag).thenApply(account->{
            player.setRiot(account);
            return repository.merge(player);
        });
    }

}
