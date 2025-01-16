package bot.view;

import bot.inhouse.InHouseEntity;

public abstract class InHouseEntityView<T extends InHouseEntity> extends NormalizedEmbedView{

    public InHouseEntityView(String emoji, String title){
        super(emoji, title);
    }

    public abstract InHouseEntityView<T> hydrate(T t);



}
