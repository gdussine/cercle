package bot.persistence;

import javax.xml.crypto.Data;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class Repository<T> {

    private Class<T> type;

    public Repository(Class<T> type) {
        this.type = type;
    }

    public T get(Object id){
        Session s =  this.getCurrentSession();
        return s.find(type, id);
    }

    public T persist(T o){
        Session s =  this.getCurrentSession();
        Transaction t = s.beginTransaction();
        s.persist(o);
        t.commit();
        return o;
    }

    public T merge(T o){
        Session s =  this.getCurrentSession();
        Transaction t = s.beginTransaction();
        s.merge(o);
        t.commit();
        return o;
    }

    private Session getCurrentSession(){
        return  DatabaseManager.getInstance().getCurrentSession();
    }

}
