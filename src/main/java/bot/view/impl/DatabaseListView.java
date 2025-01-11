package bot.view.impl;

import java.util.List;

import bot.view.DiscordPrintable;
import bot.view.pagination.PaginationView;

public class DatabaseListView extends PaginationView {

	private List<DiscordPrintable> data;
	private String type;

	private static final int LIST_RANGE = 10;

	public DatabaseListView(String type, List<DiscordPrintable> data, int page) {
		super(data.size() / LIST_RANGE + 1, page);
		this.data = data;
		this.type = type;
		this.template.setTitle(":books: Database List");
		this.template.setColor(BLUE);
		this.render();
	}

	@Override
	public void render() {
		this.template.setDescription("Size: %d\nType: %s".formatted(data.size(), type));
		if (data.isEmpty())
			return;
		List<DiscordPrintable> visible = data.subList(Math.min((page - 1) * LIST_RANGE, data.size()),
				Math.min(page * LIST_RANGE, data.size()));
		StringBuilder sb = new StringBuilder();
		visible.forEach(x -> sb.append("- %s\n".formatted(x.toDiscordString())));
		this.template.clearFields();
		this.template.addField("Data:", sb.toString(), true);
	}

}
