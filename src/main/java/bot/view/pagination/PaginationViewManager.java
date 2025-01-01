package bot.view.pagination;

import bot.core.BotManager;

public class PaginationViewManager extends BotManager{

    public PaginationViewManager(){
        super(new PaginationViewListener());
    }

    

}
