package bot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.config.Configuration;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {

    private JDA jda;
    private JDABuilder jdaBuilder;
    private Configuration configuration;
    private Logger log;

    public Bot(Configuration configuration) {
        this.configuration = configuration;
        this.log = LoggerFactory.getLogger(this.getClass().getSimpleName());
        this.jdaBuilder = JDABuilder.createDefault(configuration.getToken());
    }

    public void start() {
        try {
            this.jda = jdaBuilder.build();
            this.jda.awaitReady();
        } catch (Exception e) {
            log.error("Bot failed to start.", e);
            System.exit(1);
        }
    }

    public void stop() {
        try {
            this.jda.shutdownNow();
            this.jda.awaitShutdown();
        } catch (Exception e) {
            log.error("Bot failed to shutdown.", e);
            System.exit(1);
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
