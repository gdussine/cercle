package bot.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.config.Configuration;
import bot.config.ConfigurationLoader;

public class Launcher {

    private enum BotLauncherAction {
        RUN, SETUP, DEBUG;
    }

    private String configPath;
    private BotLauncherAction action = BotLauncherAction.RUN;
    private Logger log = LoggerFactory.getLogger(Launcher.class);
    private ConfigurationLoader loader = new ConfigurationLoader();

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

    protected Launcher parse(String[] args){
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
        Configuration config = loader.load(configPath);
        Bot bot = new Bot(config);
        bot.start();
        return this;

    }

    protected Launcher debug() {
        return run();

    }

    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.parse(args).launch();
    }
}
