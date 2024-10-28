package bot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.automation.role.AutoRoleManager;
import bot.command.core.CommandManager;
import bot.config.BotConfiguration;
import bot.config.ConfigurationManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot {

    private JDA jda;
    private JDABuilder jdaBuilder;
    private BotConfiguration configuration;
    private Logger log;

    private CommandManager commandManager;
    private AutoRoleManager autoroleManager;
    private ConfigurationManager configurationManager;

    public Bot(ConfigurationManager configurationManager, String configPath) {
        this.configuration = configurationManager.load(configPath);
        this.log = LoggerFactory.getLogger(this.getClass().getSimpleName());
        this.jdaBuilder = JDABuilder.createDefault(configuration.getToken());
        this.jdaBuilder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        this.configurationManager = configurationManager;
        this.commandManager = new CommandManager();
        this.autoroleManager = new AutoRoleManager();
        this.registerManager();
    }

    private void registerManager(){        
        this.commandManager.register(this);
        this.autoroleManager.register(this);
        this.configurationManager.register(this);
    }

    public void start() {
        try {
            this.jda = jdaBuilder.build();
            this.jda.awaitReady();
            this.log.info("Bot started.");
        } catch (Exception e) {
            log.error("Bot failed to start.", e);
            System.exit(1);
        }
    }

    public void stop() {
        try {
            this.jda.shutdownNow();
            this.jda.awaitShutdown();
            this.log.info("Bot shutdown.");
        } catch (Exception e) {
            log.error("Bot failed to shutdown.", e);
            System.exit(1);
        }
    }

    public BotConfiguration getConfiguration() {
        return configuration;
    }

    public JDA getJDA(){
        return jda;
    }

    public CommandManager getCommandManager(){
        return commandManager;
    }

    public AutoRoleManager getAutoroleManager() {
        return autoroleManager;
    }

    public ConfigurationManager getConfigurationManager(){
        return configurationManager;
    }

    public void listen(EventListener listener){
        this.jdaBuilder.addEventListeners(listener);
    }


}
