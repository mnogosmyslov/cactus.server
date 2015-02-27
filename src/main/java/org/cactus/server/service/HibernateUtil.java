package org.cactus.server.service;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@SuppressWarnings("depracation")
public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
	        Configuration config = new Configuration();
//	        config.addAnnotatedClass(UserAccount.class);
//	        config.setProperties(System.getProperties());
	        //creates the session factory from hibernate.cfg.xml
	        SessionFactory factory = config.configure().buildSessionFactory();
	        return factory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void shutdown() {
    	// Close caches and connection pools
    	getSessionFactory().close();
    }

}