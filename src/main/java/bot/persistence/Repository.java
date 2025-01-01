package bot.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class Repository<T> {

    private Class<T> type;

    public Repository(Class<T> type) {
        this.type = type;
    }

    public T get(Object id) {
        Session s = this.getCurrentSession();
        return s.find(type, id);
    }

    private CriteriaQuery<T> getDefaultCriteriaQuery(CriteriaBuilder criteriaBuilder){
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        criteriaQuery.select(criteriaQuery.from(type));
        return criteriaQuery;
    }

    public List<T> all() {
        Session s = this.getCurrentSession();
        CriteriaQuery<T> criteriaQuery = this.getDefaultCriteriaQuery(s.getCriteriaBuilder());
        return s.createQuery(criteriaQuery).getResultList(); 
    }

    public T persist(T o) {
        Session s = this.getCurrentSession();
        Transaction t = s.beginTransaction();
        s.persist(o);
        t.commit();
        return o;
    }

    public T merge(T o) {
        Session s = this.getCurrentSession();
        Transaction t = s.beginTransaction();
        s.merge(o);
        t.commit();
        return o;
    }

    private Session getCurrentSession() {
        return DatabaseManager.getInstance().getCurrentSession();
    }

}
