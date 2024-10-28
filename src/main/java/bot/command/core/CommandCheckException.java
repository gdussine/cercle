package bot.command.core;

public class CommandCheckException extends Exception{

    private CommandAction action;

    public CommandCheckException(Throwable cause, CommandAction action){
        super(cause);
        this.action = action;
    }

    public CommandCheckException(String message, CommandAction action){
        super(message);
        this.action = action;
    }

    public CommandAction getAction() {
        return action;
    }

}
