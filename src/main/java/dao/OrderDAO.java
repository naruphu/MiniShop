package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.CartItem;
import model.Order;
import util.HibernateUtil;

public class OrderDAO implements Dao<Order, Integer>{

	@Override
	public List<Order> findAll() {
		List<Order> result = new ArrayList<Order>();
		Transaction tr = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				tr = session.beginTransaction();
				result = session.createQuery("FROM Order", Order.class).getResultList();
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
	public Order selectById(Integer id) {
		Order order = null;
		Transaction tr = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				tr = session.beginTransaction();
				order = session.createQuery(
						"FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.id = :id",
						Order.class
						)
						.setParameter("id", id)
						.uniqueResult();
				tr.commit();
				session.close();
			}
		} catch (Exception e) {
			if(tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return order;
	}

	@Override
	public void save(Order t) {
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
	public void update(Order t) {
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
	public void delete(Order t) {
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
	
	public List<Order> findByUser(int userId){
		Transaction tr = null;
		List<Order> result = new ArrayList<Order>();
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				tr = session.beginTransaction();
				// session.createQuery("FROM CartItem WHERE user.id = :userId") cách này trả về query dạng raw, dễ phát sinh warning/cast.
				result = session.createQuery("FROM Order WHERE user.id = :userId", Order.class).setParameter("userId", userId).getResultList();
				
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
	
	public void save(Order order, Session session) {
	    session.persist(order);
	}
	
	public void delete(Order t, Session session) {
	    session.remove(t);
	}
	
	public Order selectById(int id, Session session) {
		return session.createQuery(
			"SELECT DISTINCT o " +
			"FROM Order o " + 
			"LEFT JOIN FETCH o.orderItems " +
			"WHERE o.id = :id", Order.class
		).setParameter("id", id).uniqueResult();
	}

}
