package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.Category;
import model.User;
import util.HibernateUtil;

public class UserDAO implements Dao<User, Integer> {

	@Override
	public List<User> findAll() {
		List<User> list = new ArrayList<User>();
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				Transaction tr = session.beginTransaction();
				String hql = "from User";
				Query query = session.createQuery(hql);
				list = query.getResultList();
				tr.commit();
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public User selectById(Integer id) {
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				Transaction tr = session.beginTransaction();
				User user = session.find(User.class, id);
				tr.commit();
				session.close();
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(User t) {
		Transaction tr = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				tr = session.beginTransaction();
				session.persist(t);
				System.out.println("SAVE USER SUCCESS");
				tr.commit();
				session.close();
			}
		} catch (Exception e) {
			System.out.println("SAVE USER FAILED");

	        if (tr != null) {
	            tr.rollback();
	        }

	        e.printStackTrace();
		}
		
	}

	@Override
	public void update(User t) {
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				Transaction tr = session.beginTransaction();
				session.merge(t);
				
				tr.commit();
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(User t) {
		Transaction tr = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				tr = session.beginTransaction();
				User t1 = session.get(User.class, t.getId());
				session.remove(t1);
				
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

	public User findByUsername(String username) {
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				Transaction tr = session.beginTransaction();
//				User user = session.find(User.class, username); sai, vi chi dc dung khi no la primary
				User user = session
				        .createQuery(
				            "FROM User WHERE username = :username",
				            User.class
				        )
				        .setParameter("username", username)
				        .uniqueResult();
				System.out.println("Found user: " + user);
				System.out.println("Searching username: " + username);
				tr.commit();
				session.close();
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public User findByEmail(String email) {
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				Transaction tr = session.beginTransaction();
				User user = session
				        .createQuery(
				            "FROM User WHERE email = :email",
				            User.class
				        )
				        .setParameter("email", email)
				        .uniqueResult();
				tr.commit();
				session.close();
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
