package wangzhongqiu.schedule.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.List;

abstract public class AbstractBaseDao<T> extends HibernateDaoSupport {

    abstract protected Class<T> getEntityClass();

    @Autowired
    private void init(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    public void persist(T entity) {
        getHibernateTemplate().persist(entity);
    }

    public void delete(T entity) {
        getHibernateTemplate().delete(entity);
    }

    public void update(T entity) {
        getHibernateTemplate().update(entity);
    }

    public T get(Serializable id) {
        return getHibernateTemplate().get(getEntityClass(), id);
    }

    protected Query createQuery(String hql) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
    }

    protected Object unique(String hql, Object... params) {
        return unique(createQuery(hql), params);
    }

    protected Object unique(Query query, Object... params) {
        for (int i = 0; i < params.length; ++i) {
            query.setParameter(i, params[i]);
        }
        return query.uniqueResult();
    }

    protected List<?> list(String hql, Object... params) {
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql);
        for (int i = 0; i < params.length; ++i) {
            query.setParameter(i, params[i]);
        }
        return query.list();
    }

}
