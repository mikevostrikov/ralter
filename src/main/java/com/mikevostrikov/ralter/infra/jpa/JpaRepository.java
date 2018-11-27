/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.infra.jpa;

import com.mikevostrikov.ralter.app.domain.Repository;
import com.mikevostrikov.ralter.app.domain.model.workers.Employee;
import com.mikevostrikov.ralter.app.domain.model.workers.Role;
import com.mikevostrikov.ralter.app.domain.model.workers.User;
import com.mikevostrikov.ralter.infra.jpa.model.workers.JpaEmployee;
import com.mikevostrikov.ralter.infra.jpa.model.workers.JpaRole;
import com.mikevostrikov.ralter.infra.jpa.model.workers.JpaUser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
 
/**
 *
 * @author m.vostrikov
 */
public class JpaRepository implements Repository {

    private final EntityManager entityManager;
    
    private final Map<Class, Class> domainToJpaClassMapping;
    
    public JpaRepository (EntityManager entityManager) {
        this.entityManager = entityManager;
        domainToJpaClassMapping = new HashMap<>();
        domainToJpaClassMapping.put(Employee.class, JpaEmployee.class);
        domainToJpaClassMapping.put(User.class, JpaUser.class);
        domainToJpaClassMapping.put(Role.class, JpaRole.class);
    }
    
    // FINDERS

    @Override
    public <T> T findById(Class<T> clazz, long id) {
        return (T) entityManager.find(domainToJpaClassMapping.get(clazz), id);
    }

    @Override
    public <T> List<T> findAll(Class<T> clazz) {
        // TODO: rewrite using JPQL syntax
        return entityManager.createQuery("from " + domainToJpaClassMapping.get(clazz).getName()).getResultList();
    }
    
    @Override
    public User findUserByCredentials(String username, String passwordHash) {
        
        List<User> usersRetrieved = entityManager.createQuery("from JpaUser users where users.usLogin='" + username + "' and users.usPassword='" + passwordHash + "'").getResultList();

        if (usersRetrieved.size() == 1) {
            return usersRetrieved.get(0);
        } else if (usersRetrieved.size() > 1) {
            throw new RuntimeException("More than one user exist with the specified credentials");
        } else {
            return null;
        }
        
    }
    
    // OTHER
    
    @Override
    public <T> void persist(T entity) {
        entityManager.persist(entity);
    }
    
    @Override
    public <T> T update(T entity) {
        T en = entityManager.merge(entity);
        return en;
    }


    @Override
    public <T> void refresh(T entity) {
        entityManager.refresh(entity);
    }
    
    @Override
    public <T> void delete(T entity) {
        T en = entityManager.merge(entity);
        entityManager.remove(en);
    }

    @Override
    public <T> void deleteById(Class<T> clazz, long entityId) {
        Object entity = findById(clazz, entityId);
        delete(entity);
    }

    @Override
    public void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    @Override
    public void commitTransaction() {
        entityManager.getTransaction().commit();
    }

    @Override
    public void rollbackTransaction() {
        entityManager.getTransaction().rollback();
    }
    
    @Override
    public void flush() {
        entityManager.flush();
    }

    @Override
    public boolean isActiveTransaction() {
        return entityManager.getTransaction().isActive();
    }
    
    @Override
    public void closeSession() {
        entityManager.close();
    }

    @Override
    public <T> T createEmpty(Class<T> clazz) {
        try {
            return (T) domainToJpaClassMapping.get(clazz).newInstance();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
}