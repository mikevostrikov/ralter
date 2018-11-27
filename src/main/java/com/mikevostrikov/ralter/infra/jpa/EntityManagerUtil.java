/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.infra.jpa;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author m.vostrikov
 */
public class EntityManagerUtil {
    
    private static final Logger LOG = Logger.getLogger(EntityManagerUtil.class.getName());
    
    private static final EntityManagerFactory entityManagerFactory;
    
    static {
        try {
            Map<String, String> env = System.getenv();
            Map<String, Object> configOverrides = new HashMap<String, Object>();
            LOG.log(Level.INFO, "Environment vars: {0}", env);
            String envDbUrl = env.get("DERBYDB_URL");
            String envDbUser = env.get("DERBYDB_USER");
            String envDbPassword = env.get("DERBYDB_PASSWORD");
            if (envDbUrl != null) {
                configOverrides.put("javax.persistence.jdbc.url", envDbUrl);
            }
            if (envDbUser != null) {
                configOverrides.put("javax.persistence.jdbc.user", envDbUser);    
            }
            if (envDbPassword != null) {
                configOverrides.put("javax.persistence.jdbc.password", envDbPassword);    
            }
            entityManagerFactory = Persistence.createEntityManagerFactory("RalterPU", configOverrides);
        } catch (Throwable ex) {
            LOG.log(Level.SEVERE, "Unable to initialize EntityManagerFactory", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
    
}
