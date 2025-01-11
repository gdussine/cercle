package bot.persistence;

import java.util.List;
import java.util.function.Function;

import org.hibernate.Session;
import org.hibernate.Transaction;

import jakarta.persistence.criteria.Predicate;

public class Repository<T> {

	private Class<T> type;

	public Repository(Class<T> type) {
		this.type = type;
	}

	public T get(Object id) {
		Session s = this.getCurrentSession();
		return s.find(type, id);
	}
	
	public T refresh(T t) {
		Session s = this.getCurrentSession();
		s.refresh(t);
		return t;
	}

	public List<T> all() {
		return this.query(qb -> new Predicate[0]);
	}

	public List<T> query(Function<QueryBuilder<T>, Predicate[]> predicate) {
		Session s = this.getCurrentSession();
		QueryBuilder<T> query = new QueryBuilder<>(s, type);
		Predicate[] predicates = predicate.apply(query);
		return query.select().where(predicates).getResultList();
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
	
	public void delete(T o) {
		Session s = this.getCurrentSession();
		Transaction t = s.beginTransaction();
		s.remove(o);
		t.commit();
	}

	private Session getCurrentSession() {
		return DatabaseManager.getInstance().getCurrentSession();
	}

}
