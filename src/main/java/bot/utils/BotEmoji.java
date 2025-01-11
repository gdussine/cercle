package bot.utils;

import net.dv8tion.jda.api.entities.emoji.Emoji;

public enum BotEmoji {

    WHITE_CHECK_MARK("\u2705"), NO_ENTRY_SIGN("\uD83D\uDEAB"), ARROW_LEFT("\u2B05"), ARROW_RIGHT("\u27A1")
    ;

    private String code;

    private BotEmoji(String code) {
        this.code = code;
    }

    public Emoji asEmoji(){
        return Emoji.fromUnicode(code);
    }
    
    public String asUnicode() {
    	return code;
    }
}
