package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.CartItem;
import model.User;
import util.HibernateUtil;

public class CartItemDAO implements Dao<CartItem, Integer> {

	@Override
	public List<CartItem> findAll() {
		List<CartItem> result = new ArrayList<CartItem>();
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				Transaction tr = session.beginTransaction();
				
				result = session.createNamedQuery("From CartItem", CartItem.class).getResultList();
				
				tr.commit();
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public CartItem selectById(Integer id) {
		CartItem result = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				Transaction tr = session.beginTransaction();
				
				result = session.createQuery("From CartItem where id=:id", CartItem.class).setParameter("id", id).uniqueResult();
				
				tr.commit();
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}

	@Override
	public void save(CartItem t) {
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
	public void update(CartItem t) {
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				Transaction tr = session.beginTransaction();
				
				session.createQuery(
	                    "UPDATE CartItem " +
	                    "SET user = :user, " +
	                    "product = :product, " +
	                    "quantity = :quantity " +
	                    "WHERE id = :id"
	            )
	            .setParameter("user", t.getUser())
	            .setParameter("product", t.getProduct())
	            .setParameter("quantity", t.getQuantity())
	            .setParameter("id", t.getId())
	            .executeUpdate();
				
				
				tr.commit();
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(CartItem t) {
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				Transaction tr = session.beginTransaction();
				
				session.createQuery("DELETE FROM CartItem WHERE id = :id").setParameter("id", t.getId()).executeUpdate();
				
				tr.commit();
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
	
	public CartItem findByUserAndProduct(int userId, int productId) {
		CartItem result = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				Transaction tr = session.beginTransaction();
				
				result = (CartItem) session.createQuery("FROM CartItem WHERE user.id = :userId AND product.id = :productId")
//				"Tao chỉ biết obj là Object, làm sao tao chắc nó là CartItem?"
//				Nên bạn phải khẳng định:
//				CartItem result = (CartItem) obj;
//				Tức: "Tôi biết object này thực chất là CartItem, hãy coi nó là CartItem."
//				Đó là explicit cast.
				// hoac result = session.createQuery("FROM CartItem WHERE user.id = :userId AND product.id = :productId", CartItem.class)	
				.setParameter("userId", userId)
				.setParameter("productId", productId)
				.uniqueResult();
				
				tr.commit();
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return result;
	}
		
	public List<CartItem> findByUser(int userId){
		Transaction tr = null;
		List<CartItem> result = new ArrayList<CartItem>();
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				tr = session.beginTransaction();
				// session.createQuery("FROM CartItem WHERE user.id = :userId") cách này trả về query dạng raw, dễ phát sinh warning/cast.
				result = session.createQuery("FROM CartItem WHERE user.id = :userId", CartItem.class).setParameter("userId", userId).getResultList();
				
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
	
	public void save(CartItem t, Session session) {
	    session.persist(t);
	}
	
	public void delete(CartItem t, Session session) {
	    session.remove(t);
	}
	
	public List<CartItem> findByUser(int userId, Session session){
		return session.createQuery("FROM CartItem WHERE user.id = :userId", CartItem.class).setParameter("userId", userId).getResultList();
	}
	
}
