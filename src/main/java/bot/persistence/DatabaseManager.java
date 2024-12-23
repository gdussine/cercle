package bot.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import bot.config.BotConfiguration;
import bot.config.GuildConfiguration;
import bot.player.Player;

public class DatabaseManager {

    private final Class<?>[] entities = { BotConfiguration.class,
            GuildConfiguration.class,
            Player.class };

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
        session = sessionFactory.openSession();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Session getCurrentSession() {
        if (session == null || !session.isOpen())
            this.session = sessionFactory.openSession();
        return session;
    }

    public void close() {
        session.close();
        sessionFactory.close();
    }

}
