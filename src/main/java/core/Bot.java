package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {

    private JDA jda;
    private JDABuilder jdaBuilder;
    private Logger log;

    public Bot(String token) {
        this.log = LoggerFactory.getLogger(this.getClass().getSimpleName());
        this.jdaBuilder = JDABuilder.createDefault(token);
    }

    public void start() {
        this.jda = jdaBuilder.build();
        try {
            this.jda.awaitReady();
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
            System.exit(1);
        }
    }

    public void stop() {
        this.jda.shutdownNow();
        try {
            this.jda.awaitShutdown();
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
            System.exit(1);
        }
    }

}
