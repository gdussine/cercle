package bot.view.pagination;

import bot.view.EmbedView;

public class PaginationView extends EmbedView {

    protected int range;
    public int size;

    public PaginationView(int range, int size) {
        this.range = range;
        this.size = size;
    }

    protected int getMaxPage() {
        if (size == 0)
            return 0;
        return (size / range) + 1;
    }

    protected void setPageFooter(int page) {
        if(size == 0)
            return; 
        this.template.setFooter("Page %d/%d".formatted(page, getMaxPage()));
    }

}
