/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package todo.com.mikevostrikov.ralter.app.domain.model;


import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate SessionFactory singleton
 *
 * @author Mike V
 */
public class HibernateSessionFactoryFactory {
    
    private static final Logger LOG = Logger.getLogger(HibernateSessionFactoryFactory.class.getName());
    
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration().configure();
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        } catch (Throwable ex) {
            LOG.log(Level.SEVERE, "Hibernate sessionFactory initialization failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
