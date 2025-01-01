package bot.view.impl;

import java.util.List;

import bot.view.DiscordPrintable;
import bot.view.PaginationView;

public class DatabaseListView<T extends DiscordPrintable> extends PaginationView{

    public DatabaseListView(List<T> data, int page){
        super(10, data.size());
        this.template.setTitle(":books: Database List");
        this.template.setColor(BLUE);
        this.setInfo(data); 
        this.setData(data, page);
        this.setPageFooter(page);
    }

    private void setInfo(List<T> data){
        String type = "unknown";
        if(size != 0){
            type = data.get(0).getClass().getSimpleName();
        }
        this.template.setDescription("Size: %d\nType: %s".formatted(data.size(), type));
    }

    private void setData(List<T> data, int page){
        if(size == 0)
            return;
        List<T> visible = data.subList(Math.min((1-page) * range, size), Math.min(page*range,size));
        StringBuilder sb = new StringBuilder();
        visible.forEach(x->sb.append("- %s\n".formatted(x.toDiscordString())));
        this.template.addField("Data:", sb.toString(), true);
    }

}
