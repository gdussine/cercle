package bot.view.pagination;

import bot.view.EmbedView;

public abstract class PaginationView extends EmbedView {

	protected int size;
	protected int page;

	public PaginationView(int size, int page) {
		this.size = size;
		this.page = page;
		this.template.setFooter("Page %d/%d".formatted(page, size));
	}

	public PaginationView(int size) {
		this(size, 1);
	}

	public boolean hasNext() {
		return page + 1 <= size;
	}
	
	public boolean hasPrevious() {
		return page - 1 >=1;
	}
	
	public boolean isOnePage() {
		return size == 1;
	}
	
	
	public PaginationView next() {
		if (hasNext()) {
			this.page++;
			render();
		}
		return this;
	}
	
	public PaginationView previous() {
		if (hasPrevious()) {
			this.page--;
			render();
		}
		return this;
	}

	public abstract void render();

}
