package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.Category;
import model.Product;
import util.HibernateUtil;

public class ProductDAO implements Dao<Product, Integer> {

	@Override
	public List<Product> findAll() {
		List<Product> list = new ArrayList<Product>();
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				Transaction tr = session.beginTransaction();
				String hql = "from Product";
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
	public Product selectById(Integer id) {
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				Transaction tr = session.beginTransaction();
				Product p = session.find(Product.class, id);
				tr.commit();
				session.close();
				return p;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	public void save(Product t) {
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
	public void update(Product t) {
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				Transaction tr = session.beginTransaction();
				session.update(t);
				tr.commit();
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(Product t) {
		Transaction tr = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				tr = session.beginTransaction();
				Product t1 = session.get(Product.class, t.getId());
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
	
//	public List<Product> searchByName(String keyword, int offset, int limit){
//		List<Product> list = new ArrayList<Product>();
//	    Session session = null;
//
//	    try {	
//
//	        session =
//	        HibernateUtil.getSessionFactory()
//	        .openSession();
//
//
//	        list = session.createQuery("FROM Product p WHERE p.name LIKE :keyword", Product.class)
//	        	.setParameter("keyword", "%" + keyword + "%")
//	        	.setFirstResult(offset)
//	        	.setMaxResults(limit)
//	        	.getResultList();
//
//
//	    } finally {
//
//	        if(session != null){
//	            session.close();
//	        }
//
//	    }
//	    return list;
//	}
	
	public List<Product> findProducts(String keyword, Integer categoryId, int offset, int limit){
		List<Product> list = new ArrayList<Product>();
	    Session session = null;

	    try {	

	        session =
	        HibernateUtil.getSessionFactory()
	        .openSession();


	        String hql = "FROM Product p WHERE 1=1";
	        if(keyword != null && !keyword.trim().isEmpty()) {
	            hql += " AND p.name LIKE :keyword";
	        }

	        if(categoryId != null) {
	            hql += " AND p.category.id = :categoryId";
	        }
	        
	        Query query = session.createQuery(hql, Product.class);
	        
	        if(keyword != null && !keyword.trim().isEmpty()) {
	  			query.setParameter("keyword", "%" + keyword + "%");
	       	}
	        if(categoryId != null) {
	  			query.setParameter("categoryId", categoryId);
	       	}

	        query
	        	.setFirstResult(offset)
	        	.setMaxResults(limit);
	       
	        list = query.getResultList();
	        
	        
	    } finally {

	        if(session != null){
	            session.close();
	        }

	    }
	    return list;
	}
	
	public long countProducts(String keyword, Integer categoryId){

	    Session session = null;

	    try {

	        session = HibernateUtil
	                .getSessionFactory()
	                .openSession();

	        String hql = "SELECT COUNT(p) FROM Product p WHERE 1=1";
	        
	        if(keyword != null || !keyword.trim().isEmpty()){
	        	hql += " AND p.name LIKE :keyword";
	        }
	        
	        if (categoryId != null) {
	            hql += " AND p.category.id = :categoryId";
	        }
	        
	        Query query =
	                session.createQuery(hql, Long.class);

	        if (keyword != null || !keyword.trim().isEmpty()) {
	            query.setParameter(
	                    "keyword",
	                    "%" + keyword + "%"
	            );
	        }

	        if (categoryId != null) {
	            query.setParameter(
	                    "categoryId",
	                    categoryId
	            );
	        }

	        return (long) query.getSingleResult();

	    } finally {

	        if(session != null){
	            session.close();
	        }

	    }

	}
	

}
