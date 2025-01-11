package bot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.automation.channel.AutoVoiceChannelManager;
import bot.automation.role.AutoRoleManager;
import bot.automation.welcome.GateManager;
import bot.command.core.CommandManager;
import bot.config.BotConfiguration;
import bot.config.ConfigurationManager;
import bot.config.GuildContext;
import bot.inhouse.InHouseManager;
import bot.player.PlayerManager;
import bot.view.pagination.PaginationViewManager;
import irelia.core.Irelia;
import irelia.core.Platform;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot {
	
	private static Bot instance;
	
	public static Bot getInstance() {
		return instance;
	}

    private JDA jda;
    private JDABuilder jdaBuilder;
    private BotConfiguration configuration;
    private Logger log;
    private Irelia irelia;

    private CommandManager commandManager;
    private AutoRoleManager autoroleManager;
    private AutoVoiceChannelManager autoVoiceChannelManager;
    private ConfigurationManager configurationManager;
    private GateManager gateManager;
    private PlayerManager playerManager;
    private PaginationViewManager paginationViewManager;
    private InHouseManager inHouseManager;

    public Bot(BotConfiguration configuration){
        this.configuration = configuration;
        this.log = LoggerFactory.getLogger(this.getClass().getSimpleName());
        this.irelia = new Irelia(configuration.getRiotToken(),Platform.EUW1);
        this.jdaBuilder = JDABuilder.createDefault(configuration.getDiscordToken());
        this.jdaBuilder.enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS);
        this.configurationManager = new ConfigurationManager();
        this.commandManager = new CommandManager();
        this.autoroleManager = new AutoRoleManager();
        this.autoVoiceChannelManager = new AutoVoiceChannelManager();
        this.gateManager = new GateManager();
        this.playerManager = new PlayerManager();
        this.paginationViewManager = new PaginationViewManager();
        this.inHouseManager = new InHouseManager();
        this.registerManager();
        Bot.instance = this;
    }

    private void registerManager(){        
        this.commandManager.register(this);
        this.autoroleManager.register(this);
        this.configurationManager.register(this);
        this.autoVoiceChannelManager.register(this);
        this.gateManager.register(this);
        this.playerManager.register(this);
        this.paginationViewManager.register(this);
        this.inHouseManager.register(this);
    }

    public void start() {
        try {
            this.jda = jdaBuilder.build();
            this.jda.awaitReady();
            this.log.info("Bot started.");
            this.irelia.start();
            this.log.info("Riot API connected.");
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
            this.irelia.stop();
            this.log.info("Riot API disconnected.");
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

    public GuildContext getContext(String guildId){
        return configurationManager.getContext(guildId);
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


    public AutoVoiceChannelManager getAutoVoiceChannelManager() {
        return autoVoiceChannelManager;
    }

    public GateManager getGateManager() {
        return gateManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
    
    public PaginationViewManager getPaginationViewManager() {
		return paginationViewManager;
	}
    
    public InHouseManager getInHouseManager() {
		return inHouseManager;
	}

    

    public void listen(EventListener listener){
        this.jdaBuilder.addEventListeners(listener);
    }

    public Irelia getIrelia() {
        return irelia;
    }



}
