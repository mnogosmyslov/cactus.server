package org.cactus.server.service;

import org.cactus.server.entity.Chat;
import org.cactus.server.entity.UserAccount;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("depracation")
public class HibernateUtil {
    private static final Logger log = Logger.getLogger(HibernateUtil.class.getName());
    private static final SessionFactory sessionFactory;
    private static final ServiceRegistry serviceRegistry;

    static {
        try {
            // create a new instance of OmgConfiguration
            Configuration cfg = new Configuration();

            //configure PostgreSQL connection
            cfg.setProperty("hibernate.bytecode.use_reflection_optimizer", "false");
            cfg.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
//            cfg.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:55432/cactusdb");
            cfg.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/cactusdb");
            cfg.setProperty("hibernate.connection.username", "postgres");
//            cfg.setProperty("hibernate.connection.password", "gl9HSx7EbVmJ"); //root
            cfg.setProperty("hibernate.connection.password", "root");
            cfg.setProperty("show_sql", "true");
            cfg.setProperty("hibernate.hbm2ddl.auto", "update");

            cfg.setProperty("hibernate.enable_lazy_load_no_trans", "true");

            //add our annotated class
            cfg.addAnnotatedClass(Chat.class).addAnnotatedClass(UserAccount.class);

            // create the SessionFactory
            serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
            sessionFactory = cfg.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            log.log(Level.SEVERE, "Initial SessionFactory creation failed !", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Get the SessionFactory
     *
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}