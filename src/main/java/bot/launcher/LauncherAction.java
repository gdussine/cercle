package bot.launcher;

public enum LauncherAction {

    HELP,
    RUN("name"),
    CONFIG("name discordToken riotToken ownerId");

    private String format;

    private LauncherAction() {
        this.format = "";
    }

    private LauncherAction(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public static LauncherAction get(String[] args) {
        if (args.length == 0)
            return HELP;
        LauncherAction action = valueOf(args[0].toUpperCase());
        String[] argsName = action.getFormat().split(" ");
        if (argsName.length != args.length - 1)
            throw new IllegalArgumentException("Correct format is \"%s %s\". %d arguments expected, %d provided"
                    .formatted(action.name(), action.getFormat(), argsName.length, args.length-1));
        return action;

    }

}
