package bot.launcher;

import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.config.BotConfiguration;
import bot.core.Bot;
import bot.persistence.DatabaseManager;
import bot.persistence.Repository;

public class Launcher {

    private String[] args = {"RUN", "MAIN"};;
    private LauncherAction action;
    private Logger log = LoggerFactory.getLogger(Launcher.class);
    private DatabaseManager dbm;

    private Launcher(String[] args) {
    	if(args.length != 0)
    		this.args = args;
        this.dbm = new DatabaseManager("bot", "test");
    }

    public void parse() {
        try {
            this.action = LauncherAction.get(args);
            switch (action) {
                case CONFIG:
                    config();
                    break;
                case RUN:
                    run();
                    break;
                case HELP:
                default:
                    help();
                    break;
            }
        } catch (IllegalArgumentException iae) {
            log.error(iae.getMessage(), iae);
        }
    }

    protected void run() {
        this.dbm.start();
        String name = args[1];
        Repository<BotConfiguration> repository = new Repository<>(BotConfiguration.class);
        BotConfiguration configuration = repository.get(name);
        Bot bot = new Bot(configuration);
        bot.start();
    }

    public void config() {
        this.dbm.start();
        BotConfiguration configuration = new BotConfiguration(args[1], args[2], args[3], args[4]);
        Repository<BotConfiguration> repository = new Repository<>(BotConfiguration.class);
        repository.merge(configuration);
        this.dbm.close();
    }

    public void help() {
        System.out.println("\n=====[ Help Bot Cercle ]\n");
        for (LauncherAction a : LauncherAction.values()) {
            System.out.println("- %s %s\n".formatted(a.name(), a.getFormat()));
        }
    }

    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Launcher launcher = new Launcher(args);
        launcher.parse();
    }
}
