package bot.player;

import java.util.concurrent.CompletableFuture;

import bot.core.BotManager;
import bot.persistence.Repository;
import irelia.data.account.Account;

public class PlayerManager extends BotManager{

    private Repository<Player> repository;
    
        public PlayerManager(){
            super(new PlayerListener());
            this.repository = new Repository<>(Player.class);
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
