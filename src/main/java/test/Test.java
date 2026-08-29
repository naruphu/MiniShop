package test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import model.Category;
import util.HibernateUtil;

public class Test {
	public static void main(String[] args) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//		if(sessionFactory != null) {
//			Session session = sessionFactory.openSession();
//			Transaction tr = session.beginTransaction();
//			Category c1 = new Category("Book");
//			Category c2 = new Category("Electronic");
//			Category c3 = new Category("Household Appliance");
//			
//			session.persist(c1);
//			session.persist(c2);
//			session.persist(c3);
//			
//			tr.commit();
//			session.close();
//		}
		SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        System.out.println("Hibernate started!");

        factory.close();
	}
}
