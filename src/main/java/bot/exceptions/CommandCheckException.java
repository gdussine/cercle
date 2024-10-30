package bot.exceptions;

import bot.command.core.CommandAction;

public class CommandCheckException extends Exception{

    private CommandAction action;

    public CommandCheckException(Throwable cause, CommandAction action){
        this(cause.getMessage(), action);
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
