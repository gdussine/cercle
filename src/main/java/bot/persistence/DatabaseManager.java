package bot.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DatabaseManager {

    private SessionFactory sessionFactory;

    private static DatabaseManager instance;

    private DatabaseManager() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .build();
        sessionFactory = new MetadataSources(registry)
                //.addAnnotatedClass(Event.class)
                .buildMetadata()
                .buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static DatabaseManager getInstance() {
        if(instance == null)
            instance = new DatabaseManager();
        return instance;
    }

}
