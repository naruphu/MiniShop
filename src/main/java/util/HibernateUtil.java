//package util;
//
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//
//public class HibernateUtil {
//	private static final SessionFactory sessionFactory = buildSessionFactory();
//
//	private static SessionFactory buildSessionFactory() {
//		try {
//			return new Configuration().configure().buildSessionFactory();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	public static SessionFactory getSessionFactory() {
//		return sessionFactory;
//	}
//	
//	public static void shutdown() {
//		sessionFactory.close();
//	}
//}

package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {

        try {

            Configuration configuration =
                    new Configuration().configure();

//            configuration.setProperty(
//                "hibernate.connection.url",
//                System.getenv("MYSQL_URL")
//            );
//
//            configuration.setProperty(
//                "hibernate.connection.username",
//                System.getenv("MYSQL_USER")
//            );
//
//            configuration.setProperty(
//                "hibernate.connection.password",
//                System.getenv("MYSQL_PASSWORD")
//            );


            return configuration.buildSessionFactory();

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }


    public static void shutdown() {
        sessionFactory.close();
    }
}
