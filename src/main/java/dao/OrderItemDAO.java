package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.Order;
import model.OrderItem;
import util.HibernateUtil;

public class OrderItemDAO implements Dao<OrderItem, Integer> {

	@Override
	public List<OrderItem> findAll() {
		List<OrderItem> result = new ArrayList<OrderItem>();
		Transaction tr = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				tr = session.beginTransaction();
				result = session.createQuery("FROM OrderItem", OrderItem.class).getResultList();
				tr.commit();
				session.close();
			}
		} catch (Exception e) {
			if(tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public OrderItem selectById(Integer id) {
		OrderItem item = null;
		Transaction tr = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				tr = session.beginTransaction();
				item = session.createQuery("FROM OrderItem WHERE id = :id", OrderItem.class).setParameter("id", id).uniqueResult();
				tr.commit();
				session.close();
			}
		} catch (Exception e) {
			if(tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return item;
	}

	@Override
	public void save(OrderItem t) {
		Transaction tr = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				tr = session.beginTransaction();
				session.persist(t);
				tr.commit();
				session.close();
			}
		} catch (Exception e) {
			if(tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(OrderItem t) {
		Transaction tr = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				tr = session.beginTransaction();
				session.merge(t);
				tr.commit();
				session.close();
			}
		} catch (Exception e) {
			if(tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(OrderItem t) {
		Transaction tr = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				tr = session.beginTransaction();
				session.remove(t);
				tr.commit();
				session.close();
			}
		} catch (Exception e) {
			if(tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
	}
	
	public void save(OrderItem item, Session session) {
	    session.persist(item);
	}
}
