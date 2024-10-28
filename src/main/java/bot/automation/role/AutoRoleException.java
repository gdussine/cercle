package bot.automation.role;

public class AutoRoleException extends Exception{

    public static final String INCORRECT_MESSAGE = "Incorrect message";
    public static final String INCORRECT_EMOJI = "Incorrect emoji";
    public static final String UNKNOWN_MESSAGE = "Unknown message";

    public AutoRoleException(String message){
        super(message);
    }


}
