package bot.utils;

import java.time.format.DateTimeFormatter;

public enum BotDateFormat {
	
    DEFAULT("dd/MM/yyyy HH:mm:ss"),
	INPUT_TIME("HH:mm"),
	INPUT_DATE("dd/MM/yyyy");

	private DateTimeFormatter formatter;
	
	private BotDateFormat(String pattern) {
		this.formatter = DateTimeFormatter.ofPattern(pattern);
	}
	
	public DateTimeFormatter getFormatter() {
		return formatter;
	}
	
	
	
}
