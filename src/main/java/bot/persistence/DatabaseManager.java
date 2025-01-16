package bot.persistence;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import bot.config.BotConfiguration;
import bot.config.GuildConfiguration;
import bot.inhouse.InHouseGame;
import bot.inhouse.InHouseGamePick;
import bot.inhouse.event.InHouseEvent;
import bot.inhouse.event.InHouseEventParticipation;
import bot.inhouse.season.InHouseSeason;
import bot.inhouse.season.InHouseSeasonElo;
import bot.player.Player;

public class DatabaseManager {

    private final Class<?>[] entities = { 
    		BotConfiguration.class,
            GuildConfiguration.class,
            Player.class,
            InHouseEventParticipation.class,
            InHouseEvent.class,
            InHouseSeason.class,
            InHouseSeasonElo.class,
            InHouseGame.class,
            InHouseGamePick.class
    };

    private SessionFactory sessionFactory;
    private Session session;
    private String password;
    private String username;

    private static DatabaseManager instance;

    public static DatabaseManager getInstance() {
        return instance;
    }

    public DatabaseManager(String username, String password) {
        this.username = username;
        this.password = password;
        DatabaseManager.instance = this;
    }

    public void start() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.username", username)
                .applySetting("hibernate.connection.password", password)
                .build();
        sessionFactory = new MetadataSources(registry)
                .addAnnotatedClasses(entities)
                .buildMetadata()
                .buildSessionFactory();
        session = getCurrentSession();

    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Session getCurrentSession() {
        if (session == null || !session.isOpen()) {
            this.session = sessionFactory.openSession();
            session.setCacheMode(CacheMode.IGNORE);
        }
        return session;
    }

    public void close() {
        session.close();
        sessionFactory.close();
    }

}
