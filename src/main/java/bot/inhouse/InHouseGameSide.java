package bot.inhouse;

public enum InHouseGameSide {

	BLUE, RED;
	
	public String discordSquare(){
		return ":%s_square:".formatted(this.name().toLowerCase());
	}

}
