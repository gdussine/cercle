package bot.core;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.config.ConfigurationManager;
import bot.persistence.DatabaseManager;

public class Launcher {

    private enum BotLauncherAction {
        RUN, SETUP, DEBUG;
    }

    private static Launcher instance;

    public static Launcher getInstance() {
        if (instance == null)
            instance = new Launcher();
        return instance;
    }

    private String configPath;
    private BotLauncherAction action = BotLauncherAction.RUN;
    private Logger log = LoggerFactory.getLogger(Launcher.class);
    private ConfigurationManager loader = new ConfigurationManager();
    private DatabaseManager databaseManager = DatabaseManager.getInstance();

    private Launcher() {

    }

    public void launch() {
        log.info("BotLauncher execute %s".formatted(action));
        switch (action) {
            case SETUP:
                this.setup();
                break;
            case DEBUG:
                this.debug();
                break;
            default:
                this.run();
                break;
        }
    }

    protected Launcher parse(String[] args) {
        if (args.length >= 1)
            this.action = BotLauncherAction.valueOf(args[0].toUpperCase());
        if (action == null)
            action = BotLauncherAction.RUN;
        if (args.length >= 2)
            this.configPath = args[1];
        return this;
    }

    protected Launcher setup() {
        loader.writeDefault();
        return this;
    }

    protected Launcher run() {
        Bot bot = new Bot(loader, configPath);
        bot.start();
        return this;
    }

    protected Launcher debug() {
        return run();

    }

    public static void main(String[] args) {
        Launcher launcher = Launcher.getInstance();
        Session session = launcher.databaseManager.getSessionFactory().openSession();
        System.out.println("coucou");
        session.close();
        System.exit(0);
        launcher.parse(args).launch();
    }
}
