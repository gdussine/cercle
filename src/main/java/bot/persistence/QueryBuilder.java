package bot.persistence;

import java.util.List;

import org.hibernate.Session;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class QueryBuilder<T> {

	private CriteriaBuilder criteriaBuilder;
	private Root<T> root;
	private CriteriaQuery<T> criteriaQuery;
	private Session session;

	public QueryBuilder(Session s, Class<T> type) {
		this.session = s;
		this.criteriaBuilder = s.getCriteriaBuilder();
		this.criteriaQuery = criteriaBuilder.createQuery(type);
		this.root = criteriaQuery.from(type);
	}

	public Root<T> getRoot() {
		return root;
	}

	public CriteriaBuilder getCriteriaBuilder() {
		return criteriaBuilder;
	}

	public CriteriaQuery<T> getCriteriaQuery() {
		return criteriaQuery;
	}

	public QueryBuilder<T> select() {
		criteriaQuery.select(root);
		return this;
	}

	public QueryBuilder<T> where(Predicate... predicates) {
		criteriaQuery.where(predicates);
		return this;
	}

	public List<T> getResultList() {
		return session.createQuery(criteriaQuery).getResultList();
	}
}
